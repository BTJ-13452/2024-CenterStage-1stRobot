package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;
@Autonomous

public class test_2_odometry_R1 extends LinearOpMode {
    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    EndPointSystem yks;

    ElevatorErectionSystem elevatorErectionSystem;

    ElevatorSystem elevatorSystem;

    DriveSystem drive ;
    ElevatorErectionSystem elvatorErectionSystem;

    @Override
    public void runOpMode() throws InterruptedException {
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

        elevatorSystem = new ElevatorSystem(hardwareMap);
        elevatorErectionSystem = new ElevatorErectionSystem(hardwareMap);
        yks = new EndPointSystem(hardwareMap);
        yks.closeYksRight();
        yks.closeYksLeft();

        int random = 1;
        drive = new DriveSystem(hardwareMap,gamepad1,0);
        waitForStart();


        sleep(100);
//        rotateBySeconds(0.3,1500);
        sleep(100);


        drive.driveByOdometry(50,0,0, telemetry,0.5);


        while (opModeIsActive()) {
        }
    }




    public void rotateBySeconds(double power, int millSeconds){
        motorBackRight.setPower(-power);
        motorFrontRight.setPower(-power);
        motorBackLeft.setPower(power);
        motorFrontLeft.setPower(power);

        sleep(millSeconds);
        stopDrive();
    }
    public void stopDrive(){
        motorBackRight.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontLeft.setPower(0);

        sleep(100);
    }
}