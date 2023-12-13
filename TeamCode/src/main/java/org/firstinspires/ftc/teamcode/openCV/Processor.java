package org.firstinspires.ftc.teamcode.openCV;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
public class Processor implements VisionProcessor {

    OpMode opMode;

    Mat leftCrop;
    Mat rightCrop;
    Mat middleCrop;
    double leftAvg;
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
    public Object processFrame(Mat frame, long captureTimeNanos) {
//        rightCrop = frame.submat(new Rect(0, 0, frame.width()/2, frame.height()));
//        leftCrop = frame.submat(new Rect(frame.width()/2, 0, frame.width()/2, frame.height()));
//        leftCrop = frame.submat(new Rect(frame.width()/2, 0, frame.width()/2, frame.height()));
        int frameThird = (int) frame.width()/3;

        rightCrop = frame.submat(new Rect(0, 100, frameThird, frame.height()));
        middleCrop = frame.submat(new Rect(frameThird , 0, frameThird, frame.height()));
        leftCrop = frame.submat(new Rect(frameThird * 2, 0, frameThird, frame.height()));

        Core.extractChannel(leftCrop, leftCrop, 0);
        Core.extractChannel(rightCrop, rightCrop, 0);
        Core.extractChannel(middleCrop, middleCrop, 0);

        leftAvg = Core.mean(leftCrop).val[0];
        rightAvg = Core.mean(rightCrop).val[0];
        middleAvg = Core.mean(middleCrop).val[0];

        if (leftAvg > rightAvg && leftAvg > middleAvg) {
            opMode.telemetry.addData("Side", "Left");
            place =0;
        } else if (rightAvg > middleAvg && rightAvg > leftAvg) {
            opMode.telemetry.addData("Side", "Right");
        } else {
            opMode.telemetry.addData("Side", "Middle");
        }
        opMode.telemetry.addLine("middle:" + middleAvg + "left:" + leftAvg +"right:" + rightAvg);
        opMode.telemetry.update();

        return frame;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
       // canvas.drawLine(onscreenWidth/3, 0, onscreenWidth/3, onscreenHeight, mainPaint);
        canvas.drawRect(onscreenWidth/3, 0, onscreenWidth/3, onscreenHeight, mainPaint);
       // canvas.drawLine(2 * onscreenWidth/3, 0, 2*onscreenWidth/3, onscreenHeight, mainPaint);
    }

}
