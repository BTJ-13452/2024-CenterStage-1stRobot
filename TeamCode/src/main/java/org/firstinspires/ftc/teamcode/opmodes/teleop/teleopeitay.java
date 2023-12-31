package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.NoFildoDriveSystem;

@TeleOp
public class teleopeitay extends OpMode {
    DriveSystem driveSystem;
    NoFildoDriveSystem noFildoDriveSystem;

    @Override
    public void init() {
        noFildoDriveSystem =  new NoFildoDriveSystem(hardwareMap, gamepad1);

        telemetry.addData("endInit","");
        telemetry.update();
    }
    @Override
    public void loop() {
        noFildoDriveSystem.drive();
    }
}

