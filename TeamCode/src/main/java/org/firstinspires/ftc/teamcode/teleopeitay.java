package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class teleopeitay extends OpMode {
    DriveSystem driveSystem;


    @Override
    public void init() {
        driveSystem =  new DriveSystem(hardwareMap, gamepad1);

        telemetry.addData("endInit","");
        telemetry.update();
    }
    @Override
    public void loop() {
        driveSystem.Drive();

    }
}

