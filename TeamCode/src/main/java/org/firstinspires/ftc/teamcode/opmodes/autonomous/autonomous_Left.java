package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;

@Autonomous
@Disabled
public class autonomous_Left extends LinearOpMode {
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

    testCamera camera ;

    testCamera.CenterStageDeterminationPipeline.CSPosition random;


    @Override
    public void runOpMode() throws InterruptedException {

//        TestTeleOp.red = true;

        new ElevatorErectionSystem(hardwareMap);
        new ElevatorSystem(hardwareMap);

       // camera = new testCamera(hardwareMap, this);
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

        do {


            telemetry.addData("Random: ", random);
            telemetry.update();
        } while (!isStarted() && !isStopRequested());

        waitForStart();


        if (testCamera.CenterStageDeterminationPipeline.getPosition() == 1) {


            driveSideBySeconds(0.5, 1000);
            sleep(500);
            elevatorErectionSystem.erectionTicks(1400);
            sleep(500);
            driveSideBySeconds(-0.5,1500);
            elevatorSystem.Extension();
            sleep(500);
            yks.closeYksLeft();
        }
        else if (testCamera.CenterStageDeterminationPipeline.getPosition() == 2) {

            driveBySeconds(-0.5,1500);
            elevatorErectionSystem.erectionTicks(1400);
            sleep(500);
            elevatorSystem.Extension();
            sleep(500);
            yks.openYksLeft();

        }
        else if (testCamera.CenterStageDeterminationPipeline.getPosition() == 3) {
            driveSideBySeconds(-0.5, 1000);
            sleep(500);
            elevatorErectionSystem.erectionTicks(1400);
            sleep(500);
            driveSideBySeconds(-0.5,1500);
            elevatorSystem.Extension();
            sleep(500);
            yks.closeYksLeft();
        }

        // rotateBySeconds(0.3,1500);
        // driveSideBySeconds(-0.5, 1000);
        // driveBySeconds(-0.3, 1000);

//        if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.RIGHT) {
//            rotateBySeconds(0.3,1500);
//            driveSideBySeconds(-0.5, 1000);
//            driveBySeconds(-0.3, 1000);
//            driveSideBySeconds(0.3, 3600);
//            yks.closeErectionYks();
//            sleep(500);
//            rotateBySeconds(0.3, 160);
//            elevatorSystem.OpenElevator(80);
//            yks.openYksRight();
//            sleep(500);
//            elevatorErectionSystem.erectionTicks(2700);
//            sleep(2000);
//            yks.openErectionYks(0.6);
//            elevatorSystem.OpenElevator(1200);
//            sleep(500);
//            yks.openYksLeft();
//            sleep(500);
//            elevatorErectionSystem.erectionTicks(0);
//            yks.closeErectionYks();
//            elevatorSystem.CloseElevator();
//            driveSideBySeconds(-0.3, 1000);
//            drive45BySeconds(0.7, 2000);
//        } else if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.LEFT) {
//
//            driveBySeconds(-0.3, 1400);
//            yks.closeErectionYks();
//            sleep(500);
//            rotateBySeconds(0.3, 2200);
//            yks.openYksRight();
//            sleep(500);
//            //driveSideBySeconds(-0.3, 300);
//
//            sleep(30000);
//        }
//        if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.CENTER) {
//
//            driveBySeconds(-0.3, 1600);
//            yks.closeErectionYks();
//            sleep(500);
//            rotateBySeconds(0.3, 2700);
//            driveBySeconds(0.3,100);
//            yks.openYksRight();
//            sleep(500);
//            //driveSideBySeconds(-0.3, 300);
//        }

//
//        yks.closeErectionYks();
//
//        sleep(2000);
//
//        yks.openYksLeft();
//        yks.openYksRight();
//
//        yks.openErectionYks();

        if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.RIGHT) {

            driveBySeconds(-0.3, 1700);
            rotateBySeconds(-0.3, 1500);
            driveSideBySeconds(0.3, 1000);
            yks.closeErectionYks();
            sleep(500);
            driveBySeconds(0.3, 600);
            yks.openYksRight();
            sleep(500);
            //driveSideBySeconds(-0.3, 300);
        }

        if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.CENTER) {
            driveBySeconds(-0.3, 1400);
            yks.closeErectionYks();
            sleep(500);
            rotateBySeconds(0.3, 1800);
            driveBySeconds(0.3, 400);
            yks.openYksRight();
            sleep(500);
            //driveSideBySeconds(-0.3, 300);

            sleep(30000);
        }
        if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.LEFT) {

            driveBySeconds(-0.3, 1700);
            rotateBySeconds(-0.3, 1500);
            driveSideBySeconds(0.3, 1000);
            yks.closeErectionYks();
            sleep(500);
            yks.openYksRight();
            sleep(500);
            //driveSideBySeconds(-0.3, 300);
        }
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
