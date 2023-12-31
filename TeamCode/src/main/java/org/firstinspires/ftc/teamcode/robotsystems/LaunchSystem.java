package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * The LaunchSystem class represents the robot's launch system.
 */
public class LaunchSystem {

    private CRServo launch;

    /**
     * Constructs a new LaunchSystem.
     *
     * @param map The HardwareMap for accessing hardware devices.
     */
    public LaunchSystem(HardwareMap map) {
        launch = map.crservo.get("launch");
        launch.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Activates or deactivates the launch mechanism based on its current power.
     */
    public void Activate() {
        if (launch.getPower() == 0) {
            launch.setPower(1);
        } else if (launch.getPower() == 1) {
            launch.setPower(0);
        }
    }
    public void EStop() {}
}
