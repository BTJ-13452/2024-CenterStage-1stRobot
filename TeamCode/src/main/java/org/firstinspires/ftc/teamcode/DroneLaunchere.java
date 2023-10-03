package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DroneLaunchere {
    public Servo launchere;
    public DroneLaunchere(HardwareMap map){
//        launchere=map.servo.get("Drone");
    }
    public void launche (){
        launchere.setPosition(1);
    }
}
