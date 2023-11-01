package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    final static double FIELD_LENGTH = 143;
    final static double TILE = FIELD_LENGTH/6;
    final static double ROBOT_LENGTH = 20;


    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        // Declare our first bot
        RoadRunnerBotEntity RobotR1mainP1 = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(TILE*-1.5, TILE*-3+ROBOT_LENGTH*0.5, Math.toRadians(90)))
                                .forward(TILE+2)
                                .lineToLinearHeading(new Pose2d(-2*TILE, -1.5*TILE, Math.toRadians(180)))
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
                                .build());

        RoadRunnerBotEntity RobotR1mainP2 = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(TILE*-1.5, TILE*-3+ROBOT_LENGTH*0.5, Math.toRadians(90)))
                                .forward(TILE+2)
                                .lineToLinearHeading(new Pose2d(-2*TILE, -1.5*TILE, Math.toRadians(180)))
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
                                .strafeLeft(TILE)
                                .build());

        RoadRunnerBotEntity RobotR1second = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(TILE*-1.5, TILE*-3+ROBOT_LENGTH*0.5, Math.toRadians(90)))
                                .forward(TILE*2+2)
                                .lineToLinearHeading(new Pose2d(-2*TILE, -0.5*TILE, Math.toRadians(180)))
                                .waitSeconds(1)
                                .back(TILE*3.5)
                                .strafeLeft(TILE)
                                .waitSeconds(2)
                                .strafeRight(TILE)
                                .forward(TILE*3.5)
                                .waitSeconds(1)
                                .back(TILE*3.5)
                                .strafeLeft(2)
                                .turn(Math.toRadians(-45))
                                .waitSeconds(2)
                                .turn(Math.toRadians(45))
                                .back(TILE)
                                .build());

        RoadRunnerBotEntity RobotB1mainP1 = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(TILE*-1.5, TILE*3-ROBOT_LENGTH*0.5, Math.toRadians(-90)))
                                .forward(TILE+2)
                                .lineToLinearHeading(new Pose2d(-2*TILE, 1.5*TILE, Math.toRadians(180)))
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
                                .strafeLeft(TILE)
                                .build());


        RoadRunnerBotEntity RobotB1mainP2 = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(TILE*-1.5, TILE*3-ROBOT_LENGTH*0.5, Math.toRadians(-90)))
                                .forward(TILE+2)
                                .lineToLinearHeading(new Pose2d(-2*TILE, 1.5*TILE, Math.toRadians(180)))
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
                                .build());


        RoadRunnerBotEntity RobotB1second = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(TILE*-1.5, TILE*3-ROBOT_LENGTH*0.5, Math.toRadians(-90)))
                                .forward(TILE*2+2)
                                .lineToLinearHeading(new Pose2d(-2*TILE, 0.5*TILE, Math.toRadians(180)))
                                .waitSeconds(1)
                                .back(TILE*3.5)
                                .strafeRight(TILE)
                                .waitSeconds(2)
                                .strafeLeft(TILE)
                                .forward(TILE*3.5)
                                .waitSeconds(1)
                                .back(TILE*3.5)
                                .strafeRight(2)
                                .turn(Math.toRadians(45))
                                .waitSeconds(2)
                                .turn(Math.toRadians(-45))
                                .back(TILE)
                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)

                // Add both of our declared bot entities
                .addEntity(RobotR1mainP1)
                .addEntity(RobotR1mainP2)
                .addEntity(RobotR1second)
                .addEntity(RobotB1second)
                .addEntity(RobotB1mainP1)
                .addEntity(RobotB1mainP2)
                .start();
    }
}