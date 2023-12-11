package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
@Autonomous
public class Autonomous_R2 extends OpMode {

    final static double FIELD_LENGTH = 143;
    final static double TILE = FIELD_LENGTH/6;
    final static double ROBOT_LENGTH = 20;


    SampleMecanumDrive drive;
    TrajectorySequence t;
    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);

        t = drive.trajectorySequenceBuilder(new Pose2d(TILE*-1.5, TILE*-3+ROBOT_LENGTH*0.5, Math.toRadians(90)))
                .forward(TILE+5)
                .lineToLinearHeading(new Pose2d(-2*TILE, -1.5*TILE, Math.toRadians(195)))
                .waitSeconds(1)
                .back(TILE*3.5)
                .waitSeconds(1)
                .forward(TILE*3.5)
                .waitSeconds(2)
                .back(TILE*3.5)
                .waitSeconds(2)
                .forward(TILE*3.5)
                .waitSeconds(2)
                .back(TILE*4)
                .waitSeconds(2)
                .strafeRight(TILE)
                .build();
    }


    @Override
    public void start(){
       drive.followTrajectorySequence(t);
    }
    @Override
    public void loop() {

    }
}
