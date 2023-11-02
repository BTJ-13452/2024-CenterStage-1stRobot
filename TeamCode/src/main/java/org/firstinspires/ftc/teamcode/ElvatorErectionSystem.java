package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ElvatorErectionSystem {

    final int[] positions =new int[]{0,20,40,60};
    final double power = 1;
    int position = 0;
    DcMotor Lm;
    DcMotor Rm;
    TouchSensor magneticSwitch;
    public ElvatorErectionSystem(HardwareMap map){
        Lm = map.dcMotor.get("lm");
        Rm = map.dcMotor.get("rm");
        Lm.setDirection(DcMotorSimple.Direction.REVERSE);

        magneticSwitch = map.get(TouchSensor.class, "erectionSwitch");

        LowerHeight();

        Lm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Rm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Lm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Rm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void elevatorHeight(boolean up, boolean down){

        if(up) {
            position++;
            if(position >= positions.length){
                position = positions.length - 1;
            }
        }
        if(down){
            position--;
            if(position < 0){
                position = 0;
            }
        }
        Lm.setTargetPosition(positions[position]);
        Rm.setTargetPosition(positions[position]);

        Lm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Rm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Lm.setPower(power);
        Rm.setPower(power);
    }


    public void LowerHeight(){
       while (!magneticSwitch.isPressed()) {
           Lm.setPower(power);
           Rm.setPower(power);
       }
       Lm.setPower(0);
       Rm.setPower(0);
    }
}
