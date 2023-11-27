package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class exmpleOpenCV extends OpMode {

    OpenCvWebcam webcam1 = null;
    @Override
    public void init() {

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam1");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam1 = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        webcam1.setPipeline(new exmplePipeLine());

        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam1.startStreaming(exmplePipeLine.IMG_WIDTH, exmplePipeLine.IMG_HEIGHT , OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });


    }

    @Override
    public void loop() {

    }

    class exmplePipeLine extends OpenCvPipeline{


        static public final int IMG_WIDTH = 640;
        static public final int IMG_HEIGHT = 320;

        Mat YCbCr = new Mat();
        Mat leftCrop;
        Mat centerCrop;
        Mat rightCrop;
        double leftavgfin;
        double centeravgfin;
        double rightavgfin;
        Mat outPut = new Mat();
        Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

        public Mat processFrame(Mat input){

            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
            telemetry.addLine("pipline running");

            int rectRange = IMG_WIDTH / 3;

//            Rect leftRect   = new Rect(1              , 1, rectRange, IMG_HEIGHT - 1);
//            Rect centerRect = new Rect(1 + rectRange  , 1, rectRange, IMG_HEIGHT - 1);
//            Rect rightRect  = new Rect(1 + 2*rectRange, 1, rectRange, IMG_HEIGHT - 1);

            Rect leftRect = new Rect(1,1,200,319);
            Rect centerRect = new Rect(106,1,150,319);
            Rect rightRect = new Rect(213,1,200,319);

            input.copyTo(outPut);
            Imgproc.rectangle(outPut, leftRect, rectColor, 2);
            Imgproc.rectangle(outPut, rightRect, rectColor, 2);
            Imgproc.rectangle(outPut, centerRect, rectColor, 2);

            leftCrop = YCbCr.submat(leftRect);
            centerCrop = YCbCr.submat(centerRect);
            rightCrop = YCbCr.submat(rightRect);

            Core.extractChannel(leftCrop, leftCrop, 2);
            Core.extractChannel(rightCrop, rightCrop, 2);
            Core.extractChannel(centerCrop, centerCrop, 2);

            Scalar leftavg = Core.mean(leftCrop);
            Scalar centeravg = Core.mean(centerCrop);
            Scalar rightavg = Core.mean(rightCrop);

            leftavgfin = leftavg.val[0];
            centeravgfin = centeravg.val[0];
            rightavgfin = rightavg.val[0];


//                telemetry.addLine("pipline running");
//                telemetry.addLine("pipline running");

            telemetry.addData("leftavgfin", leftavgfin);
            telemetry.addData("centeravgfin",  centeravgfin);
            telemetry.addData("rightavgfin",  rightavgfin);


            if(leftavgfin < centeravgfin && leftavgfin < rightavgfin) {
                telemetry.addLine("left");
            }
            else if( rightavgfin < centeravgfin &&   rightavgfin<leftavgfin){
                telemetry.addLine("right");
            }
            else if(centeravgfin < leftavgfin &&  centeravgfin < rightavgfin){
                telemetry.addLine("center");
            }

            return (outPut);


        }
    }

}
