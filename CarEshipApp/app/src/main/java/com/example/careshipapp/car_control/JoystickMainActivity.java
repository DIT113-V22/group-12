package com.example.careshipapp.car_control;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.R;

public class JoystickMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoystickFunctionality joystick = new JoystickFunctionality(this);
        setContentView(R.layout.joystick_main_activity);
    }
}
