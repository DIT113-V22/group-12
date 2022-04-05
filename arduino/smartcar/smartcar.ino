#include <SmartCar.h>

const int triggerPin = 6;
const int echoPin = 7;
const unsigned int maxDistance = 100;

ArduinoRuntime arduinoRuntime;

BrushedMotor leftMotor(arduinoRuntime, smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime, smartcarlib::pins::v2::rightMotorPins);

DifferentialControl control(leftMotor, rightMotor);

SR04 frontSensor(arduinoRuntime, triggerPin, echoPin, maxDistance);

SimpleCar car(control);

const auto forwardSpeed = 30;
const auto stopSpeed = 0;

void stopCar(){
    car.setSpeed(stopSpeed);
}
void startCar(){
    car.setSpeed(forwardSpeed);
}

void setup() {

    Serial.begin(9600);
    startCar();
}

void loop() {

    Serial.println(frontSensor.getDistance());
   
    if(frontSensor.getDistance() <= 50 && frontSensor.getDistance() > 0){
        stopCar();
    }
    
    delay(1);

}
