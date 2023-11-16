package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class YKS {

    Servo yksRight ;
    Servo yksLeft ;

    public YKS(HardwareMap Map) {
        yksRight = Map.servo.get("yksRight");
        yksLeft = Map.servo.get("yksLeft");
        yksRight.setPosition(1);
        yksLeft.setPosition(0);
    }

    public void openYksRight (){
        yksRight.setPosition(0);
    }

    public void closeYksRight (){
        yksRight.setPosition(1);
    }

    public void openYksLeft (){
        yksLeft.setPosition(1);
    }

    public void closeYksLeft (){
        yksLeft.setPosition(0);
    }
}
