package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class teleOp extends OpMode {

    double power;
    double driverAngle;
    double rotation;
    double targetAngle;

    DriveSystem driveSystem;
    @Override
    public void init() {
        driveSystem = new DriveSystem(hardwareMap);
    }

    @Override
    public void loop() {
        power = Math.sqrt(Math.pow(gamepad1.left_stick_y, 2) + Math.pow(gamepad1.left_stick_x, 2));

        driverAngle = Math.atan(-gamepad1.left_stick_y / gamepad1.left_stick_x);

        rotation = gamepad1.right_stick_x;

        if (Double.isNaN(driverAngle))
            driverAngle = 0;

        if (gamepad1.left_stick_x < 0)
            driverAngle += Math.PI;

        targetAngle = driverAngle; //- (-Math.toRadians(getAngle()));

        driveSystem.drive(power, targetAngle, rotation);
    }
}
