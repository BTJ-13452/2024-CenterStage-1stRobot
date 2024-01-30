package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import android.hardware.Sensor;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.opmodes.autonomous.openCV.Processor;
import org.firstinspires.ftc.teamcode.opmodes.teleop.TestTeleOp;
import org.firstinspires.ftc.teamcode.robotsystems.AprilTag;
import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;
import org.firstinspires.ftc.teamcode.robotsystems.hangingRobot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class automomuos_R1 extends LinearOpMode {
    public DriveSystem drive;

    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, correction;

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    private ElevatorErectionSystem elvatorErectionSystem;
    private ElevatorSystem elevatorSystem;
    public ElapsedTime runTime = new ElapsedTime();

    private EndPointSystem yks;

    hangingRobot hanging ;
    Processor processor = new Processor(this);
    VisionPortal myVisionPortal;

    SensorInput aprilTag6;

    int type = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        myVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), processor);
        aprilTag6 = new SensorInput() {

            AprilTag aprilTag = new AprilTag(hardwareMap);
            @Override
            public boolean verifySensor() {
                int counter = 0;
                boolean current1 = false;
                boolean current2 = false;
                while(counter < 1 && opModeIsActive()){
                    for (AprilTagDetection detection: aprilTag.getAprilTagDetection()) {
                        if(detection.id == 6) {
                            double r = detection.ftcPose.x ;
                            telemetry.addData("X", r);
                            telemetry.update();
                            current1 = r < 0.25;
                        }
                    }
                    for (AprilTagDetection detection: aprilTag.getAprilTagDetection()) {
                        if (detection.id == 6) {
                            double r = detection.ftcPose.x;
                            current2 = r < 0.25;
                        }
                    }

                    if(current1 == current2 && current1){
                        counter++;
                    }
                    else{
                        counter = 0;
                    }
                }
                return current1;
            }
        };

        do{
            type = Processor.whichSide();
        } while (opModeInInit());


        waitForStart();
        //  drive.opModeIsActive = true;
        // drive.driveByEncoder(0.3,0,90,0);
        if (type == 1) {
            driveByEncoder(0.5, 135, 90, 90);
            driveBySensor(0.5,90,0, aprilTag6);
            sleep(1000);
            elvatorErectionSystem.LowerHeight(-0.5);
            sleep(1000);
            elevatorSystem.OpenElevator(700);
            sleep(1000);
            yks.closeErectionYks();
            sleep(1000);
            yks.openYksRight();
            sleep(1000);
            yks.openErectionYks();
            sleep(1000);
            elvatorErectionSystem.erectionTicks(2000);
            sleep(3000);
            yks.openYksLeft();
            elvatorErectionSystem.erectionTicks(0);
            telemetry.addData("1", "1");
            telemetry.update();
            yks.closeErectionYks();
            sleep(1000);
            elevatorSystem.CloseElevator();
            sleep(1000);
            driveByEncoder(0.5, 110, 60, 90);

        } else if (type == 2) {
            driveByEncoder(0.5, 135, 100, 90);
            sleep(1000);
            elvatorErectionSystem.LowerHeight(-0.5);
            sleep(1000);

            driveByEncoder(0.5, 0, 55, 95);
            sleep(1000);
            sleep(1000);
            yks.closeErectionYks();
            sleep(1000);
            yks.openYksRight();
            sleep(1000);
            yks.openErectionYks();
            sleep(1000);
            driveByEncoder(0.5, 195, 30, 90);
            sleep(1000);

            elvatorErectionSystem.erectionTicks(2000);
            sleep(3000);
            yks.openYksLeft();
            elvatorErectionSystem.erectionTicks(0);
            telemetry.addData("1", "1");
            telemetry.update();
            yks.closeErectionYks();
            sleep(1000);
            elevatorSystem.CloseElevator();
            sleep(1000);
            driveByEncoder(0.5, 110, 40, 90);
        } else if (type == 3) {
            driveByEncoder(0.5, 135, 85, 90);
            sleep(1000);
            driveByEncoder(0.5, 0, 40, 90);
            elvatorErectionSystem.LowerHeight(-0.5);
            sleep(1000);
            elevatorSystem.OpenElevator(250);
            sleep(1000);
            yks.closeErectionYks();
            sleep(1000);
            yks.openYksRight();
            sleep(1000);
            yks.openErectionYks();
            sleep(1000);
            driveByEncoder(-0.5, 0, 40 , 90);
            sleep(1000);
            driveByEncoder(0.5, 200, 30, 90);
            sleep(1000);
            elvatorErectionSystem.erectionTicks(2000);
            sleep(3000);
            yks.openYksLeft();
            elvatorErectionSystem.erectionTicks(0);
            telemetry.addData("1", "1");
            telemetry.update();
            yks.closeErectionYks();
            sleep(1000);
            elevatorSystem.CloseElevator();
            sleep(1000);
            driveByEncoder(0.5, 110, 30, 90);
        }


