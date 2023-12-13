package org.firstinspires.ftc.teamcode.openCV;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
@Autonomous
public class test extends LinearOpMode {
    Processor processor =new Processor(this);
    VisionPortal myVisionPortal;

// Create a VisionPortal, with the specified camera and AprilTag processor, and assign it to a variable.

    @Override
    public void runOpMode() throws InterruptedException {
        myVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName .class, "Webcam 1"), processor);
        while (opModeInInit()){

        }
        waitForStart();





    }
}
