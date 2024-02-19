package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;

/**
 * The teleOp class represents a TeleOp mode for controlling a robot drive system.
 */
@TeleOp
@Disabled
public class teleOp extends OpMode {

    private DriveSystem driveSystem;

    private DcMotor leftEncoder;
    private DcMotor rightEncoder;
    private DcMotor frontEncoder;

    @Override
    public void init() {
        driveSystem = new DriveSystem(hardwareMap, gamepad1,0);

        leftEncoder = hardwareMap.get(DcMotorEx.class, "DL");
        rightEncoder = hardwareMap.get(DcMotorEx.class, "DR");
        frontEncoder = hardwareMap.get(DcMotorEx.class, "UR");
    }


    @Override
    public void loop() {
        driveSystem.Drive();

        telemetry.addData(" left", leftEncoder.getCurrentPosition());
        telemetry.addData(" right", rightEncoder.getCurrentPosition());
        telemetry.addData(" front", frontEncoder.getCurrentPosition());
    }
}
