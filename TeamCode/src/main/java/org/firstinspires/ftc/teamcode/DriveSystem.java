package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSystem {
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    public DriveSystem(HardwareMap map) {
        motorFrontLeft = map.dcMotor.get("UL");
        motorBackLeft = map.dcMotor.get("DL");
        motorFrontRight = map.dcMotor.get("UR");
        motorBackRight = map.dcMotor.get("DR");
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(double power, double angle, double rotation) {

        double y = -(power * Math.sin(angle));
        double x = -(power * Math.cos(angle));
        double rx = -rotation;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        motorFrontLeft.setPower(frontLeftPower);
        motorBackLeft.setPower(backLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackRight.setPower(backRightPower);
    }
}
