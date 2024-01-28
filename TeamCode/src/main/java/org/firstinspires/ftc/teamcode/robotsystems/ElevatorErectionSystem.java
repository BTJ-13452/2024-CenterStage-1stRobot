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


    final int HEIGHT_OF_HANG = 1000; //TODO: לעשות מדידות

    public DcMotor Lm;
    public DcMotor Rm;
    TouchSensor magneticSwitch;

    /**
     * Constructs a new ElevatorErectionSystem.
     *
     * @param map The HardwareMap for accessing hardware devices.
     */
    public ElevatorErectionSystem(HardwareMap map) {
        Lm = map.dcMotor.get("lm");
        Rm = map.dcMotor.get("rm");
        Lm.setDirection(DcMotorSimple.Direction.REVERSE);

        magneticSwitch = map.get(TouchSensor.class, "erectionSwitch");

        Lm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Rm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Lm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Rm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Moves the elevator up with the specified power.
     *
     * @param power The power to move the elevator up.
     */
    public void elevatorUp(double power) {
        Lm.setPower(power);
        Rm.setPower(power);

        Lm.setTargetPosition(Lm.getTargetPosition());
        Rm.setTargetPosition(Rm.getTargetPosition());
    }

    /**
     * Moves the elevator down with the specified power.
     *
     * @param power The power to move the elevator down.
     */
    public void elevatorDown(double power) {
        Lm.setPower(-power);
        Rm.setPower(-power);

        Lm.setTargetPosition(Lm.getTargetPosition());
        Rm.setTargetPosition(Rm.getTargetPosition());
    }

    /**
     * Stops the elevator and sets the zero power behavior to BRAKE.
     */
    public void elevatorToZero() {
        Lm.setPower(0);
        Rm.setPower(0);
        Lm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Rm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Lowers the elevator to a certain position with the specified power.
     *
     * @param power The power to lower the elevator.
     */
    public void LowerHeight(double power) {
        while (!magneticSwitch.isPressed()) {
            Lm.setPower(power);
            Rm.setPower(power);
        }
        Lm.setPower(0);
        Rm.setPower(0);
    }

    public void setPositionToHanging(){
//        Lm.setTargetPosition();
//        Rm.setTargetPosition();
    }

    /**
     * Stops the endgame by continuously checking the magnetic switch.
     */
    public void StopEndGame() {
        while (true) {
            if (!magneticSwitch.isPressed()) {
                Lm.setPower(-1);
                Rm.setPower(-1);
            } else {
                Lm.setPower(0);
                Rm.setPower(0);
            }
        }
    }

    public void erectionTicks(int tick) {
        Rm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       Lm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

       Rm.setTargetPosition(tick);
        Lm.setTargetPosition(tick);

        Rm.setPower(0.5);
        Lm.setPower(0.5);

        Rm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Lm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }
    public int getPosition(){
        return Rm.getTargetPosition();
    }


    public void EStop() {
        Lm.setPower(0);
        Rm.setPower(0);
    }
}
