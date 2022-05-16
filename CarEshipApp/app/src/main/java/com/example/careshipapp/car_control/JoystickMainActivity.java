package com.example.careshipapp.car_control;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
    private static final String LOCALHOST = "10.0.2.2";
    private static final String MQTT_SERVER = "tcp://" + LOCALHOST + ":1883";
    private static final String THROTTLE_TOPIC = "/smartcar/carcontrol/throttle";
    private static final String STEERING_TOPIC = "/smartcar/carcontrol/steering";
    private static final String CAMERA_TOPIC = "/smartcar/camera";
    private static final String ULTRASOUND_TOPIC = "/smartcar/ultrasound/front";
    private static final int MOVEMENT = 50;
    private static final int TURN = 70;
    private static final int STRAIGHT_ANGLE = 0;
    private static final int STOP_CAR = 0;
    private static final int QOS = 1;
    private static final int IMAGE_WIDTH = 320;
    private static final int IMAGE_HEIGHT = 240;

    private MqttClient mqttClient;
    private boolean isConnected = false;
    private ImageView camera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.joystick_main_activity);
        mqttClient = new MqttClient(getApplicationContext(), MQTT_SERVER, TAG);
        camera = findViewById(R.id.imageView);


        connectToMqttBroker();

    }

    @Override
    public void onJoystickMoved(float xCoordinate, float yCoordinate) {



        whileMoving(xCoordinate, yCoordinate);

    }

    void whileMoving(float x, float y){


        if(y <= -0.2){
            //go straight forward
            startMoving(adjustSpeed(-y), STRAIGHT_ANGLE);
        }
        else if(y >= 0.9){
            //go straight back
            startMoving(-MOVEMENT, STRAIGHT_ANGLE);
        }
        else if(x >= 0.2){
            //turn right and drive
            startMoving(adjustSpeed(x), TURN);
        }
        else if(x <= -0.2){
            //turn left and drive
            startMoving(adjustSpeed(-x), -TURN);
        }
        else if(y == 0 && x == 0){
            //stop the car
            stopCar();
        }
    }

    float adjustSpeed(float value){
        return value*100;
    }


    void startMoving(float throttle, int steeringDirection){
        if (!isConnected) {
            final String notConnected = "Not connected (yet)";
            Log.e(TAG, notConnected);
            Toast.makeText(getApplicationContext(), notConnected, Toast.LENGTH_SHORT).show();
            return;
        }

        mqttClient.publish(THROTTLE_TOPIC, Float.toString(throttle), QOS, null);
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

                   // mqttClient.subscribe(ULTRASOUND_TOPIC, QOS, null);
                    mqttClient.subscribe(CAMERA_TOPIC, QOS, null);
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
                    if(topic.equals(CAMERA_TOPIC)) {
                        final Bitmap bitMap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);

                        final byte[] payload = message.getPayload();
                        final int[] colors = new int[IMAGE_WIDTH * IMAGE_HEIGHT];
                        for (int ci = 0; ci < colors.length; ++ci) {
                            final int r = payload[3 * ci] & 0xFF;
                            final int g = payload[3 * ci + 1] & 0xFF;
                            final int b = payload[3 * ci + 2] & 0xFF;
                            colors[ci] = Color.rgb(r, g, b);
                        }
                        bitMap.setPixels(colors, 0, IMAGE_WIDTH, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
                        camera.setImageBitmap(bitMap);
                    }
                    else{
                        Log.i(TAG, "[MQTT] Topic: " + topic + " | Message: " + message.toString());
                        }
                }


                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Message delivered");
                }
            });
        }
    }

}




