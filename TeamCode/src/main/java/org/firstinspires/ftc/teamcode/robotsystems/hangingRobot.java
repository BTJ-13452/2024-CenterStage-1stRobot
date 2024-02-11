package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * The hangingRobot class represents the robot's hanging mechanism.
 */
public class hangingRobot {

    private Servo hang;

    /**
     * Constructs a new hangingRobot.
     *
     * @param Map The HardwareMap for accessing hardware devices.
     */
    public hangingRobot(HardwareMap Map) {
        hang = Map.servo.get("hang");
        hang.setPosition(1);
    }

    /**
     * Activates or deactivates the hanging mechanism based on its current position.
     */
    public void activateHanging() {
        if (hang.getPosition() == 1) {
            hang.setPosition(0);
        } else {
            hang.setPosition(1);
        }
    }

}
