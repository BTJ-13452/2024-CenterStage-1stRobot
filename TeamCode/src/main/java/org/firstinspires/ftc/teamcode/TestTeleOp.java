package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

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
//        elvatorErectionSystem = new ElvatorErectionSystem(hardwareMap);
//        endPointSystem = new EndPointSystem((hardwareMap));
        launchSystem = new LaunchSystem(hardwareMap);
        telemetry.addData("endInit","");
        telemetry.update();
    }

    @Override
    public void loop()
    {
        driveSystem.Drive();

//        telemetry.addData("lm",elvatorErectionSystem.Lm.getCurrentPosition());
        telemetry.addData("motor",elevatorSystem.elevator.getCurrentPosition());
        telemetry.update();
//        elevatorSystem.Extension(gamepad1.right_trigger - gamepad1.left_trigger);

//        if((gamepad1.right_bumper || gamepad1.left_bumper) && !RLBPressed){
//            RLBPressed = true;
//            elvatorErectionSystem.elevatorHeight(gamepad1.right_bumper,gamepad1.left_bumper);
//        } else if(RLBPressed){
//            RLBPressed = false;
//        }
        //ציר המעלית
//        if(gamepad1.right_bumper) {
//            elvatorErectionSystem.elevatorUp();
//        }
//        if(gamepad1.left_bumper) {
//            elvatorErectionSystem.elevatorDown();
//        }
//        if(!gamepad1.left_bumper && !gamepad1.right_bumper){
//            elvatorErectionSystem.elevatorToZero();
//        }

        //שאיבה
//        if(gamepad1.x && !XPressed){
//            XPressed = true;
//            endPointSystem.sucking();
//        } else if (!gamepad1.x) {
//            XPressed = false;
//        }
        //פריקה
//        if(gamepad1.a && !APressed){
//            APressed = true;
//            endPointSystem.spitting();
//        } else if (!gamepad1.a) {
//            APressed = false;
//        }
        //מטוס
        if(gamepad1.y && !YPressed){
            YPressed = true;
            launchSystem.LaunchP();
        }
        else if(!gamepad1.y){
            YPressed = false;
        }
        // מעלית
        if(gamepad1.right_trigger>0)
        {
            elevatorSystem.elevatorUp();
        }
        else if(gamepad1.left_trigger>0)
        {
            elevatorSystem.elevatorDown();
        }



    }
}
