package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class NoFildoDriveSystem {

    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    Gamepad gamepad;
    double driverAngle;
    double angle;
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();

    double power;

    double rotation;


    public NoFildoDriveSystem(HardwareMap map, Gamepad gamepad){
        motorFrontLeft = map.get(DcMotor.class, "UR");
        motorBackLeft = map.get(DcMotor.class, "DR");
        motorFrontRight = map.get(DcMotor.class, "DL");
        motorBackRight = map.get(DcMotor.class, "UL");
        this.gamepad = gamepad;

        driverAngle = 0;
    }

    public void drive() {



        power = Math.sqrt(Math.pow(gamepad.left_stick_y, 2) + Math.pow(gamepad.left_stick_x, 2));

        driverAngle = Math.atan(-gamepad.left_stick_y / gamepad.left_stick_x);

        rotation = gamepad.right_stick_x;


        if (Double.isNaN(driverAngle)) {

            driverAngle = 0;
        }


        if (gamepad.left_stick_x < 0) {

            driverAngle += Math.PI;
        }


        angle = driverAngle;

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
