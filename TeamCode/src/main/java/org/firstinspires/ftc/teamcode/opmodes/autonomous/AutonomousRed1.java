package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.ScanProp;

@Autonomous
//@Disabled
public class AutonomousRed1 extends LinearOpMode {
    final int  ENGEL_TO_PROP = 45;
    ScanProp scanProp;
    DriveSystem driveSystem;
 //   EndPoint endPoint;

    @Override
    public void runOpMode() throws InterruptedException {
        scanProp = new ScanProp(hardwareMap);
        driveSystem = new DriveSystem(hardwareMap);

 //    do {
 //        scanProp.scan();
 //    }while (opModeInInit());

 //    waitForStart();

 //    activateRobot.start();

 //    while (opModeIsActive()){}

 //    activateRobot.interrupt();

    }

// Thread activateRobot = new Thread(new Runnable() {
//     @Override
//     public void run() {
//         if (scanProp.propPosition == 3) {
//             driveSystem.driveByEncoder(1, 0, 78, 0);
//         }

//         if (scanProp.propPosition == 2) {
//             driveSystem.driveByEncoder(1, 0, 40, ENGEL_TO_PROP);
//         }
//         if (scanProp.propPosition == 1) {
//             driveSystem.driveByEncoder(1, 0, 40, -ENGEL_TO_PROP);
//         }
//         endPoint.RelesePixel();
//         driveSystem.driveByEncoder(0.5, 90,0,0 );

//     }
// });
}