package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.System_Odometry;
import org.firstinspires.ftc.teamcode.robotsystems.hangingRobot;
import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;
import org.firstinspires.ftc.teamcode.robotsystems.LaunchSystem;
import static android.os.SystemClock.sleep;

/**
 * The TestTeleOp class represents the TeleOp mode for testing various robot systems.
 */
@TeleOp
public class
 TestTeleOp extends OpMode {

    private DriveSystem driveSystem;
    private ElevatorSystem elevatorSystem;
    private ElevatorErectionSystem elvatorErectionSystem;

    private RevBlinkinLedDriver ledDriver;

    private boolean RLBPressed = false;
    private boolean XPressed = false;
    private boolean APressed = false;
    private boolean BPressed = false;
    private boolean upPressed = false;
    private boolean YPressed = false;
    private boolean RBPressed = false;
    private boolean LBPressed = false;
    private boolean RSTPressed = false;
    private boolean G2BPressed = false;
    public boolean fildo = true;

    private hangingRobot hangingRobot;
    private LaunchSystem launchSystem;

    private EndPointSystem yks;

    System_Odometry odometry;
    public void reInit(){

    }

    @Override
    public void init() {
        odometry = new System_Odometry(hardwareMap);

        if (driveSystem==null){
            driveSystem = new DriveSystem(hardwareMap,gamepad1,0);
        }

        yks = new EndPointSystem(hardwareMap);
        elevatorSystem = new ElevatorSystem(hardwareMap);
        elvatorErectionSystem = new ElevatorErectionSystem(hardwareMap);
        hangingRobot = new hangingRobot(hardwareMap);
        launchSystem = new LaunchSystem(hardwareMap);

        ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

        telemetry.addData("using", DcMotor.RunMode.RUN_USING_ENCODER.equals(DcMotor.RunMode.RUN_WITHOUT_ENCODER));
        telemetry.addData("endInit", "");
        telemetry.update();

        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_LAVA_PALETTE);

//        hardwareMap.servo.get("erectionYks").setPosition(0);
    }

    Thread plane = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true){
                if (gamepad1.y && !YPressed) {
                    YPressed = true;
                    launchSystem.Activate();
                } else if (!gamepad1.y) {
                    YPressed = false;
                }
            }
        }
    });

    @Override
    public void start(){
        plane.start();
    }

    @Override
    public void loop() {
        reInit();
        odometry.Odometry();
        telemetry.addData("LRA", "%6d %6d %6d", odometry.currentLeftPosition, odometry.currentRightPosition, odometry.currentAuxPosition);
        telemetry.addData("xyh", "%6.1f cm %6.1f cm %6.1f deg", odometry.pos.getX(), odometry.pos.getY(),Math.toDegrees(odometry.pos.getH()));
        telemetry.addData("pos",elvatorErectionSystem.getErection());
        telemetry.update();

        if (fildo){
        driveSystem.Drive();
        }
        else driveSystem.DriveWithoutFildo();

        if (gamepad2.a){
            fildo = false;
        }





//        if(gamepad1.right_bumper) {
//            elvatorErectionSystem.elevatorUp();
//        }
//        if(gamepad1.left_bumper) {
//            elvatorErectionSystem.elevatorDown();
//        }
//        if(!gamepad1.left_bumper && !gamepad1.right_bumper){
//            elvatorErectionSystem.elevatorToZero();
//        }
//
//        if (gamepad1.right_trigger > 0)
//            elevatorSystem.Extension(gamepad1.right_trigger);
//        if (gamepad1.left_trigger > 0)
//            elevatorSystem.LowerElevator(gamepad1.left_trigger);
//        if (gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0){
//            elevatorSystem.StopElevator();
//        }

        if (gamepad1.right_bumper) {
            elevatorSystem.Extension();
        }
        if (gamepad1.left_bumper) {
            elevatorSystem.LowerElevator();
        }
        if (!gamepad1.left_bumper && !gamepad1.right_bumper) {
            elevatorSystem.StopElevator();
        }

        elvatorErectionSystem.raisedAndlowerErection(gamepad1.right_trigger - gamepad1.left_trigger);

        if (gamepad1.y && !YPressed) {
            YPressed = true;
            launchSystem.Activate();
        } else if (!gamepad1.y) {
            YPressed = false;
        }

        if (gamepad1.a && !APressed) {
            APressed = true;
            yks.YksLactive();
//            if(yks.yksLeft.getPosition() == 0){
//                yks.openYksLeft();
//            }else if(yks.yksLeft.getPosition() != 0){
//                yks.closeYksLeft();
//            }
        } else if (!gamepad1.a) {
            APressed = false;
        }

        if (gamepad1.b && !BPressed) {
            BPressed = true;
            yks.YksRactive();
//            if(yks.yksRight.getPosition()==1){
//                yks.openYksRight();
//            }else if(yks.yksRight.getPosition()!= 1){
//                yks.closeYksRight();
//            }
        }

        if (!gamepad1.b) {
            BPressed = false;
        }

        if (gamepad1.x && !XPressed) {
            XPressed = true;
            hangingRobot.activateHanging();
        }
        if (!gamepad1.x)
            XPressed = false;

        if (gamepad1.dpad_up && !upPressed) {
            upPressed = true;
            yks.openErectionYks();
        }
        if (!gamepad1.dpad_up) {
            upPressed = false;
        }
        if (gamepad1.dpad_down) {
            yks.closeErectionYks();
        }

        if (gamepad2.start){
            stop();
            init();
        }

        if(gamepad1.left_stick_button){
            driveSystem.Estop();
            driveSystem = new DriveSystem(hardwareMap, gamepad1, 0);
        }

        if (gamepad2.b &&! G2BPressed){
            G2BPressed = true;

            hangingRobot.activateHanging();
            elevatorSystem.CloseElevator();



        }
        else if(!gamepad2.b){
            G2BPressed = false;
        }

        //automatic Yks Erection

       // if (elvatorErectionSystem.getErection() == 0){
       //     yks.YksSetPosition(0);
       // }
        //else if (elvatorErectionSystem.getErection() <= 400 && elvatorErectionSystem.getErection() != 0){
          //  yks.YksSetPosition(0.3);
        //}
        //else if (elvatorErectionSystem.getErection() >= 1400 && elvatorErectionSystem.getErection() < 1800){
          //  yks.YksSetPosition(0.7);
        //}
        //else if (elvatorErectionSystem.getErection() <= 1800 && elvatorErectionSystem.getErection() < 2000){
          //  yks.YksSetPosition(0.6);
        //}
        //else if (elvatorErectionSystem.getErection() == 2000){
         //   yks.YksSetPosition(0.45);
        //}


    if(gamepad1.right_stick_button) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                yks.closeErectionYks();
                sleep(500);
                elevatorSystem.CloseElevator();
            }
        }).start();
    }
    }

    @Override
    public void stop() {
        plane.interrupt();
        driveSystem = null;
    }
}
