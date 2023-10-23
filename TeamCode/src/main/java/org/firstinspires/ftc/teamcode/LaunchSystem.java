package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LaunchSystem {
    Servo launch;


    public LaunchSystem(HardwareMap map){
        launch = map.servo.get("launch");
        launch.setPosition(0);
    }
    public void LaunchP(){
        launch.setPosition(1);
    }
}
