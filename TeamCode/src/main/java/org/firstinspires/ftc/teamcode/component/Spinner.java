package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Spinner {

    DcMotor spinner;

    private final double SPEED = 0.8;

    public void init(HardwareMap hardwareMap){
        spinner = hardwareMap.get(DcMotor.class, "spin");
    }

    public void spin(){
        spinner.setPower(SPEED);
    }

    public void reverse(){
        spinner.setPower(-SPEED);
    }

    public void stop(){
        spinner.setPower(0);
    }

}
