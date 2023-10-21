package org.firstinspires.ftc.teamcode;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class DriveMethod extends OpMode {
   private DcMotor leftFrontDrive;
   private DcMotor leftBackDrive;
   private DcMotor rightFrontDrive;
   private DcMotor rightBackDrive;

BNO055IMU imu;
Orientation angles= new Orientation();
double initYaw;
double adjustedYaw;
    @Override
    public void init() {
BNO055IMU.Parameters parameters=new BNO055IMU.Parameters();
parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
parameters.mode = BNO055IMU.SensorMode.IMU;
parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

imu = hardwareMap.get(BNO055IMU.class, "imu");
imu.initialize(parameters);

angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

initYaw = angles.firstAngle;

        rightFrontDrive = hardwareMap.get(DcMotor.class, "FRwheel");
        rightBackDrive = hardwareMap.get(DcMotor.class, "BRwheel");
        leftBackDrive= hardwareMap.get(DcMotor.class, "BLwheel");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "FLwheel");

        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        angles=imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        adjustedYaw = angles.firstAngle-initYaw;
        double zeroDYaw = initYaw+angles.firstAngle;

        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

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


}
