package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * The ElevatorSystem class represents the robot's elevator system.
 */
public class ElevatorSystem {

    private DcMotor elevator;
    private TouchSensor magneticSwitch;
    private final double Speed = 0.8;
    private final int MAX_HEIGHT = 1000;

    /**
     * Constructs a new ElevatorSystem.
     *
     * @param map The HardwareMap for accessing hardware devices.
     */
    public ElevatorSystem(HardwareMap map) {
        elevator = map.dcMotor.get("Elevator");
        magneticSwitch = map.get(TouchSensor.class, "elevatorSwitch");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        CloseElevator();
    }

    /**
     * Extends the elevator.
     */
    public void Extension() {
        if (elevator.getCurrentPosition() > -1600) {
            elevator.setPower(-0.8);
        } else {
            elevator.setPower(0);
        }
    }

    /**
     * Lowers the elevator if the magnetic switch is not pressed.
     */
    public void LowerElevator() {
        if (!magneticSwitch.isPressed()) {
            elevator.setPower(0.8);
        }
    }

    /**
     * Stops the elevator.
     */
    public void StopElevator() {
        elevator.setPower(0);
    }

    /**
     * Closes the elevator until the magnetic switch is pressed.
     */
    public void CloseElevator() {
        while (!magneticSwitch.isPressed()) {
            elevator.setPower(Speed);
        }
        elevator.setPower(0);
        //elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
