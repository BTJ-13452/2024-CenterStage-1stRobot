package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import android.graphics.Region;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;

@Autonomous
public class TestOdometeyR1 extends LinearOpMode {
    DriveSystem drive;
    private ElevatorErectionSystem elvatorErectionSystem;
    private EndPointSystem yks;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new DriveSystem(hardwareMap,gamepad1,0);
        elvatorErectionSystem = new ElevatorErectionSystem(hardwareMap);
        yks = new EndPointSystem(hardwareMap);
        waitForStart();
        yks.closeYksRight();
        yks.closeYksLeft();
        sleep(100);
        drive.driveByOdometryOnlyFor90Degrees(Math.PI/2,telemetry);
        drive.driveByOdometry(-93,93,0,telemetry);
        sleep(1000);
        drive.driveByOdometryOnlyFor90Degrees(Math.PI/2,telemetry);
        sleep(1000);
        drive.driveByOdometry(40,0,0,telemetry);
        yks.openYksRight();

        sleep(1000);
        while (opModeIsActive()){}
    }
}
