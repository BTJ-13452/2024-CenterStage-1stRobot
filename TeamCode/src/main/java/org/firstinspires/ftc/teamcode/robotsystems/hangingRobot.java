package org.firstinspires.ftc.teamcode.robotsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class hangingRobot {
//תאוריה
    Servo hang;

    public hangingRobot(HardwareMap Map) {
        hang = Map.servo.get("hang");
        hang.setPosition(1);

    }

    public void activateHanging(){
        if (hang.getPosition()==1){
            hang.setPosition(0);
        }else {
            hang.setPosition(1);
        }
    }
}


