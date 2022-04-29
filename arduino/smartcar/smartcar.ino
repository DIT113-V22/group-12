

#include <MQTT.h>
#include <WiFi.h>
#ifdef __SMCE__
#endif

#include <Smartcar.h>

MQTTClient mqtt;
WiFiClient net;

const char ssid[] = "***";
const char pass[] = "****";

ArduinoRuntime arduinoRuntime;
BrushedMotor leftMotor(arduinoRuntime, smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime, smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

SimpleCar car(control);

const auto oneSecond = 1000UL;
#ifdef __SMCE__
const auto triggerPin = 6;
const auto echoPin = 7;
const auto mqttBrokerUrl = "127.0.0.1";
#else
const auto triggerPin = 33;
const auto echoPin = 32;
const auto mqttBrokerUrl = "192.168.0.10";
#endif
const auto maxDistance = 100;
SR04 frontSensor(arduinoRuntime, triggerPin, echoPin, maxDistance);

const auto THROTTLE_TOPIC = "/smartcar/carcontrol/throttle";
const auto STEERING_TOPIC = "/smartcar/carcontrol/steering";
const auto ULTRA_SOUND_TOPIC = "/smartcar/ultrasound/front";


void stopBeforeObstacle(){
    const auto distance = frontSensor.getDistance();
    if(distance < 50 && distance > 0){
        car.setSpeed(0);
    }
}

void setup() {
    Serial.begin(9600);

    WiFi.begin(ssid, pass);
    mqtt.begin(mqttBrokerUrl, 1883, net);

    Serial.println("Connecting to WiFi...");
    auto wifiStatus = WiFi.status();
    while (wifiStatus != WL_CONNECTED && wifiStatus != WL_NO_SHIELD) {
        Serial.println(wifiStatus);
        Serial.print(".");
        delay(1000);
        wifiStatus = WiFi.status();
    }


    Serial.println("Connecting to MQTT broker");
    while (!mqtt.connect("arduino", "public", "public")) {
        Serial.print(".");
        delay(1000);
    }
    if(mqtt.connect("arduino", "public", "public")) {
        Serial.print("Connected");
    }



    mqtt.subscribe("/smartcar/carcontrol/#", 1);
    mqtt.onMessage([](String topic, String message) {
        if (topic == THROTTLE_TOPIC) {
            car.setSpeed(message.toInt());
        } else if (topic == STEERING_TOPIC) {
            car.setAngle(message.toInt());
        } else {
            Serial.println(topic + " " + message);
        }
    });
}

void loop() {
    if (mqtt.connected()) {
        mqtt.loop();
        const auto currentTime = millis();
#ifdef __SMCE__

    static auto previousTransmission = 0UL;
    if (currentTime - previousTransmission >= oneSecond) {
      previousTransmission = currentTime;
      const auto distance = String(frontSensor.getDistance());
      mqtt.publish(ULTRA_SOUND_TOPIC, distance);
      stopBeforeObstacle();
    }
#endif
    }
#ifdef __SMCE__
    // Avoid over-using the CPU if we are running in the emulator
  delay(1);
#endif
}