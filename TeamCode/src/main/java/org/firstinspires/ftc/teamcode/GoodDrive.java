package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class GoodDrive {

    BNO055IMU imu;
    public ElapsedTime runTime = new ElapsedTime();
    Orientation lastAngles = new Orientation();
    double globalAngle;


    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    Gamepad gamepad;

    Orientation angles= new Orientation();


    public GoodDrive(HardwareMap map, Gamepad gamepad) {
        BNO055IMU.Parameters parameters=new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = map.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        motorFrontLeft = map.get(DcMotor.class,"UL");
        motorBackLeft = map.get(DcMotor.class,"DL");
        motorFrontRight = map.get(DcMotor.class,"UR");
        motorBackRight = map.get(DcMotor.class,"DR");

        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);


        this.gamepad = gamepad;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
    }

    public void drive() {

        motorFrontLeft.setPower(-gamepad.right_stick_x + gamepad.left_stick_y * Math.cos( getAngle() - angles.firstAngle + Math.PI/4)
                + gamepad.left_stick_x * Math.sin(getAngle() - angles.firstAngle + Math.PI/4));

        motorBackLeft.setPower(-gamepad.right_stick_x + gamepad.left_stick_y * Math.sin( getAngle() - angles.firstAngle + Math.PI/4)
                - gamepad.left_stick_x * Math.cos(getAngle() - angles.firstAngle + Math.PI/4));

        motorFrontRight.setPower(gamepad.right_stick_x + gamepad.left_stick_y * Math.sin( getAngle() - angles.firstAngle + Math.PI/4)
                - gamepad.left_stick_x * Math.cos( getAngle() - angles.firstAngle + Math.PI/4));

        motorBackLeft.setPower(gamepad.right_stick_x + gamepad.left_stick_y * Math.cos( getAngle() - angles.firstAngle + Math.PI/4)
                + gamepad.left_stick_x * Math.sin( getAngle() - angles.firstAngle + Math.PI/4));

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


        while ((Math.abs(motorFrontRight.getCurrentPosition()) < frontRightEncoder || Math.abs(motorBackRight.getCurrentPosition()) < backRightEncoder)) {
            rx = -(angleAheadDegrees - getAngle()) * 0.01;
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            if ((motorFrontRight.getCurrentPosition() == 0 && motorBackRight.getCurrentPosition() == 0 && runTime.seconds() > 1)

            ) {
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
}
