package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;

@Autonomous
public class automomuos_R1 extends LinearOpMode {
public DriveSystem drive;
    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        drive.opModeIsActive = true;
        drive.driveByEncoder(0.5,0,70,0);

    }
    public void Init () {
        drive = new DriveSystem(hardwareMap);

    }

}
