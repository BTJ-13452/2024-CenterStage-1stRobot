package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ListResourceBundle;

@TeleOp
public class manualDrive extends OpMode {
    ElevatorRotator elevatorRotator ;

    boolean RTisPressed =false;
    boolean LTisPressed = false;

    double power;
    double driverAngle;
    double rotation;
    double targetAngle;

    DriveSystem driveSystem;
    @Override
    public void init() {
        elevatorRotator = new ElevatorRotator(hardwareMap);

        driveSystem = new DriveSystem(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.right_trigger!=0){
            RTisPressed = true;
            elevatorRotator.RotateByPower(gamepad1.right_trigger);
        } else if (RTisPressed) elevatorRotator.RotateByPower(0);


        if (gamepad1.left_trigger!=0){
            LTisPressed = true;
            elevatorRotator.RotateByPower(-gamepad1.left_trigger);
        }else if (LTisPressed) elevatorRotator.RotateByPower(0);


        power = Math.sqrt(Math.pow(gamepad1.left_stick_y, 2) + Math.pow(gamepad1.left_stick_x, 2));

        driverAngle = Math.atan(-gamepad1.left_stick_y / gamepad1.left_stick_x);

        rotation = gamepad1.right_stick_x;

        if (Double.isNaN(driverAngle))
            driverAngle = 0;

        if (gamepad1.left_stick_x < 0)
            driverAngle += Math.PI;

        targetAngle = driverAngle; //- (-Math.toRadians(getAngle()));

        driveSystem.drive(power, targetAngle, rotation);
    }
}
