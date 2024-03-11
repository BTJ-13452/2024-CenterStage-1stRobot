package org.firstinspires.ftc.teamcode.robotsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.vision.VisionPortal;

/**
 * The ElevatorErectionSystem class represents the robot's elevator erection system.
 */
public class ElevatorErectionSystem {

    final String RIGHT_MOTOR_CONFIGURE = "rm";
    final String LEFT_MOTOR_CONFIGURE = "lm";

    final public static int TICKS_FOR_BACKDROPS = 1;
    final public static int TICKS_FOR_RIGGING = 2;

    final double POWER = 1;


    DcMotor motorRight;
    DcMotor motorLeft;

    /**
     * Constructor for the System_ElevatorErection class.
     *
     * @param map The HardwareMap object used to initialize hardware components.
     */
    public ElevatorErectionSystem(HardwareMap map) {
        // Motor initialization
        motorRight = map.dcMotor.get(RIGHT_MOTOR_CONFIGURE);
        motorLeft = map.dcMotor.get(LEFT_MOTOR_CONFIGURE);

        // Motor zero power behavior
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set motors to zero position
//        setToZero();

        // Motor run modes and encoder setup
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        motorRight.setTargetPosition(0);
        motorLeft.setTargetPosition(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

//    public ElevatorErectionSystem(HardwareMap map) {
//        Lm = map.dcMotor.get("lm");
//        Rm = map.dcMotor.get("rm");
//        Lm.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        magneticSwitch = map.get(TouchSensor.class, "erectionSwitch");
//
//        Lm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Rm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        Lm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        Rm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//    }

    /**
     * Raises or lowers the elevator based on the specified multiplier.
     *
     * @param multiplier The multiplier to adjust the power for raising or lowering the elevator.
     */
    public void raisedAndlowerErection(double multiplier) {
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorLeft.setPower(POWER * multiplier);
        motorRight.setPower(POWER * multiplier);
    }

    /**
     * Stops the elevator and resets motor run modes and positions.
     */
    public void stopErection() {
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorRight.setTargetPosition(motorRight.getCurrentPosition());
        motorLeft.setTargetPosition(motorLeft.getCurrentPosition());

        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorRight.setPower(1);
        motorLeft.setPower(1);
    }

    /**
     * Moves the elevator to a specified number of ticks using encoder positions.
     *
     * @param tick The target position in ticks.
     */
    public void erectionTicks(int tick) {
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorRight.setTargetPosition(tick);
        motorLeft.setTargetPosition(tick);

        motorRight.setPower(1);
        motorLeft.setPower(1);

        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public int getErection(){
       return motorLeft.getCurrentPosition();
    }

}
