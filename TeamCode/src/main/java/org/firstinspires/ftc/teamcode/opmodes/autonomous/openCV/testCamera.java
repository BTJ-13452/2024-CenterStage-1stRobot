package org.firstinspires.ftc.teamcode.opmodes.autonomous.openCV;

import static org.firstinspires.ftc.teamcode.opmodes.autonomous.openCV.Processor.opMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

@Autonomous
//@Disabled
public class testCamera extends LinearOpMode {

    OpenCvWebcam webcam;
    CenterStageDeterminationPipeline pipeline;
    CenterStageDeterminationPipeline.CSPosition snapshotAnalysis = CenterStageDeterminationPipeline.CSPosition.LEFT;

    @Override
    public void runOpMode() throws InterruptedException {
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"));
        pipeline = new CenterStageDeterminationPipeline(this);
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

        while (!isStarted() && !isStopRequested())
        {
//            telemetry.addData("Realtime analysis", pipeline.getAnalysis());
//            telemetry.update();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }
    }

    public static class CenterStageDeterminationPipeline extends OpenCvPipeline {

        public enum CSPosition {
            LEFT,
            CENTER,
            RIGHT
        }

        static final Scalar Black = new Scalar(0, 0, 0);

        static final Point CENTER_TOPLEFT_ANCHOR_POINT = new Point(20, 300);
        static final Point CENTER_BUTTOMRIGHT_ANCHOR_POINT = new Point(250, 450);
        static final Point RIGHT_TOPLEFT_ANCHOR_POINT = new Point(400, 300);
        static final Point RIGHT_BUTTOMRIGHT_ANCHOR_POINT = new Point(530, 450);

        Mat middle_Cb, right_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        int middleAvg, rightAvg;

        private volatile CSPosition position = CSPosition.LEFT;

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

            int x = rightAvg + middleAvg;

            if (rightAvg < middleAvg && rightAvg < 70 && x < 170) {
                position = CSPosition.RIGHT;
            }  if (middleAvg < rightAvg && middleAvg < 80) {
                position = CSPosition.CENTER;
            }  if (middleAvg>rightAvg && rightAvg < 110&& x > 170){
                position = CSPosition.LEFT;
            }

            opMode.telemetry.addData("Right Avg: ", rightAvg);
            opMode.telemetry.addData("Middle Avg: ", middleAvg);
            opMode.telemetry.addData("Realtime analysis", getAnalysis());
            opMode.telemetry.update();

            return input;
        }
        private final LinearOpMode opMode;  // Add this field

        public CenterStageDeterminationPipeline(LinearOpMode opMode) {
            this.opMode = opMode;
        }
        public CSPosition getAnalysis()
        {
            return position;
        }
    }

}
