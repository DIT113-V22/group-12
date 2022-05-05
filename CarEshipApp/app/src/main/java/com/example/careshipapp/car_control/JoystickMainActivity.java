package com.example.careshipapp.car_control;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.R;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class JoystickMainActivity extends AppCompatActivity implements JoystickFunctionality.JoystickListener {
    private static final String TAG = "SmartcarMqttController";
    private static final String EXTERNAL_BROKER = "broker.hivemq.com";
    private static final String LOCALHOST = "10.0.2.2";
    private static final String MQTT_SERVER = "tcp://" + LOCALHOST + ":1883";
    private static final String THROTTLE_TOPIC = "/smartcar/carcontrol/throttle";
    private static final String STEERING_TOPIC = "/smartcar/carcontrol/steering";
    private static final int MOVEMENT = 50;
    private static final int TURN = 70;
    private static final int STOP_CAR = 0;
    private static final int QOS = 1;

    private MqttClient mqttClient;
    private boolean isConnected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.joystick_main_activity);

        mqttClient = new MqttClient(getApplicationContext(), MQTT_SERVER, TAG);
        connectToMqttBroker();

    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent) {

        if(yPercent <= -0.9){
            //go straight forward
            startMoving(MOVEMENT, 0);
        }
        else if(yPercent >= 0.9){
            //go straight back
            startMoving(-MOVEMENT, 0);
        }
        else if(xPercent >= 0.9){
            //turn right and drive
            startMoving(MOVEMENT, TURN);
        }
        else if(xPercent <= -0.9){
            //turn left and drive
            startMoving(MOVEMENT, -TURN);
        }
        else if(yPercent == 0 && xPercent == 0){
            //stop the car
            stopCar();
        }
    }

    void startMoving(int throttle, int steeringDirection){
        if (!isConnected) {
            final String notConnected = "Not connected (yet)";
            Log.e(TAG, notConnected);
            Toast.makeText(getApplicationContext(), notConnected, Toast.LENGTH_SHORT).show();
            return;
        }

        mqttClient.publish(THROTTLE_TOPIC, Integer.toString(throttle), QOS, null);
        mqttClient.publish(STEERING_TOPIC, Integer.toString(steeringDirection), QOS, null);

    }

    void stopCar(){
        mqttClient.publish(THROTTLE_TOPIC, Integer.toString(JoystickMainActivity.STOP_CAR), QOS, null);
    }

    protected void onResume() {
        super.onResume();

        connectToMqttBroker();
    }
    protected void onPause() {
        super.onPause();

        mqttClient.disconnect(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i(TAG, "Disconnected from broker");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }
    private void connectToMqttBroker() {
        if (!isConnected) {
            mqttClient.connect(TAG, "", new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;

                    final String successfulConnection = "Connected to MQTT broker";
                    Log.i(TAG, successfulConnection);
                    Toast.makeText(getApplicationContext(), successfulConnection, Toast.LENGTH_SHORT).show();

                    mqttClient.subscribe("/smartcar/ultrasound/front", QOS, null);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    final String failedConnection = "Failed to connect to MQTT broker";
                    Log.e(TAG, failedConnection);
                    Toast.makeText(getApplicationContext(), failedConnection, Toast.LENGTH_SHORT).show();
                }
            }, new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    isConnected = false;

                    final String connectionLost = "Connection to MQTT broker lost";
                    Log.w(TAG, connectionLost);
                    Toast.makeText(getApplicationContext(), connectionLost, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                }


                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Message delivered");
                }
            });
        }
    }

}




