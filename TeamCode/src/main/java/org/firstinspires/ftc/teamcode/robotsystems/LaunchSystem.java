package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LaunchSystem {
    CRServo launch;


    public LaunchSystem(HardwareMap map){
        launch = map.crservo.get("launch");

        launch.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void Activate(){
        if (launch.getPower()==0){
            launch.setPower(1);
        } else if(launch.getPower()==1){
            launch.setPower(0);
        }
    }

}
