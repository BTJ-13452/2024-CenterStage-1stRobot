package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ElevatorSystem  {
    DcMotor elevator;
    TouchSensor magneticSwitch;
    final double Speed = -0.4;
    final int MAX_HEIGHT = 366;

    public ElevatorSystem(HardwareMap map){
        elevator = map.dcMotor.get("Elevator");
        elevator.setDirection(DcMotor.Direction.REVERSE);
        magneticSwitch = map.get(TouchSensor.class, "elevatorSwitch");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LowerElevator();
    }

    public void Extension(double power){

        if((elevator.getCurrentPosition() >= MAX_HEIGHT && power > 0) || (magneticSwitch.isPressed())){
            power = 0;
        }
        elevator.setPower(power);
    }
    public void elevatorUp(){
        elevator.setPower(gamepad1.right_trigger);
        Extension(gamepad1.right_trigger);
    }public void elevatorDown(){
        elevator.setPower(-gamepad1.left_trigger);
        LowerElevator();
    }



    public void LowerElevator (){

       while(!magneticSwitch.isPressed()){
           elevator.setPower(Speed);
       }
       elevator.setPower(0);
    }
}
