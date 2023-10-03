package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.Random;

public class ElevatorRotator {
    DcMotor right;
    DcMotor left;
    TouchSensor zeroPositionSensor;

    final double POWER_TO_ZERO = -0.5;
    final int UP_TO_FINAL = 1234;
    public ElevatorRotator (HardwareMap map) {
        right = map.dcMotor.get("ERR");
        left = map.dcMotor.get("ERL");
        left.setDirection(DcMotorSimple.Direction.REVERSE);
//        zeroPositionSensor = map.get(TouchSensor.class, "zeroPositionSensor");
    }
    public void RotateByPower(double power) {
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setPower(power);
        left.setPower(power);
    }

    public void goToZero(){
        while (!zeroPositionSensor.isPressed()){
            RotateByPower(POWER_TO_ZERO);
        }
       RotateByPower(0);
    }
    public void finalPosition(){
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setTargetPosition(UP_TO_FINAL);
        left.setTargetPosition(UP_TO_FINAL);

        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        right.setPower(1);
        left.setPower(1);
    }
}
