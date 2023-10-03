package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Telop extends OpMode {
    Elevatore elevatore;
    DroneLaunchere launchere;
    @Override
    public void init() {
        elevatore=new Elevatore(hardwareMap);
        launchere=new DroneLaunchere(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.right_stick_button){
            launchere.launche();
        }

        if (gamepad1.right_bumper) {
            elevatore.RaiseElevator();
        }
        telemetry.addData("verification",elevatore.elevator.getCurrentPosition());
        telemetry.update();







    }


}
