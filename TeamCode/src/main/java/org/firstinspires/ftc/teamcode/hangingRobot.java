package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class hangingRobot {
//תאוריה
    Servo hang;

    public hangingRobot(HardwareMap Map) {
        hang = Map.servo.get("hang");
        hang.setPosition(1);

    }

    public void hang(){hang.setPosition(0);
    }
}
