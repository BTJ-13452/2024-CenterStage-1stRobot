package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestTeleOp extends OpMode {
        DriveSystem driveSystem;
        ElevatorSystem elevatorSystem;
        ElvatorErectionSystem elvatorErectionSystem;
        boolean RLBPressed = false;
        EndPointSystem endPointSystem;
        boolean XPressed = false;
        boolean APressed = false;
        LaunchSystem launchSystem;
        boolean YPressed = false;

    @Override
    public void init() {
        driveSystem =  new DriveSystem(hardwareMap, gamepad1);
        elevatorSystem = new ElevatorSystem(hardwareMap);
        elvatorErectionSystem = new ElvatorErectionSystem(hardwareMap);
//        endPointSystem = new EndPointSystem((hardwareMap));
        launchSystem = new LaunchSystem(hardwareMap);
    }

    @Override
    public void loop() {
        driveSystem.Drive();
        elevatorSystem.Extension(gamepad1.right_trigger - gamepad1.left_trigger);

        if((gamepad1.right_bumper || gamepad1.left_bumper) && !RLBPressed){
            RLBPressed = true;
            elvatorErectionSystem.elevatorHeight(gamepad1.right_bumper,gamepad1.left_bumper);
        } else if(RLBPressed){
            RLBPressed = false;
        }
//        if(gamepad1.x && !XPressed){
//            XPressed = true;
//            endPointSystem.sucking();
//        } else if (XPressed) {
//            XPressed = false;
//        }
//        if(gamepad1.a && !APressed){
//            APressed = true;
//            endPointSystem.spitting();
//        } else if (APressed) {
//            APressed = false;
//        }
        if(gamepad1.y && !YPressed){
            YPressed = true;
            launchSystem.LaunchP();
        }else if(YPressed){
            YPressed = false;
        }

    }
}
