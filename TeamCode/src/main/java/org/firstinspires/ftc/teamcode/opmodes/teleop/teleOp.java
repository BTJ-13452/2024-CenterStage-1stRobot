package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;
import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;

@TeleOp
public class teleOp extends OpMode {

    DriveSystem driveSystem;

    DcMotor leftEncoder;
    DcMotor rightEncoder;
    DcMotor frontEncoder;

    @Override
    public void init() {

        driveSystem = new DriveSystem(hardwareMap, gamepad1);


        leftEncoder = hardwareMap.get(DcMotorEx.class, "DL");
        rightEncoder = hardwareMap.get(DcMotorEx.class, "DR");
        frontEncoder = hardwareMap.get(DcMotorEx.class, "UR");
    }

    @Override
    public void loop() {
        driveSystem.Drive();

        telemetry.addData(" left",leftEncoder.getCurrentPosition());
        telemetry.addData(" right",rightEncoder.getCurrentPosition());
        telemetry.addData(" front",frontEncoder.getCurrentPosition());
    }
}
