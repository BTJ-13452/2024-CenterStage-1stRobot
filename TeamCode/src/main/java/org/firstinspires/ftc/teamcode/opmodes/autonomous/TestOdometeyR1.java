package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import android.graphics.Region;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;

@Autonomous
public class TestOdometeyR1 extends LinearOpMode {
    DriveSystem drive;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        drive = new DriveSystem(hardwareMap,gamepad1,0);

        drive.driveByOdometry(10,0,90,telemetry);
        sleep(1000);
        while (opModeIsActive()){}
    }
}
