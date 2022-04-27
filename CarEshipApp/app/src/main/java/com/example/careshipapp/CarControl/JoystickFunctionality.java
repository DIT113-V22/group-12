package com.example.careshipapp.CarControl;
// code adapted from https://github.com/efficientisoceles/JoystickView/blob/master/app/src/main/java/com/ravensoft/daniel/joysticktest/JoystickView.java
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import androidx.annotation.NonNull;

public class JoystickFunctionality extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private float cenX;
    private float cenY;
    private float lowerCircleRadius;
    private float upperCircleRadius;

    private void dimensions(){
        cenX = (float) getWidth() / 2;
        cenY = (float) getHeight() / 2;
        lowerCircleRadius =  (float) Math.min(getWidth(), getHeight()) / 3; // size of the lower circle of the joystick(the higher the number, the smaller the size of the circle.
        upperCircleRadius = (float) Math.min(getWidth(), getHeight()) / 7; // the same as above, but the size of the upper circle.
        
    }


    public JoystickFunctionality(Context context) {
        
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }



    public JoystickFunctionality(Context context, AttributeSet attributes) {
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public JoystickFunctionality(Context context, AttributeSet attributes, int style) {
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        dimensions();
        joystickDesign(cenX, cenY);

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }


    private void joystickDesign(float x, float y){
        if(getHolder().getSurface().isValid()){

            Canvas canvas = this.getHolder().lockCanvas();// creating the canvas for the screen.
            Paint color = new Paint();//initializing the color variable.
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // make the joystick's trace transparent.

            color.setARGB(80, 80,100,20);//color scheme of the lower circle.
            canvas.drawCircle(cenX, cenY, lowerCircleRadius, color);// add the changes onto the canvas.
            color.setARGB(255, 255,255,255);//color scheme of the upper circle.
            canvas.drawCircle(x, y, upperCircleRadius, color);
            getHolder().unlockCanvasAndPost(canvas);// update the changes

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(view.equals(this)){
            if(motionEvent.getAction() != MotionEvent.ACTION_UP) {

                float displacement = (float) Math.sqrt((Math.pow(motionEvent.getX() - cenX, 2)) + Math.pow(motionEvent.getY() - cenY, 2));// change of the object's movement relative to its initial position.
                if (displacement < lowerCircleRadius) { //if the displacement value is lower than the lower circle's value, then
                    joystickDesign(motionEvent.getX(), motionEvent.getY());
                } else {// constrain the position of the joystick when it is out of the lower circle. It would stop moving when the joystick's(upper circle) center will level with the lower circle's boundary.
                    float ratio = lowerCircleRadius / displacement;
                    float constrainedX = cenX + (motionEvent.getX() - cenX) * ratio;// formula for constraining the upper circle within the lower circle along the X axis.
                    float constrainedY = cenY + (motionEvent.getY() - cenY) * ratio;// formula for constraining the upper circle within the lower circle along the Y axis.
                    joystickDesign(constrainedX, constrainedY);
                }
            }
            else{
               joystickDesign(cenX, cenY);
            }
        }
        return true;
    }
}
