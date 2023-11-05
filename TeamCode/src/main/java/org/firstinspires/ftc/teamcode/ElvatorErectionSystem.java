package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ElvatorErectionSystem {

    final int[] positions =new int[]{0,20,40,60};
   // final double power = 1;
    int position = 0;
    public DcMotor Lm;
    public DcMotor Rm;
    TouchSensor magneticSwitch;
    public ElvatorErectionSystem(HardwareMap map){
        Lm = map.dcMotor.get("lm");
        Rm = map.dcMotor.get("rm");
        Lm.setDirection(DcMotorSimple.Direction.REVERSE);
        Lm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Rm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        magneticSwitch = map.get(TouchSensor.class, "erectionSwitch");
        LowerHeight(-1);
//        Lm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Rm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


//        Lm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        Rm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
//    public void telemetry1()
//    {
//        telemetry.addData("lm",Lm.getCurrentPosition());
//        telemetry.addData("rm",Rm.getCurrentPosition());
//        telemetry.update();
//    }

 /*   public void elevatorHeight(boolean up, boolean down){

        if(up) {
            position++;
            if(position >= positions.length){
                position = positions.length - 1;
            }
        }
        if(down){
            position--;
            if(position < 0){
                position = 0;
            }
        }
        Lm.setTargetPosition(positions[position]);
        Rm.setTargetPosition(positions[position]);

        Lm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Rm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Lm.setPower(power);
        Rm.setPower(power);
    }*/ //פונקציה של יהושע

    public void elevatorUp(){
        Lm.setPower(1);
        Rm.setPower(1);
    }
    public void elevatorDown(){
        Lm.setPower(-1);
        Rm.setPower(-1);

//        LowerHeight(-1);
    }
    public void elevatorToZero(){
        Lm.setPower(0);
        Rm.setPower(0);

    }

    public void LowerHeight(int power){
       while (!magneticSwitch.isPressed()) {
           Lm.setPower(power);
           Rm.setPower(power);

       }
       Lm.setPower(0);
       Rm.setPower(0);
    }
}
