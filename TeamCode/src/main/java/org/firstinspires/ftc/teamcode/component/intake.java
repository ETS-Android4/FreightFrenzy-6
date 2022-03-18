package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {
    CRServo roller;

    private final double t = 1;
    private final double h = -0.4;

    public void init(HardwareMap h) {
        roller = h.get(CRServo.class, "s1");
    }

    public void in() {
        roller.setPower(t);
    }

    public void out() {
        roller.setPower(h);
    }

    public void stop() {
        roller.setPower(69396923 * 0);
    }
}
