package org.firstinspires.ftc.teamcode.opmodes.autonomous.openCV;

import android.graphics.Canvas;
import android.graphics.Paint;


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

    Mat rightCrop;

    Mat middleCrop;
    double rightAvg;
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
    public   Object processFrame(Mat frame, long captureTimeNanos) {
        middleCrop = frame.submat(new Rect(30, 100, 350, 250));
        rightCrop = frame.submat(new Rect(450, 100, 185, 250));
//        leftCrop = frame.submat(new Rect(frame.width()/2, 0, frame.width()/2, frame.height()));

        opMode.telemetry.addData("height: ", frame.height());
        opMode.telemetry.addData("width: ", frame.width());
        //זיהוי  אמצעי
        Point point1 = new Point(30,100);// שמאל למעלה
        Point point2 = new Point(400,350);//ימין למטה
        Scalar color = new Scalar(64,64,64);
        int thickness = 10;
        Imgproc.rectangle(frame, point1,point2,color,thickness);

        //זיהוי צד ימין
        Point point3 = new Point(450,100);// שמאל למעלה
        Point point4 = new Point(650,350);//ימין למטה
        Scalar color1 = new Scalar(64,44,64);
        Imgproc.rectangle(frame, point3,point4,color1,thickness);


//
//        leftCrop = frame.submat(new Rect(0, 0, frame.width()/3, frame.height()/3));
//        middleCrop = frame.submat(new Rect(frame.width()/3 +50 , 0, frame.width()/3 +40, frame.height()/3));
//
//
        Core.extractChannel(rightCrop, rightCrop, 0);
        Core.extractChannel(middleCrop, middleCrop, 0);
//
        rightAvg = Core.mean(rightCrop).val[0];
        middleAvg = Core.mean(middleCrop).val[0];
//
        if (rightAvg> middleAvg) {
            opMode.telemetry.addData("Side", "Right");
            place =0;
        } else if (middleAvg>rightAvg) {
            opMode.telemetry.addData("Side", "middle");
        } else if(rightCrop.empty() || middleCrop.empty()) {
            opMode.telemetry.addData("Side", "empty");
        }
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
