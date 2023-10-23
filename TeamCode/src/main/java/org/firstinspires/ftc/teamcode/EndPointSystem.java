package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class EndPointSystem {

    CRServo right;
    CRServo left;

    public EndPointSystem(HardwareMap map) {
        right = map.crservo.get("right");
        left = map.crservo.get("left");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void sucking(){
        if(right.getPower() > 0){
            right.setPower(0);
            left.setPower(0);
        }else{
            right.setPower(1);
            left.setPower(1);
        }
    }
    public void spitting(){
        if(right.getPower() < 0){
            right.setPower(0);
            left.setPower(0);
        }else{
            right.setPower(-1);
            left.setPower(-1);
        }
    }

}
