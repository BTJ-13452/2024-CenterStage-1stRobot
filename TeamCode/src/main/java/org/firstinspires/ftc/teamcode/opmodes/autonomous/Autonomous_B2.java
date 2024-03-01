package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.opmodes.teleop.TestTeleOp;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;

@Autonomous
@Disabled
public class Autonomous_B2 extends LinearOpMode {
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, correction;

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    EndPointSystem yks;

    int type = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        new ElevatorErectionSystem(hardwareMap);
        new ElevatorSystem(hardwareMap);

//        TestTeleOp.red = false;
        motorBackLeft = hardwareMap.dcMotor.get("DL");
        motorBackRight = hardwareMap.dcMotor.get("DR");
        motorFrontLeft = hardwareMap.dcMotor.get("UL");
        motorFrontRight = hardwareMap.dcMotor.get("UR");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);



        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        yks = new EndPointSystem(hardwareMap);
        yks.closeYksRight();
        yks.closeYksLeft();

        waitForStart();

        motorBackLeft.setPower(0.5);
        motorBackRight.setPower(-0.5);
        motorFrontLeft.setPower(-0.5);
        motorFrontRight.setPower(0.5);

        sleep(115);

        motorBackRight.setPower(-0.5);
        motorFrontRight.setPower(-0.5);
        motorBackLeft.setPower(-0.5);
        motorFrontLeft.setPower(-0.5);



        sleep(100);

        motorBackRight.setPower(-0.5);
        motorFrontRight.setPower(-0.5);
        motorBackLeft.setPower(-0.5);
        motorFrontLeft.setPower(-0.5);

        sleep(1800);

        yks.closeErectionYks();

        sleep(2000);

        yks.openYksLeft();
        yks.openYksRight();

        yks.openErectionYks();
        sleep(3000);

    }
}
