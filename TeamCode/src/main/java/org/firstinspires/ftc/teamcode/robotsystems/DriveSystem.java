package org.firstinspires.ftc.teamcode.robotsystems;


import static android.os.SystemClock.sleep;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class DriveSystem{
   private DcMotor leftFrontDrive;
   private DcMotor leftBackDrive;
   private DcMotor rightFrontDrive;
   private DcMotor rightBackDrive;
    ElapsedTime runTime = new ElapsedTime();
    Orientation lastAngles = new Orientation();
    double globalAngle;
BNO055IMU imu;
Orientation angles = new Orientation();
public double initYaw;
double adjustedYaw;

Gamepad gamepad;

public boolean opModeIsActive = false;

    System_Odometry odometry;

    Telemetry telemetry;

    public DriveSystem(HardwareMap map, Gamepad gamepad, double startAngle) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = map.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        initYaw = angles.firstAngle + startAngle;

        odometry = new System_Odometry(map);



        rightFrontDrive = map.get(DcMotor.class, "UR");
        rightBackDrive = map.get(DcMotor.class, "DR");
        leftBackDrive = map.get(DcMotor.class, "DL");
        leftFrontDrive = map.get(DcMotor.class, "UL");

        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);

        this.gamepad = gamepad;
    }
    public void Drive() {

        angles=imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double zeroDYaw = initYaw+angles.firstAngle;

        double x = gamepad.left_stick_x;
        double y = -gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;

        double theta = Math.atan2(y, x)* 180/Math.PI;
        double realtheta;
        realtheta = (360 - zeroDYaw) + theta;
        double power = Math.hypot(x, y);

        double sin = Math.sin((realtheta*(Math.PI/180))-(Math.PI /4));
        double cos = Math.cos((realtheta*(Math.PI/180))-(Math.PI /4));
        double maxsincos = Math.max(Math.abs(sin),Math.abs(cos));

        double leftFront = (power * cos / maxsincos + turn);
        double rightFront = (power * sin / maxsincos - turn);
        double leftBack = (power * sin / maxsincos + turn);
        double rightBack =(power * cos / maxsincos - turn);
        if ((power + Math.abs(turn))>1) {
            leftFront /= power + turn;
            rightFront /= power - turn;
            leftBack /= power + turn;
            rightBack /= power - turn;
        }
        leftFrontDrive.setPower(leftFront);
        rightFrontDrive.setPower(rightFront);
        leftBackDrive.setPower(leftBack);
        rightBackDrive.setPower(rightBack);
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

        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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



        while ((Math.abs(rightFrontDrive.getCurrentPosition()) < Math.abs(frontRightEncoder) || Math.abs(rightBackDrive.getCurrentPosition()) < backRightEncoder)&&opModeIsActive) {
            rx = -(angleAheadDegrees - getAngle()) * 0.01;
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            if ((rightFrontDrive.getCurrentPosition() == 0 && rightBackDrive.getCurrentPosition() == 0 && runTime.seconds() > 1)&&  opModeIsActive) {
                leftFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
                rightFrontDrive.setPower(0);
                rightBackDrive.setPower(0);
            }
            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;

            leftFrontDrive.setPower(frontLeftPower);
            leftBackDrive.setPower(backLeftPower);
            rightFrontDrive.setPower(frontRightPower);
            rightBackDrive.setPower(backRightPower);
        }

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
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
    public void TestOnMotor(){
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftFrontDrive.setTargetPosition(10000);
    leftFrontDrive.setPower(1);
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void reDrive(){
        leftFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
    }

    public void Estop(){
        leftFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
    }

    public void drive(double power, double angle, double rotation) {

        double y = -(power * Math.sin(angle));
        double x = -(power * Math.cos(angle));
        double rx = -rotation;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        leftFrontDrive.setPower(-frontLeftPower);
        leftBackDrive.setPower(-backLeftPower);
        rightFrontDrive.setPower(-frontRightPower);
        rightBackDrive.setPower(-backRightPower);
    }

    public void driveByOdometry(double x, double y, double angle, Telemetry t){
odometry.pos.setH(0);
odometry.pos.setX(0);
odometry.pos.setY(0);

        telemetry = t;
        double dx;
        double dy;
        double Dangle;
        double headAngle;
        double distance;

        do {
            odometry.Odometry();
            dx = x - odometry.pos.getX();
            dy = y - odometry.pos.getY();
            Dangle = Math.atan(dx/dy);
            headAngle = 0;
//           headAngle = angle - Math.toDegrees(odometry.pos.h);
            distance = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
            drive(distance<10? 0.1:0.4 ,Dangle, headAngle);
            telemetry.addData("dis", distance);
            telemetry.addData("LRA", "%6d %6d %6d", odometry.currentLeftPosition, odometry.currentRightPosition, odometry.currentAuxPosition);
            telemetry.addData("xyh", "%6.1f cm %6.1f cm %6.1f deg", odometry.pos.getX(), odometry.pos.getY(),Math.toDegrees(odometry.pos.getH()));
            telemetry.update();
        }
        while (distance>5);

        drive(0,0,0);
    }
}

