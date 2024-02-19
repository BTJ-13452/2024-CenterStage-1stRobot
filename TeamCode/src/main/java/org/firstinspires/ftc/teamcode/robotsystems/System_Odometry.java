package org.firstinspires.ftc.teamcode.robotsystems;

import android.graphics.Paint;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.security.PublicKey;

public class System_Odometry {


    public DcMotor rightFrontDrive;
    public DcMotor rightBackDrive;
    public DcMotor leftFrontDrive;
    public DcMotor leftBackDrive;

    public DcMotor encoderRight;
    public DcMotor encoderLeft;
    public DcMotor encoderAux;

    int rightStartPos;
    int leftStartPos;
    int auxStartPos;


    public System_Odometry (HardwareMap map){

        rightFrontDrive = map.dcMotor.get("UR");
        rightBackDrive = map.dcMotor.get("DR");
        leftFrontDrive = map.dcMotor.get("UL");
        leftBackDrive = map.dcMotor.get("DL");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);

        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        encoderAux = rightBackDrive ;
        encoderRight = rightFrontDrive;
        encoderLeft = leftBackDrive;                                                                                                                   ;

        auxStartPos = encoderAux.getCurrentPosition();
        rightStartPos = encoderRight.getCurrentPosition();
        leftStartPos = encoderLeft.getCurrentPosition();
    }
    public static final double L = 23;
    public static final double B = 8.5;
    public static final double R = 2.4;
    public static final double N = 2000;
    public static final double CM_per_ticks = 2.0*Math.PI * R/N;

    public int currentRightPosition = 0;
    public int currentLeftPosition = 0;
    public int currentAuxPosition = 0;

    private int oldRightPosition = 0;
    private int oldLeftPosition = 0;
    private int oldAuxPosition = 0;

    public  XYHVector START_POS = new XYHVector(0.0,0.0,Math.toRadians(0));
    public XYHVector pos = new XYHVector(START_POS);



    public void Odometry(){
        oldRightPosition = currentRightPosition;
        oldAuxPosition = currentAuxPosition;
        oldLeftPosition = currentLeftPosition;

        currentRightPosition = -encoderRight.getCurrentPosition() + rightStartPos;
        currentLeftPosition = -encoderLeft.getCurrentPosition() + leftStartPos;
        currentAuxPosition = encoderAux.getCurrentPosition() - auxStartPos;

        int d1 = currentLeftPosition - oldLeftPosition;
        int d2 = currentRightPosition - oldRightPosition;
        int d3 = currentAuxPosition - oldAuxPosition;

        double dtheta = CM_per_ticks * (d2-d1) / L;
        double dx = CM_per_ticks * (d1+d1) / 2.0;
        double dy = CM_per_ticks * (d3-(d2-d1) * B/L) ;

        double theta = pos.h + (dtheta/2.0);
        pos.setX(dx * Math.cos(theta) - dy * Math.sin(theta)+pos.x);
        pos.setY( dx * Math.sin(theta) + dy * Math.cos(theta)+ pos.y);
        pos.setH(dtheta+pos.h);
    }

    public class XYHVector{
        public double getX() {
            return -x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getH() {
            return h;
        }

        public void setH(double h) {
            while(h>2*Math.PI){
                h -= 2*Math.PI;
            }
            while (h<0){
                h+=2*Math.PI;
            }
            this.h = h;
        }

        private double x;
        private double y;
        private double h;

        public void reset(){
            setX(0);
            setY(0);
            setH(0);
        }


        public XYHVector ( double x,double y, double h){
           setH(h);
           setX(x);
           setY(y);
        }

        public XYHVector (XYHVector XYH ){
            setH(h);
            setX(x);
            setY(y);
        }
    }
}

