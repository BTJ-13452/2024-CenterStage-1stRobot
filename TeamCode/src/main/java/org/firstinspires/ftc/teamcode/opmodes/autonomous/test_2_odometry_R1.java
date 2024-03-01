package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;
@Autonomous
@Disabled
public class test_2_odometry_R1 extends LinearOpMode {
    DriveSystem drive;
    private ElevatorErectionSystem elvatorErectionSystem;
    private EndPointSystem yks;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        drive = new DriveSystem(hardwareMap, gamepad1, 0);
        elvatorErectionSystem = new ElevatorErectionSystem(hardwareMap);
        yks = new EndPointSystem(hardwareMap);
        waitForStart();
//        drive.driveByOdometry(10.0,10.0,10.0,);
        yks.closeYksRight();
        yks.closeYksLeft();
        sleep(100);
        drive.driveByOdometryOnlyFor90Degrees(Math.toRadians(90), telemetry);

        while (opModeIsActive()) {
        }
    }
}