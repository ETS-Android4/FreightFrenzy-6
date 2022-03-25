package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    CRServo intake;

    final double IN = 1;
    final double OUT = -0.4;

    public void init(HardwareMap hardwareMap){
        intake = hardwareMap.get(CRServo.class, "s1");
    }

    public void in(){
        intake.setPower(IN);
    }

    public void out(){
        intake.setPower(OUT);
    }

    public void stop(){
        intake.setPower(0);
    }

}