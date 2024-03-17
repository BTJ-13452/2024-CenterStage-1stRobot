package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.robotsystems.AprilTag;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;
import org.firstinspires.ftc.teamcode.robotsystems.hangingRobot;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous
    //@Disabled
    public class Autonomus_Red extends LinearOpMode {
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

        testCamera camera;

        testCamera.CenterStageDeterminationPipeline.CSPosition random;

        AprilTag aprilTag;

        int type =2;

        @Override
        public void runOpMode() throws InterruptedException {

//        TestTeleOp.red = true;

            new ElevatorErectionSystem(hardwareMap);
            new ElevatorSystem(hardwareMap);
            new hangingRobot(hardwareMap);

            camera = new testCamera(hardwareMap, this);

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

            do{
                random = camera.pipeline.getAnalysis();
            }while (!isStarted() && !isStopRequested());

            switch (random){
                case LEFT:
                    type = 3;
                    break;
                case CENTER:
                    type = 2;
                    break;
                case RIGHT:
                    type = 1 ;
                    break;

            }

            camera.webcam.closeCameraDevice();

            aprilTag = new AprilTag(hardwareMap);


            waitForStart();

            if (type==3) {

                rotateBySeconds(0.3, 1400);
                sleep(500);
                driveSideBySeconds(-0.5, 2000);
                sleep(500);
                driveBySeconds(-0.3, 1300);
                sleep(500);
                driveSideBySecondsToAprilTag(0.4, 5, 3);
                sleep(1000);
                yks.closeErectionYks();
                sleep(500);
                // rotateBySecondsToAprilTag(0.3,5,10,12);
                driveBySecondsToAprilTag(0.3, 5, 36);
                sleep(500);
                yks.openYksRight();
                sleep(500);
                elevatorSystem.CloseElevator();
                yks.openErectionYks(0.6);
                driveBySecondsToAprilTag(-0.3,4,16);
                sleep(500);
                driveSideBySecondsToAprilTag(-0.3, 4, 2);
                elevatorErectionSystem.erectionTicks(2450);
                sleep(500);
                elevatorSystem.OpenElevator(300);
                sleep(1000);
                yks.openYksLeft();
                sleep(500);
                elevatorErectionSystem.erectionTicks(0);
                yks.closeErectionYks();
                elevatorSystem.CloseElevator();
                driveSideBySecondsToAprilTag(-0.3, 6, -3);
                driveSideBySeconds(-0.3,1000);
                drive45BySeconds(0.5, 1500);
            }
            if (type==1) {

//                rotateBySeconds(0.3, 1400);
//                sleep(500);
//                driveSideBySeconds(-0.5, 1000);
//                sleep(500);
//                driveBySeconds(-0.3, 1300);
//                sleep(500);
//                yks.closeErectionYks();
//                sleep(500);
                rotateBySeconds(0.3, 1500);
                sleep(500);
                driveSideBySeconds(-0.5, 1000);
                sleep(500);
                driveBySeconds(-0.3, 1300);
                sleep(500);
                yks.closeErectionYks();
                sleep(500);
                driveSideBySecondsToAprilTag(0.4, 5, 0);
                sleep(2000);
                // rotateBySecondsToAprilTag(0.3,5,10,12);
                driveBySecondsToAprilTag(0.3, 5, 15);
                sleep(1000);
                rotateBySecondsToAprilTag(0.2, 5, -10, 10);
                sleep(1000);
                yks.openYksRight();
                sleep(500);
                elevatorSystem.CloseElevator();
                yks.openErectionYks(0.6);
                driveSideBySecondsToAprilTag(-0.3, 6, -1);
                elevatorErectionSystem.erectionTicks(2500);
                sleep(1000);
                elevatorSystem.OpenElevator(1200);
                sleep(500);
                yks.openYksLeft();
                sleep(500);
                elevatorErectionSystem.erectionTicks(0);
                yks.closeErectionYks();
                elevatorSystem.CloseElevator();
                driveSideBySecondsToAprilTag(-0.3, 6, -3);
                driveSideBySeconds(-0.3,1000);
                drive45BySeconds(0.5, 2500);
            }

            if (type==2){
                    rotateBySeconds(0.3, 1500);
                    sleep(500);
                    driveSideBySeconds(-0.5, 1000);
                    sleep(500);
                    driveBySeconds(-0.3, 1000);
                    sleep(500);
                    driveSideBySeconds(-0.5, 1000);
                    sleep(100);
                    driveSideBySecondsToAprilTag(0.3, 4, 0);
                    sleep(500);
                    yks.closeErectionYks();
                    sleep(500);
                    elevatorSystem.OpenElevator(400);
                    // rotateBySecondsToAprilTag(0.3,5,10,12);
                    driveBySecondsToAprilTag(0.3, 4, 15);
                    sleep(1000);
                    rotateBySecondsToAprilTag(0.2, 4, -7, 7);
                    sleep(1000);
                    driveSideBySecondsToAprilTag(0.3, 4, 1);
                    sleep(500);
                    yks.openYksRight();
                    sleep(500);
                    elevatorSystem.CloseElevator();
                    driveSideBySecondsToAprilTag(-0.3, 5, 0);
                    driveBySecondsToAprilTag(-0.3, 5, 13);
                    elevatorErectionSystem.erectionTicks(2525);
                    sleep(1000);
                    yks.openErectionYks(0.6);
                    sleep(500);
                    yks.openYksLeft();
                    sleep(500);
                    elevatorErectionSystem.erectionTicks(0);
                    yks.closeErectionYks();
                    elevatorSystem.CloseElevator();
//                    driveSideBySecondsToAprilTag(-0.3, 6, -3);
                    driveSideBySeconds(-0.5,2000);
                    driveBySeconds(-0.7,500);
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
        public void driveBySecondsToAprilTag(double power, int id,double distance){
        motorBackRight.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorFrontLeft.setPower(power);
        AprilTagDetection DTA = GetDetection(id);
        telemetry.addData("fgdfg",234);
        telemetry.update();

        int mol = power>0? 1:-1;
        while(DTA==null||DTA.ftcPose.y*mol<distance*mol){
            DTA=GetDetection(id);
        }
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
    public void rotateBySecondsToAprilTag(double power, int id,double minAngle,double maxAngle){
        motorBackRight.setPower(-power);
        motorFrontRight.setPower(-power);
        motorBackLeft.setPower(power);
        motorFrontLeft.setPower(power);

        AprilTagDetection ATD =GetDetection(id);
        while(ATD==null||ATD.ftcPose.yaw>maxAngle||ATD==null||ATD.ftcPose.yaw<minAngle){
            ATD=GetDetection(id);
        }

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
    public void driveSideBySecondsToAprilTag(double power, int id,double distance){
        motorBackRight.setPower(power);
        motorFrontRight.setPower(-power);
        motorBackLeft.setPower(-power);
        motorFrontLeft.setPower(power);

        int mol = power>0? 1:-1;
        AprilTagDetection ATD =GetDetection(id);
        while(ATD==null||mol*ATD.ftcPose.x<distance*mol){
            ATD = GetDetection(id);

        }
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
        public AprilTagDetection GetDetection(int ID) {
            List<AprilTagDetection> AT = aprilTag.getAprilTagDetection();
            for (AprilTagDetection DATA : AT
            ) {
                if (DATA.id == ID) {
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", DATA.ftcPose.x, DATA.ftcPose.y, DATA.ftcPose.z));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", DATA.ftcPose.pitch, DATA.ftcPose.roll, DATA.ftcPose.yaw));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", DATA.ftcPose.range, DATA.ftcPose.bearing, DATA.ftcPose.elevation));
                    telemetry.update();
                    return DATA;
                }
            }

        return null;
        }
}
