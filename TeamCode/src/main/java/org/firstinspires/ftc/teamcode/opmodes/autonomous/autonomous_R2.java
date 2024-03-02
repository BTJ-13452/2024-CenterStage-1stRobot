package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.opmodes.teleop.TestTeleOp;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;

@Autonomous
//@Disabled
public class autonomous_R2 extends LinearOpMode {
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, correction;

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    EndPointSystem yks;

    ElevatorErectionSystem elevatorErectionSystem;

    ElevatorSystem elevatorSystem;

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

        elevatorSystem = new ElevatorSystem(hardwareMap);
        elevatorErectionSystem = new ElevatorErectionSystem(hardwareMap);
        yks = new EndPointSystem(hardwareMap);
        yks.closeYksRight();
        yks.closeYksLeft();

        int random = 2;
        waitForStart();

        rotateBySeconds(0.3,1500);
        driveSideBySeconds(-0.5, 1000);
        driveBySeconds(-0.3, 1000);

        if(random == 1) {
            driveSideBySeconds(0.3, 3800);
            yks.closeErectionYks();
            sleep(500);
            rotateBySeconds(0.3,160);
            elevatorSystem.OpenElevator(80);
            yks.openYksRight();
            sleep(500);
            elevatorErectionSystem.erectionTicks(2700);
            sleep(2000);
            yks.openErectionYks(0.6);
            elevatorSystem.OpenElevator(1200);
            sleep(500);
            yks.openYksLeft();
            sleep(500);
            elevatorErectionSystem.erectionTicks(0);
            yks.closeErectionYks();
            elevatorSystem.CloseElevator();
            driveSideBySeconds(-0.3,1000);
            drive45BySeconds(0.7,2000);
        }
        else if (random == 2 ){
            driveSideBySeconds(0.3, 4000);
            yks.closeErectionYks();
            sleep(500);
            yks.openYksRight();
            sleep(500);
            driveSideBySeconds(-0.3, 300);
        }
        sleep(30000);
//
//        yks.closeErectionYks();
//
//        sleep(2000);
//
//        yks.openYksLeft();
//        yks.openYksRight();
//
//        yks.openErectionYks();
    }

    public void driveBySeconds(double power, int millSeconds){
        motorBackRight.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorFrontLeft.setPower(power);

        sleep(millSeconds);
        stopDrive();
    }

    public void rotateBySeconds(double power, int millSeconds){
        motorBackRight.setPower(-power);
        motorFrontRight.setPower(-power);
        motorBackLeft.setPower(power);
        motorFrontLeft.setPower(power);

        sleep(millSeconds);
        stopDrive();
    }
    public void driveSideBySeconds(double power, int millSeconds){
        motorBackRight.setPower(power);
        motorFrontRight.setPower(-power);
        motorBackLeft.setPower(-power);
        motorFrontLeft.setPower(power);

        sleep(millSeconds);
        stopDrive();
    }
    public void drive45BySeconds(double power, int millSeconds){
        motorBackRight.setPower(-power);
        motorFrontLeft.setPower(-power);
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
