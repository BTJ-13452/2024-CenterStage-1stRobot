package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * The EndPointSystem class represents the robot's end point system.
 */
public class EndPointSystem {

    final static double MIN_ERECTION_POSITION = 0.0;
    final static double INIT_ERECTION_POSITION = 0.6;
    final static double MAX_ERECTION_POSITION = 0.75;
    final static double RIGHT_OPEN_POSITION = 0.6;
    final static double LEFT_OPEN_POSITION = 0.65;

    private Servo yksRight;
    private Servo yksLeft;
    private Servo erectionYks;
    ElevatorErectionSystem elevatorErectionSystem;

    /**
     * Constructs a new EndPointSystem.
     *
     * @param Map The HardwareMap for accessing hardware devices.
     */
    public EndPointSystem(HardwareMap Map) {
        yksRight = Map.servo.get("yksRight");
        yksLeft = Map.servo.get("yksLeft");
        erectionYks = Map.servo.get("erectionYks");

        erectionYks.setPosition(INIT_ERECTION_POSITION);
        yksRight.setPosition(1);
        yksLeft.setPosition(0);
    }

    /**
     * Opens the erection Yks.
     */
    public void openErectionYks() {
        erectionYks.setPosition(MAX_ERECTION_POSITION);
    }

    public void openErectionYks(double position) {
        erectionYks.setPosition(position);
    }

    /**
     * Closes the erection Yks.
     */
    public void closeErectionYks() {
        erectionYks.setPosition(MIN_ERECTION_POSITION);
    }

    /**
     * Opens the right Yks.
     */
    public void openYksRight() {
        yksRight.setPosition(RIGHT_OPEN_POSITION);
    }

    /**
     * Closes the right Yks.
     */
    public void closeYksRight() {
        yksRight.setPosition(1);
    }

    /**
     * Opens the left Yks.
     */
    public void openYksLeft() {
        yksLeft.setPosition(LEFT_OPEN_POSITION);
    }

    /**
     * Closes the left Yks.
     */
    public void closeYksLeft() {
        yksLeft.setPosition(0);
    }

    /**
     * Activates or deactivates the left Yks based on its current position.
     */
    public void YksLactive() {
        if (yksLeft.getPosition() == 0) {
            openYksLeft();
        } else {
            closeYksLeft();
        }
    }

    /**
     * Activates or deactivates the right Yks based on its current position.
     */
    public void YksRactive() {
        if (yksRight.getPosition() == 1) {
            openYksRight();
        } else {
            closeYksRight();
        }
    }
    public void EStop() {}


    public void YksSetPosition(double position){
        erectionYks.setPosition(position);
    }

}

