package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ElevatorSystem {
    DcMotor elevator;
    TouchSensor magneticSwitch;
    final double Speed =0.8;
    final int MAX_HEIGHT = 1000;

    public ElevatorSystem(HardwareMap map){
        elevator = map.dcMotor.get("Elevator");
        magneticSwitch= map.get(TouchSensor.class, "elevatorSwitch");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        CloseElevator();
    }

    public void Extension(){
     if (elevator.getCurrentPosition()>-1600){
         elevator.setPower(-0.8);
     }
    else elevator.setPower(0);

    }

    public void LowerElevator (){
        if (!magneticSwitch.isPressed()){
            elevator.setPower(0.8);
        }
    }

    public void StopElevator(){
        elevator.setPower(0);
    }

    public void CloseElevator (){
       while(!magneticSwitch.isPressed()){
            elevator.setPower(Speed);
       }
       elevator.setPower(0);
       //elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
