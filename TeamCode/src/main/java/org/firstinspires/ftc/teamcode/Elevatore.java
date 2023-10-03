package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Elevatore {
    DcMotor elevator;
    TouchSensor magneticSwitch;
    final double Speed = -0.4;
    final int ELEVATOR_UP_HEIGHT =1000;
    boolean isUp=false;

    public Elevatore(HardwareMap map){
//        elevator = map.dcMotor.get()
//        magneticSwitch= hardwareMap.get(DigitalDevice.class, "switch");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LowerElevator();
    }
    public void RaiseElevator(){
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevator.setTargetPosition(ELEVATOR_UP_HEIGHT);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(1);
    }

    public void  LowerElevator (){
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setPower(Speed);
        if(magneticSwitch.isPressed()){
            elevator.setPower(0);
            elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}
