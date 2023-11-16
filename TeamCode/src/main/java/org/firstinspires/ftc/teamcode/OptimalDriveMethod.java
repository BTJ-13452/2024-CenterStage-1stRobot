package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp


public class OptimalDriveMethod extends OpMode {

    DcMotor FronttRight;
    DcMotor FrontLeft;
    DcMotor backRight;
    DcMotor backLeft;


    @Override
    public void init() {
        FronttRight = hardwareMap.get(DcMotor.class, "FRwheel");
        backRight = hardwareMap.get(DcMotor.class, "BRwheel");
        backLeft = hardwareMap.get(DcMotor.class, "BLwheel");
        FrontLeft = hardwareMap.get(DcMotor.class, "FLwheel");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);


    }

    @Override
    public void loop() {
        double pawor = gamepad1.left_stick_y;
        double angle;
        if (gamepad1.left_stick_x==0)
            angle=0;
        angle =  (gamepad1.left_stick_y/gamepad1.left_stick_x);
        angle = Math.tan(angle);
        drive(pawor,angle);
    }

    public void  drive (double pawor, double angle)
    {

        FronttRight.setPower(pawor);
        FrontLeft.setPower(pawor);
        backRight.setPower(pawor);
        backLeft.setPower(pawor);

//        double f1;
//        double f2;
//
//        if (angle>0&angle<90||angle>180&angle<270){
//
//            f1 = -1*pawor*Coefficient1(angle);
//            f2 = pawor;
//
//
//            FronttRight.setPower(f1);
//            backLeft.setPower(f1);
//            FrontLeft.setPower(f2);
//            backRight.setPower(f2);
//        }
//
//        if (angle>90&angle<180||angle>270&angle<360)
//        {
//            f1 = pawor;
//            f2 = -1 * pawor * Coefficient2(angle);
//
//            FronttRight.setPower(f1);
//            backLeft.setPower(f1);
//            FrontLeft.setPower(f2);
//            backRight.setPower(f2);
//
//        }
    }


//    double f1;
//    double f2;
//    //  .פונקציה לעבודה ולהדגמה  ל-א ה-א-מ-י-ת-י   (pawor זה כוח),  (Coefficient זה זווית)
//    public void  drive1 (double pawor, double angle)
//    {
//        if (angle>0&angle<90||angle>180&angle<270){
//
//                f1 = -1*pawor*Coefficient1(angle);
//                f2 = pawor;
//
//
//            FronttRight.setPower(f1);
//            backLeft.setPower(f1);
//            FrontLeft.setPower(f2);
//            backRight.setPower(f2);
//        }
//
//        if (angle>90&angle<180||angle>270&angle<360)
//        {
//            f1 = pawor;
//            f2 = -1 * pawor * Coefficient2(angle);
//
//            FronttRight.setPower(f1);
//            backLeft.setPower(f1);
//            FrontLeft.setPower(f2);
//            backRight.setPower(f2);
//
//        }
//    }

    public double Coefficient1 (double a){
        double x = (1-Math.tan(a));
        x = (x/(1+Math.tan(a)));
        return x;
    }
    public double Coefficient2 (double a){
        double x = (Math.tan(a)+1);
        x = (x/(1-Math.tan(a)));
        return x;
    }

}
