package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;

@TeleOp
public class teleOp extends OpMode {

    double power;
    double driverAngle;
    double rotation;
    double targetAngle;

    DriveSystem driveSystem;
    DcMotor elevator;

    @Override
    public void init() {
        elevator = hardwareMap.dcMotor.get("Elevator");

        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        elevator.setTargetPosition(-1000);

        elevator.setPower(1);

        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveSystem = new DriveSystem(hardwareMap);
    }

    @Override
    public void loop() {
        power = Math.sqrt(Math.pow(gamepad1.left_stick_y, 2) + Math.pow(gamepad1.left_stick_x, 2));

        driverAngle = Math.atan(-gamepad1.left_stick_y / gamepad1.left_stick_x);

        rotation = gamepad1.right_stick_x;

        if (Double.isNaN(driverAngle))
            driverAngle = 0;

        if (gamepad1.left_stick_x < 0)
            driverAngle += Math.PI;

        targetAngle = driverAngle; //- (-Math.toRadians(getAngle()));

//        driveSystem.drive(power, targetAngle, rotation);
    }
}
