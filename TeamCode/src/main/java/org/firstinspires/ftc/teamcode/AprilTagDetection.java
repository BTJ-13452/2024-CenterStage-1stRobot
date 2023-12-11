//package org.firstinspires.ftc.teamcode;
//
//import android.util.Size;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//import java.util.List;
//
//@TeleOp
//public class AprilTagDetection extends LinearOpMode {
//
//    private AprilTagProcessor aprilTag;
//
//    private VisionPortal visionPortal;
//
//
//
//    @Override
//    public void runOpMode() {
//        initAprilTag();
//
//        waitForStart();
//        while (opModeIsActive()) {
//            telemetryAprilTag();
//            telemetry.update();
//        }
//        visionPortal.close();
//    }
//
//    private void initAprilTag() {
//        aprilTag = new AprilTagProcessor.Builder()
//                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
//                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
//            .build();
//
//        VisionPortal.Builder builder = new VisionPortal.Builder();
//        builder.setCamera(hardwareMap.get(WebcamName.class, "AprilTagCamera"));
//        builder.setCameraResolution(new Size(640, 480));
//        builder.enableCameraMonitoring(true);
//        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
//        builder.setAutoStopLiveView(true);
//        builder.addProcessor(aprilTag);
//
//        visionPortal = builder.build();
//
//    }
//
//    private void telemetryAprilTag() {
//        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
//        telemetry.addData("# AprilTags Detected", currentDetections.size());
//        for (AprilTagDetection detection : currentDetections) {
//            if (detection.metadata != null) {
//                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
//                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
//                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
//            } else {
//                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
//                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
//            }
//        }
//        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
//        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
//        telemetry.addLine("RBE = Range, Bearing & Elevation");
//    }
//
//}
