package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class servoPlane extends OpMode {

    Servo plane;
    @Override
    public void init() {
        plane = hardwareMap.get(Servo.class,"servoaPlane");

    }

    @Override
    public void loop() {
if (gamepad1.a){
   plane.setPosition(1);
}
    }
}
