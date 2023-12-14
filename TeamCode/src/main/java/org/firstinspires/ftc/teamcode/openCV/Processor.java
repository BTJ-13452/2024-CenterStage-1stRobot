package org.firstinspires.ftc.teamcode.openCV;

import android.graphics.Canvas;
import android.graphics.Paint;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;

import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
public class Processor implements VisionProcessor {

    OpMode opMode;

    Mat leftCrop;

    Mat middleCrop;
    double leftAvg;
    double middleAvg;
    int place;
    Paint mainPaint;

    Processor(OpMode opMode) {
        this.opMode = opMode;

        mainPaint = new Paint();
        mainPaint.setARGB(255, 0, 0, 255);
        mainPaint.setStrokeWidth(3);
    }
    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
//        rightCrop = frame.submat(new Rect(0, 0, frame.width()/2, frame.height()));
//        leftCrop = frame.submat(new Rect(frame.width()/2, 0, frame.width()/2, frame.height()));
//        leftCrop = frame.submat(new Rect(frame.width()/2, 0, frame.width()/2, frame.height()));
        int frameThird = (int) frame.width()/3;
        opMode.telemetry.addData("hieght: ", frame.height());
        opMode.telemetry.addData("width: ", frame.width());
        Point point1 = new Point(100,100);// שמאל למעלה
        Point point2 = new Point(500,300);//ימין למטה
        Scalar color = new Scalar(64,64,64);
        int thickness = 10;
        Imgproc.rectangle(frame, point1,point2,color,thickness);
//
//        leftCrop = frame.submat(new Rect(0, 0, frame.width()/3, frame.height()/3));
//        middleCrop = frame.submat(new Rect(frame.width()/3 +50 , 0, frame.width()/3 +40, frame.height()/3));
//
//
//        Core.extractChannel(leftCrop, leftCrop, 0);
//        Core.extractChannel(middleCrop, middleCrop, 0);
//
//        leftAvg = Core.mean(leftCrop).val[0];
//        middleAvg = Core.mean(middleCrop).val[0];
//
//        if (leftAvg> middleAvg) {
//            opMode.telemetry.addData("Side", "Left");
//            place =0;
//        } else if (middleAvg>leftAvg) {
//            opMode.telemetry.addData("Side", "Right");
//        } else if(leftCrop.empty() || middleCrop.empty()) {
//            opMode.telemetry.addData("Side", "right");
//        }
        opMode.telemetry.update();

        return frame;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        int widthRight = onscreenWidth/3;


    //   canvas.drawRect(0, 0, onscreenWidth/3, onscreenHeight/3, mainPaint);
      // canvas.drawRect(onscreenWidth/3, 0, 2 *(onscreenWidth/3) + 150 , onscreenHeight/3, mainPaint);

    }

}
