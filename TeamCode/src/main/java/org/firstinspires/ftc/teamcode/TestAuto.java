package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        Pose2d startPose = new Pose2d(0,0,0);
        Trajectory colshhu = drive.trajectoryBuilder(new Pose2d())
                .forward(20)
                .build();

        TrajectorySequence ts = drive.trajectorySequenceBuilder(startPose)
                .turn(Math.toRadians(90))
                .back(80)
                .build();

        Trajectory rightBack = drive.trajectoryBuilder(new Pose2d())
                .forward(80)
                .back(80)
                .build();

        TrajectorySequence theAngle = drive.trajectorySequenceBuilder(startPose)
                .forward(80)
                .turn(Math.toRadians(45))
                .turn(Math.toRadians(90))
                .back(80)
                .build();

        TrajectorySequence u123 = drive.trajectorySequenceBuilder(startPose)
                        .forward(80)
                .waitSeconds(2)
                .turn(Math.toRadians(45))
                .waitSeconds(2)
                .turn(Math.toRadians(90))
                .back(90)
                .build();




        waitForStart();

        drive.followTrajectory(colshhu);
        drive.followTrajectorySequence(ts);
        drive.followTrajectory(rightBack);
        drive.followTrajectory(rightBack);
        drive.followTrajectorySequence(theAngle);
        drive.followTrajectorySequence(theAngle);
        drive.followTrajectorySequence(u123);

    }
}