//        sleep(1000);
//       elvatorErectionSystem.erectionTicks(2400);
//        sleep(1000);
//        if (elvatorErectionSystem.getPosition()>2399){
//            yks.openYksRight();
//
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();
        while (opModeIsActive()) {
        }
        sleep(10000);


    }

    public void Init() {
        //  drive = new DriveSystem(hardwareMap);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        motorBackLeft = hardwareMap.dcMotor.get("DL");
        motorBackRight = hardwareMap.dcMotor.get("DR");
        motorFrontLeft = hardwareMap.dcMotor.get("UL");
        motorFrontRight = hardwareMap.dcMotor.get("UR");

        TestTeleOp.driveSystem = new DriveSystem(hardwareMap,gamepad1,180);


        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
//        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        yks = new EndPointSystem(hardwareMap);

        elvatorErectionSystem = new ElevatorErectionSystem(hardwareMap);

        elevatorSystem = new ElevatorSystem(hardwareMap);

        hanging = new hangingRobot(hardwareMap);
    }

//    public void driveByEncoder(double power, double angleDegrees, int CM, int angleAheadDegrees) {
//        // 9.6 CM Koter
//        // 9.6 * PI = Hekef
//        // Hekef = 30.16
//        // encoder for one round = 537.6
//        // (30.16 / 537.6) * encoder = CM
//        // 0.0561 * encoder = CM
//        // encoder = CM / 0.0561
//        double encoder = CM / 0.0561;
//
//        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
////        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
////        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
////        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        runTime.reset();
//
//        double y = -(power * Math.sin(Math.toRadians(-angleDegrees) + Math.PI / 2));
//        double x = -(power * Math.cos(Math.toRadians(-angleDegrees) + Math.PI / 2));
//        double rx = -(angleAheadDegrees - getCurrentAngleFromIMU()) * 0.01;
////         Denominator is the largest motor power (absolute value) or 1
////         This ensures all the powers maintain the same ratio, but only when
////         at least one is out of the range [-1, 1]
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//        double frontLeftPower = (y + x + rx) / denominator;
//        double backLeftPower = (y - x + rx) / denominator;
//        double frontRightPower = (y - x - rx) / denominator;
//        double backRightPower = (y + x - rx) / denominator;
//
//        double frontRightEncoder = encoder;
//        double backRightEncoder = encoder;
//
//        boolean motorFrontRightEncoderIsNotFinished = Math.abs(motorFrontRight.getCurrentPosition()) < frontRightEncoder;
////        boolean motorBackRightEncoderIsNotFinished = Math.abs(motorBackRight.getCurrentPosition()) < backRightEncoder;
//
//
////        while (motorFrontRightEncoderIsNotFinished || motorBackRightEncoderIsNotFinished) {
//        while (motorFrontRightEncoderIsNotFinished) {
//
//            drive(power, angleDegrees, angleAheadDegrees);
////            rx = -(angleAheadDegrees - getCurrentAngleFromIMU()) * 0.01;
////            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//////
////            if (motorFrontRight.getCurrentPosition() == 0 && motorBackRight.getCurrentPosition() == 0 && runTime.seconds() > 1) {
////                stopRobot();
////            }
////            frontLeftPower = (y + x + rx) / denominator;
////            backLeftPower = (y - x + rx) / denominator;
////            frontRightPower = (y - x - rx) / denominator;
////            backRightPower = (y + x - rx) / denominator;
////
////            motorFrontLeft.setPower(frontLeftPower);
////            motorBackLeft.setPower(backLeftPower);
////            motorFrontRight.setPower(frontRightPower);
////            motorBackRight.setPower(backRightPower);
//            motorFrontRightEncoderIsNotFinished = Math.abs(motorFrontRight.getCurrentPosition()) < frontRightEncoder;
//        }
//        stopRobot();
//    }

    private void stopRobot() {
        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackRight.setPower(0);
    }

    public double getCurrentAngleFromIMU() {


        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180) {

            deltaAngle += 360;

        } else if (deltaAngle > 180) {

            deltaAngle -= 360;
        }

        globalAngle += deltaAngle;

        lastAngles = angles;

        return -globalAngle;
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

    public void driveByEncoder(double power, double angleDegrees, double CM, int angleAheadDegrees) {
        // 9.6 CM diameter
        // 9.6 * PI = perimeter
        // perimeter = 30.16
        // encoder for one round = 384.5
        // (30.16 / 384.5) * encoder = CM
        // 0.0784 * encoder = CM
        // encoder = CM / 0.0561
        double encoder = CM / 0.0784;

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runTime.reset();
        double y = -(power * Math.sin(Math.toRadians(-angleDegrees) + Math.PI / 2));
        double x = -(power * Math.cos(Math.toRadians(-angleDegrees) + Math.PI / 2));
        double rx = -(angleAheadDegrees - getAngle()) * 0.01;
        //Denominator is the largest motor power (absolute value) or 1
        //This ensures all the powers maintain the same ratio, but only when
        //at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        double frontRightEncoder = encoder;
        double backRightEncoder = encoder;


        while ((Math.abs(motorFrontRight.getCurrentPosition()) < frontRightEncoder || Math.abs(motorBackRight.getCurrentPosition()) < backRightEncoder) && opModeIsActive()) {
            rx = -(angleAheadDegrees - getAngle()) * 0.01;
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            if ((motorFrontRight.getCurrentPosition() == 0 && motorBackRight.getCurrentPosition() == 0 && runTime.seconds() > 1) && opModeIsActive()) {
                motorFrontLeft.setPower(0);
                motorBackLeft.setPower(0);
                motorFrontRight.setPower(0);
                motorBackRight.setPower(0);
            }
            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);
        }

        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackRight.setPower(0);
    }

    public double getAngle() {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;
        lastAngles = angles;
        return -globalAngle;
    }

    public void driveBySensor(double power, double angleDegrees, int angleAheadDegrees, SensorInput sensor) {
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runTime.reset();

        double y = -(power * Math.sin(Math.toRadians(-angleDegrees) + Math.PI / 2));
        double x = -(power * Math.cos(Math.toRadians(-angleDegrees) + Math.PI / 2));
        double rx = -(angleAheadDegrees - getAngle()) * 0.01;
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        while (sensor.verifySensor() && opModeIsActive()) {
            rx = -(angleAheadDegrees - getAngle()) * 0.01;
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            if (motorFrontRight.getCurrentPosition() == 0 && motorBackRight.getCurrentPosition() == 0 && runTime.seconds() > 1) {
                motorFrontLeft.setPower(0);
                motorBackLeft.setPower(0);
                motorFrontRight.setPower(0);
                motorBackRight.setPower(0);
            }
            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);
        }

        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackRight.setPower(0);
    }

    private interface SensorInput{
        public boolean verifySensor();
    }
}
