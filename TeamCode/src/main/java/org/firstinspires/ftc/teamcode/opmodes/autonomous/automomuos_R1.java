package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.opmodes.teleop.TestTeleOp;
import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;

@Autonomous
//@Disabled
public class automomuos_R1 extends LinearOpMode {


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
//        TestTeleOp.red = true;

        new ElevatorErectionSystem(hardwareMap);
        new ElevatorSystem(hardwareMap);

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

        waitForStart();

        motorBackLeft.setPower(-0.5);
        motorBackRight.setPower(-0.5);
        motorFrontLeft.setPower(-0.5);
        motorFrontRight.setPower(-0.5);

        sleep(3000);

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);

        yks.openYksLeft();
        yks.openYksRight();

        sleep(3000);
    }
}
