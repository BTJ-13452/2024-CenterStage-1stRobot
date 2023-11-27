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
        boolean BPressed = false;
        boolean upPressed = false;
        LaunchSystem launchSystem;
        boolean YPressed = false;

        hangingRobot hangingRobot;


        YKS yks;

    @Override
    public void init() {
        driveSystem =  new DriveSystem(hardwareMap, gamepad1);
        elevatorSystem = new ElevatorSystem(hardwareMap);
        elvatorErectionSystem = new ElvatorErectionSystem(hardwareMap);
        yks = new YKS(hardwareMap);
        hangingRobot = new hangingRobot(hardwareMap);
//        endPointSystem = new EndPointSystem((hardwareMap));
        launchSystem = new LaunchSystem(hardwareMap);
        telemetry.addData("endInit","");
        telemetry.update();

//        hardwareMap.servo.get("erectionYks").setPosition(0);
    }
    @Override
    public void loop()
    {
        driveSystem.Drive();

        telemetry.addData("lm",elvatorErectionSystem.Lm.getCurrentPosition());
        telemetry.addData("rm",elvatorErectionSystem.Rm.getCurrentPosition());
        telemetry.addData("yksRight",yks.yksRight.getPosition());
        telemetry.update();
//        elevatorSystem.Extension(gamepad1.right_trigger - gamepad1.left_trigger);

//        if((gamepad1.right_bumper || gamepad1.left_bumper) && !RLBPressed){
//            RLBPressed = true;
//            elvatorErectionSystem.elevatorHeight(gamepad1.right_bumper,gamepad1.left_bumper);
//        } else if(RLBPressed){
//            RLBPressed = false;
//        }
        //ציר המעלית
        if(gamepad1.right_bumper) {
            elvatorErectionSystem.elevatorUp();
        }
        if(gamepad1.left_bumper) {
            elvatorErectionSystem.elevatorDown();
        }
        if(!gamepad1.left_bumper && !gamepad1.right_bumper){
            elvatorErectionSystem.elevatorToZero();
        }

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

        if (gamepad1.a&&!APressed){
            APressed=true;
            if(yks.yksLeft.getPosition()==0){
                yks.openYksLeft();
            }else if(yks.yksLeft.getPosition()==0.5){
                yks.closeYksLeft();

            }
        }
        if(!gamepad1.a){
            APressed=false;
        }
        if (gamepad1.b&&!BPressed){
            BPressed=true;
            if(yks.yksRight.getPosition()==1){
                yks.openYksRight();
            }else if(yks.yksRight.getPosition()!= 1){
                yks.closeYksRight();
            }
        }
        if(!gamepad1.b){
            BPressed=false;
        }


        if (gamepad1.right_trigger > 0)
            elevatorSystem.Extension(gamepad1.right_trigger);
        if (gamepad1.left_trigger > 0)
            elevatorSystem.LowerElevator(gamepad1.left_trigger);
        if (gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0){
            elevatorSystem.StopElevator();
        }
        if (gamepad1.x && !XPressed){
            XPressed = true;
            if(hangingRobot.hang.getPosition()==1){
                hangingRobot.hang();
            }else if(hangingRobot.hang.getPosition()==0){
                hangingRobot.stopHang();
            }
        }if (!gamepad1.x)
            XPressed = false;

        if(gamepad1.dpad_up&&!upPressed){
            upPressed=true;
            yks.openErectionYks();
        }
        if(!gamepad1.dpad_up){
            upPressed=false;
        }
        if(gamepad1.dpad_down){
            yks.closeErectionYks();
        }
    }

    @Override
    public void stop(){
        elvatorErectionSystem.StopEndGame();
    }
}
