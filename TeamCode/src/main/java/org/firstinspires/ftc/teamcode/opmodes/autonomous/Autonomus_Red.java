package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.robotsystems.AprilTag;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;
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

      //  testCamera camera;

        testCamera.CenterStageDeterminationPipeline.CSPosition random;

        AprilTag aprilTag;



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
            aprilTag = new AprilTag(hardwareMap);

//            do{
//                random = camera.pipeline.getAnalysis();
//
//                telemetry.addData("Random: ", random);
//                telemetry.update();
//            }while (!isStarted() && !isStopRequested());

            waitForStart();

            if (true||random == testCamera.CenterStageDeterminationPipeline.CSPosition.RIGHT ||random == testCamera.CenterStageDeterminationPipeline.CSPosition.LEFT){

                rotateBySeconds(0.3, 1500);
                driveSideBySeconds(-0.5, 1000);
                driveBySeconds(-0.3, 1000);
                driveSideBySecondsToAprilTag(0.3, 5, 1);
                sleep(500);
                rotateBySecondsToAprilTag(0.3,5,90,180);
                yks.closeErectionYks();
                sleep(20000);
                rotateBySeconds(0.3, 160);
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
                driveSideBySeconds(-0.3, 1000);
                drive45BySeconds(0.7, 2000);

            } else if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.LEFT) {

                rotateBySeconds(0.3,1500);
                driveSideBySeconds(-0.5,1000);
                yks.openYksRight();
                sleep(500);
                driveBySeconds(0.5,1000);
                elevatorSystem.Extension();
                elevatorErectionSystem.erectionTicks(400);
                sleep(500);
                yks.openYksLeft();
//            driveBySeconds(-0.3, 1400);
//            yks.closeErectionYks();
//            sleep(500);
//            rotateBySeconds(0.3, 2200);
//            yks.openYksRight();
//            sleep(500);
//            //driveSideBySeconds(-0.3, 300);
//
//            sleep(30000);



            }
            else if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.CENTER) {

                driveBySeconds(-5,1600);
                yks.closeErectionYks();
                sleep(500);
                elevatorErectionSystem.erectionTicks(1700);
                sleep(500);
                yks.openYksRight();
                sleep(500);
                rotateBySeconds(0.3,1500);
                driveBySeconds(-0.5,1500);
                yks.openYksLeft();
//            driveBySeconds(-0.3, 1600);
//            yks.closeErectionYks();
//            sleep(500);
//            rotateBySeconds(0.3, 2700);
//            driveBySeconds(0.3,100);
//            yks.openYksRight();
//            sleep(500);
                //driveSideBySeconds(-0.3, 300);
            }

//
//        yks.closeErectionYks();
//
//        sleep(2000);
//
//        yks.openYksLeft();
//        yks.openYksRight();
//
//        yks.openErectionYks();

//        if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.RIGHT) {
//
//            driveBySeconds(-0.3, 1700);
//            rotateBySeconds(-0.3, 1500);
//            driveSideBySeconds(0.3,1000);
//            yks.closeErectionYks();
//            sleep(500);
//            driveBySeconds(0.3, 600);
//            yks.openYksRight();
//            sleep(500);
//            //driveSideBySeconds(-0.3, 300);
//        }
//
//        if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.CENTER) {
//            driveBySeconds(-0.3, 1400);
//            yks.closeErectionYks();
//            sleep(500);
//            rotateBySeconds(0.3, 1800);
//            driveBySeconds(0.3,400);
//            yks.openYksRight();
//            sleep(500);
//            //driveSideBySeconds(-0.3, 300);
//
//            sleep(30000);
//        }
//        if (random == testCamera.CenterStageDeterminationPipeline.CSPosition.LEFT) {
//
//            driveBySeconds(-0.3, 1700);
//            rotateBySeconds(-0.3, 1500);
//            driveSideBySeconds(0.3,1000);
//            yks.closeErectionYks();
//            sleep(500);
//            yks.openYksRight();
//            sleep(500);
//            //driveSideBySeconds(-0.3, 300);
//        }
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

        while(GetDetection(id)==null||GetDetection(id).rawPose.x>distance){

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
