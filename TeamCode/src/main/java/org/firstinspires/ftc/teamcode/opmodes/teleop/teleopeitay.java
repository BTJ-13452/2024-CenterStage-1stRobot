package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.NoFildoDriveSystem;

@TeleOp
@Disabled
public class teleopeitay extends OpMode {
    DriveSystem driveSystem;


    @Override
    public void init() {
        driveSystem =  new DriveSystem(hardwareMap, gamepad1,0);

        telemetry.addData("endInit","");
        telemetry.update();
    }
    @Override
    public void loop() {
        driveSystem.Drive();
    }
}

