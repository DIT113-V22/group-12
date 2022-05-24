#include <vector>

#include <MQTT.h>
#include <WiFi.h>
#ifdef __SMCE__
#include <OV767X.h>
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

const auto triggerPinLeft = 2;
const auto triggerPinRight = 4;
const auto triggerPinFront = 6;

const auto echoPinLeft = 3;
const auto echoPinRight = 5;
const auto echoPinFront = 7;

const auto oneSecond = 1000UL;
#ifdef __SMCE__

const auto mqttBrokerUrl = "127.0.0.1";

#else
const auto triggerPin = 33;
const auto echoPin = 32;
const auto mqttBrokerUrl = "192.168.0.10";
#endif
const auto maxDistance = 100;
SR04 frontSensor(arduinoRuntime, triggerPinFront, echoPinFront, maxDistance);
SR04 rightSensor(arduinoRuntime, triggerPinRight, echoPinRight, maxDistance);
SR04 leftSensor(arduinoRuntime, triggerPinLeft, echoPinLeft, maxDistance);

std::vector<char> frameBuffer;

const auto THROTTLE_TOPIC = "/smartcar/carcontrol/throttle";
const auto STEERING_TOPIC = "/smartcar/carcontrol/steering";
const auto ULTRA_SOUND_TOPIC = "/smartcar/ultrasound/front";
const auto ULTRA_SOUND_TOPIC_RIGHT = "/smartcar/ultrasound/right";
const auto ULTRA_SOUND_TOPIC_LEFT = "/smartcar/ultrasound/left";
const auto AUTOPILOT_TOPIC = "/smartcar/autopilot";
bool autoPilotOn = false;


// MANUAL CONTROL
void onMessageAction(){
    mqtt.onMessage([](String topic, String message) {
        if (topic == THROTTLE_TOPIC) {
            car.setSpeed(message.toInt());

        } else if (topic == STEERING_TOPIC) {
            car.setAngle(message.toInt());
        } else if(topic == AUTOPILOT_TOPIC){
            if(message.toInt() == 1){
                autoPilotOn = true;
            }
            else if(message.toInt() == 0){
                autoPilotOn = false;
            }
        }else {
            Serial.println(topic + " " + message);
        }
    });
}


// AUTOPILOT FEATURE
void autoPilot(){

    const auto frontDistance = frontSensor.getDistance();
    const auto leftDistance = leftSensor.getDistance();
    const auto rightDistance = rightSensor.getDistance();



    if(frontDistance < 80 && frontDistance > 0){//this algorithm checks if there is an obstacle at the front and on the sides, in order to avoid potential collision in traffic

        car.setSpeed(30);
        car.setAngle(100);// turn right if there is an obstacle at the front
    }else if(leftDistance < 60 && leftDistance > 0 && rightDistance == 0){
        car.setAngle(25);// turn right if there is an obstacle on the left side
    }else if(rightDistance < 60 && rightDistance > 0 && leftDistance == 0){
        car.setAngle(-25);// turn left if there is an obstacle on the right side
    }else{// otherwise, the car drives straight
        car.setAngle(0);// drive straight forward
        car.setSpeed(50);

    }
}


void setup() {
    Serial.begin(9600);
#ifdef __SMCE__
    Camera.begin(QVGA, RGB888, 15);
  frameBuffer.resize(Camera.width() * Camera.height() * Camera.bytesPerPixel());
#endif

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


//subscribe to mqtt topics from smartcar
    mqtt.subscribe("/smartcar/#", 1);
    onMessageAction();

}


void loop() {
    if (mqtt.connected()) {
        mqtt.loop();
        const auto currentTime = millis();
#ifdef __SMCE__
    static auto previousFrame = 0UL;
    if (currentTime - previousFrame >= 65) {
      previousFrame = currentTime;
      Camera.readFrame(frameBuffer.data());
      mqtt.publish("/smartcar/camera", frameBuffer.data(), frameBuffer.size(),
                   false, 0);
    }
#endif

        static auto previousTransmission = 0UL;
        if (currentTime - previousTransmission >= oneSecond) {
            previousTransmission = currentTime;
            const auto distanceFront = String(frontSensor.getDistance());
            const auto distanceRight = String(rightSensor.getDistance());
            const auto distanceLeft = String(leftSensor.getDistance());

            mqtt.publish(ULTRA_SOUND_TOPIC, distanceFront);
            mqtt.publish(ULTRA_SOUND_TOPIC_RIGHT, distanceRight);
            mqtt.publish(ULTRA_SOUND_TOPIC_LEFT, distanceLeft);

        }
    }
#ifdef __SMCE__

    if(autoPilotOn == true){//activate autopilot
      autoPilot();
     }
     else{
      autoPilotOn == false;//deactive autopilot
      onMessageAction();//switch to manual control
     }
    // Avoid over-using the CPU if we are running in the emulator
  delay(1);
#endif
}
