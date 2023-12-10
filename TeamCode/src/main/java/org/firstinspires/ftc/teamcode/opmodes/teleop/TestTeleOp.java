package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.hangingRobot;
import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSytem;
import org.firstinspires.ftc.teamcode.robotsystems.LaunchSystem;

@TeleOp
public class TestTeleOp extends OpMode {
        DriveSystem driveSystem;
        ElevatorSystem elevatorSystem;
    ElevatorErectionSystem elvatorErectionSystem;
        boolean RLBPressed = false;
        boolean XPressed = false;
        boolean APressed = false;
        boolean BPressed = false;
        boolean upPressed = false;
        boolean YPressed = false;
        boolean RBPressed = false;
        boolean LBPressed = false;

        hangingRobot hangingRobot;
    LaunchSystem launchSystem;


        EndPointSytem yks;

    @Override
    public void init() {
        driveSystem =  new DriveSystem(hardwareMap, gamepad1);
        elevatorSystem = new ElevatorSystem(hardwareMap);
       elvatorErectionSystem = new ElevatorErectionSystem(hardwareMap);
        yks = new EndPointSytem(hardwareMap);
        hangingRobot = new hangingRobot(hardwareMap);
        launchSystem = new LaunchSystem(hardwareMap);

        telemetry.addData("using", DcMotor.RunMode.RUN_USING_ENCODER.equals(DcMotor.RunMode.RUN_WITHOUT_ENCODER));
        telemetry.addData("endInit","");
        telemetry.update();

//        hardwareMap.servo.get("erectionYks").setPosition(0);
    }



    @Override
    public void loop()
    {
        driveSystem.Drive();

//        telemetry.addData("lm",elvatorErectionSystem.Lm.getCurrentPosition());
//        telemetry.addData("rm",elvatorErectionSystem.Rm.getCurrentPosition());
//        telemetry.addData("yksRight",yks.yksRight.getPosition());
        telemetry.update();


        if(gamepad1.right_bumper) {
            elvatorErectionSystem.elevatorUp();
        }
        if(gamepad1.left_bumper) {
            elvatorErectionSystem.elevatorDown();
        }
        if(!gamepad1.left_bumper && !gamepad1.right_bumper){
            elvatorErectionSystem.elevatorToZero();
        }





        if(gamepad1.y && !YPressed){
            YPressed = true;
            launchSystem.LaunchP();
        } else if(!gamepad1.y){
            YPressed = false;
        }

        if (gamepad1.a && !APressed){
            APressed=true;
            yks.YksLactive();
//            if(yks.yksLeft.getPosition() == 0){
//                yks.openYksLeft();
//            }else if(yks.yksLeft.getPosition() != 0){
//                yks.closeYksLeft();
//            }
        }else if(!gamepad1.a){
            APressed=false;
        }

        if (gamepad1.b&&!BPressed){
            BPressed=true;
            yks.YksRactive();
//            if(yks.yksRight.getPosition()==1){
//                yks.openYksRight();
//            }else if(yks.yksRight.getPosition()!= 1){
//                yks.closeYksRight();
//            }
        }




        if(!gamepad1.b){
            BPressed=false;
        }

//elevatorSystem
        if (gamepad1.right_trigger > 0)
            elevatorSystem.Extension(gamepad1.right_trigger);
        if (gamepad1.left_trigger > 0)
            elevatorSystem.LowerElevator(gamepad1.left_trigger);
        if (gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0){
            elevatorSystem.StopElevator();


        }
        if (gamepad1.x && !XPressed){
            XPressed = true;
            hangingRobot.activateHanging();
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
