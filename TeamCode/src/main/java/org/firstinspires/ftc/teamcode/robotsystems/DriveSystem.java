package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * The DriveSystem class represents the robot's drive system.
 */
public class DriveSystem {

    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;

    private BNO055IMU imu;
    private Orientation angles = new Orientation();
    private double initYaw;
    private double adjustedYaw;

    private Gamepad gamepad;

    /**
     * Constructs a new DriveSystem.
     *
     * @param map      The HardwareMap for accessing hardware devices.
     * @param gamepad  The Gamepad for controlling the robot.
     */
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

    /**
     * Drives the robot based on the gamepad input and IMU data.
     */
    public void Drive() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        adjustedYaw = angles.firstAngle - initYaw;
        double zeroDYaw = initYaw + angles.firstAngle;

        double x = gamepad.left_stick_x;
        double y = -gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;

        double theta = Math.atan2(y, x) * 180 / Math.PI;
        double realtheta;
        realtheta = (360 - zeroDYaw) + theta;
        double power = Math.hypot(x, y);

        double sin = Math.sin((realtheta * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((realtheta * (Math.PI / 180)) - (Math.PI / 4));
        double maxsincos = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFront = (power * cos / maxsincos + turn);
        double rightFront = (power * sin / maxsincos - turn);
        double leftBack = (power * sin / maxsincos + turn);
        double rightBack = (power * cos / maxsincos - turn);

        if ((power + Math.abs(turn)) > 1) {
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

    public void EStop(){
        double leftFront = 0;
        double rightFront = 0;
        double leftBack = 0;
        double rightBack = 0;
    }
}
