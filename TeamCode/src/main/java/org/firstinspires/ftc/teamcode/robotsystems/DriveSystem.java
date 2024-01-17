package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

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
double initYaw;
double adjustedYaw;

Gamepad gamepad;

public boolean opModeIsActive = false;
public DriveSystem(HardwareMap map){
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    parameters.mode = BNO055IMU.SensorMode.IMU;
    parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
    parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
    parameters.loggingEnabled = false;
    imu = map.get(BNO055IMU.class, "imu");

    imu.initialize(parameters);

    leftFrontDrive = map.dcMotor.get("UL");
    leftBackDrive = map.dcMotor.get("DL");
    leftFrontDrive = map.dcMotor.get("UR");
    leftBackDrive = map.dcMotor.get("DR");
    leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    leftBackDrive.setTargetPosition(0);
    leftBackDrive.setTargetPosition(0);
    leftFrontDrive.setTargetPosition(0);
    leftFrontDrive.setTargetPosition(0);

    runTime.reset();
}
    public DriveSystem(HardwareMap map, Gamepad gamepad) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = map.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        initYaw = angles.firstAngle;

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

        adjustedYaw = angles.firstAngle-initYaw;
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

        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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


        while ((Math.abs(leftFrontDrive.getCurrentPosition()) < frontRightEncoder || Math.abs(leftBackDrive.getCurrentPosition()) < backRightEncoder)&&opModeIsActive) {
            rx = -(angleAheadDegrees - getAngle()) * 0.01;
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            if ((leftFrontDrive.getCurrentPosition() == 0 && leftBackDrive.getCurrentPosition() == 0 && runTime.seconds() > 1)&&  opModeIsActive) {
                leftFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
                leftFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
            }
            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;

            leftFrontDrive.setPower(frontLeftPower);
            leftBackDrive.setPower(backLeftPower);
            leftFrontDrive.setPower(frontRightPower);
            leftBackDrive.setPower(backRightPower);
        }

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
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
}
