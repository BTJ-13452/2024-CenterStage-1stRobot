package org.firstinspires.ftc.teamcode.opmodes.autonomous.openCV;

import static android.os.SystemClock.sleep;

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

    static OpMode opMode;

    Mat rightCrop = new Mat();

    Mat middleCrop = new Mat();
    static double rightAvg;
    static double middleAvg;


    int place;
    Paint mainPaint;

    public Processor(OpMode opMode) {
        Processor.opMode = opMode;
        mainPaint = new Paint();
        mainPaint.setARGB(0, 0, 0, 0);
        mainPaint.setStrokeWidth(3);
    }
    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        middleCrop = frame.submat(new Rect(20, 50, 200, 200));
        rightCrop = frame.submat(new Rect(450, 60, 180, 190));
//        leftCrop = frame.submat(new Rect(frame.width()/2, 0, frame.width()/2, frame.height()));

       // opMode.telemetry.addData("height: ", frame.height());
       // opMode.telemetry.addData("width: ", frame.width());
        //זיהוי  אמצעי
        Point point1 = new Point(20,50);// שמאל למעלה
        Point point2 = new Point(220,250);//ימין למטה
        Scalar color = new Scalar(64,64,64);
        int thickness = 10;
        Imgproc.rectangle(frame, point1,point2,color,thickness);

        //זיהוי צד ימין
        Point point3 = new Point(450,60);
        Point point4 = new Point(630,250);
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
        rightAvg =(int) Core.mean(rightCrop).val[0];
        middleAvg =(int) Core.mean(middleCrop).val[0];
//
//        if (rightAvg< middleAvg && rightAvg < 110) {
//            opMode.telemetry.addData("Side", "Right");
//            place =1;
//        } else if (middleAvg<rightAvg && middleAvg<110) {
//            opMode.telemetry.addData("Side", "middle");
//            place =2;
//        } else {
//            opMode.telemetry.addData("Side", "left");
//            place =3;
//        }


        opMode.telemetry.addData( "Right:  ", rightAvg);
        opMode.telemetry.addData( "middle:  ", middleAvg);
        opMode.telemetry.update();

        return frame;
    }

    public static int whichSideIf(){
        if (rightAvg< middleAvg && rightAvg < 110) {
            return 1;
        } else if (middleAvg<rightAvg && middleAvg<110) {

           return 2;
        } else {

            return 3;
        }
    }
    public static int findHighest(int right, int middle, int left) {
        if (right >= middle && right >= left) {
            opMode.telemetry.addData("Side", "right");
            return 1;
        } else if (middle >= right && middle >= left) {
            opMode.telemetry.addData("Side", "middle");
            return 2;
        } else {
            opMode.telemetry.addData("Side", "left");
            return 3;
        }
    }
    public static int whichSide(){
      int right = 0, left = 0, middle = 0;
        final boolean[] aa = {true};
        while(aa[0]){
      new Thread(new Runnable() {
          @Override
          public void run() {
             sleep(1000);
             aa[0] = false;
          }

      }).start();
      switch (whichSideIf()){
          case 1:
              right++;
              break;
          case 2:
              middle++;
              break;
          case 3:
              left++;
              break;
      }

      }
      return findHighest(right,middle,left);

    }



    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }


}
