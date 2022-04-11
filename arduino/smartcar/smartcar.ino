#include <Smartcar.h>

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
const int bSpeed   = -70;
const int lDegrees = -75;
const int rDegrees = 75;

void stopCar(){
    car.setSpeed(stopSpeed);
}
void startCar(){
    car.setSpeed(forwardSpeed);
}


void handleInput()
{ // handle serial input if there is any
    if (Serial.available())
    {
        char input = Serial.read(); // read everything that has been received so far and log down
                                    // the last entry
        switch (input)
        {
        case 'a': // rotate counter-clockwise going forward
            car.setSpeed(forwardSpeed);
            car.setAngle(lDegrees);
            break;
        case 'd': // turn clock-wise
            car.setSpeed(forwardSpeed);
            car.setAngle(rDegrees);
            break;
        case 'w': // go ahead
            car.setSpeed(forwardSpeed);
            car.setAngle(0);
            break;
        case 's': // go back
            car.setSpeed(bSpeed);
            car.setAngle(0);
            break;
        default: // if you receive something that you don't know, just stop
            car.setSpeed(0);
            car.setAngle(0);
        }
    }
}

void setup() {

    Serial.begin(9600);
    
}

void loop() {

    const auto distance = frontSensor.getDistance();
    Serial.println(distance);
    
    if(distance < 50 && distance > 0){
        stopCar();
    } 
    
    delay(1);

}
