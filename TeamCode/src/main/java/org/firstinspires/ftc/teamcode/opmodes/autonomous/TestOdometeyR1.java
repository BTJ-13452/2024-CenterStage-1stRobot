package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import android.graphics.Region;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.robotsystems.ElevatorErectionSystem;
import org.firstinspires.ftc.teamcode.robotsystems.EndPointSystem;

@Autonomous
@Disabled
public class TestOdometeyR1 extends LinearOpMode {
    DriveSystem drive;
    private ElevatorErectionSystem elvatorErectionSystem;
    private EndPointSystem yks;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new DriveSystem(hardwareMap,gamepad1,0);
        elvatorErectionSystem = new ElevatorErectionSystem(hardwareMap);
        yks = new EndPointSystem(hardwareMap);
        yks.closeYksRight();
        yks.closeYksLeft();

        int position = 1;
        do{
            position = detect();
        } while(opModeInInit());

        waitForStart();

//        drive.driveByOdometry(0,-100,0,telemetry);
        drive.driveByOdometryOnlyFor90Degrees2(Math.toRadians(90), telemetry);

        sleep(1000000000);
        switch (position){
            case 1:

                drive.driveByOdometry(0,-20,0,telemetry,0.7);
                //לפתוח יחידת קצה
                elvatorErectionSystem.erectionTicks(1000);
                yks.openYksRight();
                sleep(1500);
                yks.closeYksRight();
                elvatorErectionSystem.erectionTicks(-1000);
                //ליסגור יחידת קצה
                drive.driveByOdometry(10,30,0,telemetry,0.7);
                drive.turnByOdometry(Math.PI*3/4.0,telemetry);
                elvatorErectionSystem.erectionTicks(650);
                //לפתוח יחידת קצה
                yks.openYksLeft();
                yks.closeYksLeft();
                //לסגור יחידת קצה
                elvatorErectionSystem.erectionTicks(-650);
                drive.driveByOdometry(35,0,0,telemetry,0.7);
                drive.driveByOdometry(0,-30,0,telemetry,0.7);
                break;

            case 2:

                drive.driveByOdometry(0,15,0,telemetry,0.7);
                //לפתוח יחידת קצה
                elvatorErectionSystem.erectionTicks(1000);
                yks.openYksRight();
                sleep(1500);
                yks.closeYksRight();
                elvatorErectionSystem.erectionTicks(-1000);
                //ליסגור יחידת קצה
                drive.driveByOdometry(10,30,0,telemetry,0.7);
                drive.turnByOdometry(Math.PI*3/4.0,telemetry);
                elvatorErectionSystem.erectionTicks(650);
                //לפתוח יחידת קצה
                yks.openYksLeft();
                yks.closeYksLeft();
                //לסגור יחידת קצה
                elvatorErectionSystem.erectionTicks(-650);
                drive.driveByOdometry(35,0,0,telemetry,0.7);
                drive.driveByOdometry(0,-30,0,telemetry,0.7);
                break;

            case 3:
                drive.driveByOdometry(10,20,0,telemetry,0.7);
                drive.turnByOdometry(Math.PI*-1/4.0,telemetry);
                //לפתוח יחידת קצה
                elvatorErectionSystem.erectionTicks(1000);
                yks.openYksRight();
                sleep(1500);
                yks.closeYksRight();
                elvatorErectionSystem.erectionTicks(-1000);
                //ליסגור יחידת קצה
                drive.turnByOdometry(Math.PI*3/4.0,telemetry);
                drive.driveByOdometry(0,-50,0,telemetry,0.7);
                elvatorErectionSystem.erectionTicks(650);
                //לפתוח יחידת קצה
                yks.openYksLeft();
                yks.closeYksLeft();
                //לסגור יחידת קצה
                elvatorErectionSystem.erectionTicks(-650);
                drive.driveByOdometry(35,0,0,telemetry,0.7);
                drive.driveByOdometry(0,-30,0,telemetry,0.7);
                break;
        }
//        drive.driveByOdometry(,-50,0,telemetry);
//        yks.openYksRight();
//
//        sleep(1000);
//
//        drive.turnByOdometry(Math.PI,telemetry);
//
//        sleep(1000);
//
//        drive.driveByOdometry(20,30,0,telemetry);

        while (opModeIsActive()){}
    }

    public int detect(){
        return 1;
    }
}
