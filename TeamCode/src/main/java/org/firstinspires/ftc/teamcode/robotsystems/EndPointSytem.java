package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class EndPointSytem {

    final static double MIN_ERECTION_POSITION = 0.38;
    final static double MAX_ERECTION_POSITION = 0.95;
    final static double RIGHT_OPEN_POSITION = 0.6;
    final static double LEFT_OPEN_POSITION = 0.65;

    Servo yksRight;
    Servo yksLeft;
    Servo erectionYks;


    public EndPointSytem(HardwareMap Map) {
        yksRight = Map.servo.get("yksRight");
        yksLeft = Map.servo.get("yksLeft");
        erectionYks = Map.servo.get("erectionYks");

        erectionYks.setPosition(MIN_ERECTION_POSITION);
        yksRight.setPosition(1);
        yksLeft.setPosition(0);
    }
    public void openErectionYks(){
        erectionYks.setPosition(MAX_ERECTION_POSITION);
    }
    public void closeErectionYks(){
        erectionYks.setPosition(MIN_ERECTION_POSITION);
    }

    public void openYksRight (){
        yksRight.setPosition(RIGHT_OPEN_POSITION);
    }

    public void closeYksRight (){
        yksRight.setPosition(1);
    }

    public void openYksLeft (){
        yksLeft.setPosition(LEFT_OPEN_POSITION);
    }

    public void closeYksLeft (){
        yksLeft.setPosition(0);
    }

    public void  YksLactive() {
        if (yksLeft.getPosition() == 0){
            openYksLeft();
        } else{
            closeYksLeft();
        }
    }
    public void  YksRactive() {
        if (yksRight.getPosition() == 1){
            openYksRight();
        } else {
            closeYksRight();
        }
    }
}
