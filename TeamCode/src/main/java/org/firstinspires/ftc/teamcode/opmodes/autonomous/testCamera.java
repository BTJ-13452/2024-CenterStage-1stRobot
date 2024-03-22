package org.firstinspires.ftc.teamcode.opmodes.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.ftdi.eeprom.FT_EEPROM_232H;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

//@Autonomous

//@Disabled
public class testCamera{

    public OpenCvWebcam webcam;
    CenterStageDeterminationPipeline pipeline;
    CenterStageDeterminationPipeline.CSPosition snapshotAnalysis = CenterStageDeterminationPipeline.CSPosition.LEFT;
    double X_middle,  Y_middle,  X_right,  Y_right, X_middle_down, Y_middle_down,X_right_down,Y_right_down ;

    public testCamera(HardwareMap map, LinearOpMode mode, double X_middle, double Y_middle, double X_right, double Y_right, double X_middle_down, double Y_middle_down, double X_right_down, double  Y_right_down ){
        webcam = OpenCvCameraFactory.getInstance().createWebcam(map.get(WebcamName.class, "Webcam 1"));
        pipeline = new CenterStageDeterminationPipeline(mode, X_middle,  Y_middle,  X_right,  Y_right,  X_middle_down,  Y_middle_down,  X_right_down,   Y_right_down);
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(640,480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });
        this.X_middle = X_middle;
        this.Y_middle = Y_middle;
        this.X_right = X_right;
        this.Y_right = Y_right;
        this.X_middle_down = X_middle_down;
        this.Y_middle_down = Y_middle_down;
        this.X_right_down = X_right_down;
        this.Y_right_down = Y_right_down;
    }


    public testCamera(Point h){}
    public static class CenterStageDeterminationPipeline extends OpenCvPipeline {
        double X_middle,  Y_middle,  X_right,  Y_right, X_middle_down, Y_middle_down,X_right_down,Y_right_down ;

        public CenterStageDeterminationPipeline (LinearOpMode opMode, double X_middle, double Y_middle, double X_right, double Y_right, double X_middle_down, double Y_middle_down, double X_right_down, double  Y_right_down ){
            this.X_middle = X_middle;
            this.Y_middle = Y_middle;
            this.X_right = X_right;
            this.Y_right = Y_right;
            this.X_middle_down = X_middle_down;
            this.Y_middle_down = Y_middle_down;
            this.X_right_down = X_right_down;
            this.Y_right_down = Y_right_down;
            this.opMode = opMode;
            CENTER_TOPLEFT_ANCHOR_POINT = new Point(X_middle, Y_middle);
            CENTER_BUTTOMRIGHT_ANCHOR_POINT = new Point(X_middle_down, Y_middle_down);
            RIGHT_TOPLEFT_ANCHOR_POINT = new Point(X_right, Y_right);
            RIGHT_BUTTOMRIGHT_ANCHOR_POINT = new Point(X_right_down, Y_right_down);
        }
        public enum CSPosition {
            LEFT,
            CENTER,
            RIGHT
        }

        static final Scalar Black = new Scalar(0, 0, 0);
// middle
       Point CENTER_TOPLEFT_ANCHOR_POINT ;
        Point CENTER_BUTTOMRIGHT_ANCHOR_POINT ;
//right
        Point RIGHT_TOPLEFT_ANCHOR_POINT;
        Point RIGHT_BUTTOMRIGHT_ANCHOR_POINT ;

        Mat middle_Cb, right_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        int middleAvg, rightAvg;

        private static volatile CSPosition position = CSPosition.LEFT;


        @Override
        public void init(Mat firstFrame)
        {
            /*
             * We need to call this in order to make sure the 'Cb'
             * object is initialized, so that the submats we make
             * will still be linked to it on subsequent frames. (If
             * the object were to only be initialized in processFrame,
             * then the submats would become delinked because the backing
             * buffer would be re-allocated the first time a real frame
             * was crunched)
             */
            inputToCb(firstFrame);

            /*
             * Submats are a persistent reference to a region of the parent
             * buffer. Any changes to the child affect the parent, and the
             * reverse also holds true.
             */
            middle_Cb = Cb.submat(new Rect(CENTER_TOPLEFT_ANCHOR_POINT, CENTER_BUTTOMRIGHT_ANCHOR_POINT));
            right_Cb = Cb.submat(new Rect(RIGHT_TOPLEFT_ANCHOR_POINT, RIGHT_BUTTOMRIGHT_ANCHOR_POINT));
        }

        void inputToCb(Mat input)
        {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 0);
        }
        @Override
        public Mat processFrame(Mat input) {
            inputToCb(input);

            rightAvg =(int) Core.mean(right_Cb).val[0];
            middleAvg =(int) Core.mean(middle_Cb).val[0];

            Imgproc.rectangle(
                    input, // Buffer to draw on
                    CENTER_TOPLEFT_ANCHOR_POINT, // First point which defines the rectangle
                    CENTER_BUTTOMRIGHT_ANCHOR_POINT, // Second point which defines the rectangle
                    Black, // The color the rectangle is drawn in
                    4);
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    RIGHT_TOPLEFT_ANCHOR_POINT, // First point which defines the rectangle
                    RIGHT_BUTTOMRIGHT_ANCHOR_POINT, // Second point which defines the rectangle
                    Black, // The color the rectangle is drawn in
                    4);


            if (rightAvg < 100) {
                position = CSPosition.RIGHT;
            } else if (middleAvg < 100) {
                position = CSPosition.CENTER;
            }
               else position = CSPosition.LEFT;


            opMode.telemetry.addData("Right Avg: ", rightAvg);
            opMode.telemetry.addData("Middle Avg: ", middleAvg);
            opMode.telemetry.addData("Realtime analysis", getAnalysis());
            opMode.telemetry.update();

            return input;
        }
        private final LinearOpMode opMode;  // Add this field

        public static int getPosition() {
            if(getAnalysis() == CSPosition.LEFT){
                return 1;
            }
            else if(getAnalysis() == CSPosition.CENTER){
                return 2;
            }
            else {
                return 3;
            }
        }

        public static CSPosition getAnalysis()
        {
            return position;
        }
    }
}
