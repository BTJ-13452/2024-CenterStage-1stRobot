package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class YKS {

    Servo yksRight ;
    Servo yksLeft ;
    Servo erectionYks ;


    public YKS(HardwareMap Map) {
        yksRight = Map.servo.get("yksRight");
        yksLeft = Map.servo.get("yksLeft");
        erectionYks = Map.servo.get("erectionYks");

        erectionYks.setPosition(0.18);
        yksRight.setPosition(1);
        yksLeft.setPosition(0);
    }
    public void openErectionYks(){
        erectionYks.setPosition(erectionYks.getPosition() + 0.1);
    }
    public void closeErectionYks(){
        erectionYks.setPosition(0);
    }
    public void openYksRight (){
        yksRight.setPosition(0.7);
    }

    public void closeYksRight (){
        yksRight.setPosition(1);
    }

    public void openYksLeft (){
        yksLeft.setPosition(0.5);
    }

    public void closeYksLeft (){
        yksLeft.setPosition(0);
    }
}
