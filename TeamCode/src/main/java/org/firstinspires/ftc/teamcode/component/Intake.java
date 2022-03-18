package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private CRServo spinner;




    //tetrix: 1440 ticks per revolution
    //andymark: 1120 ticks per rev

    //init
    public void init(HardwareMap hardwareMap){

        spinner = hardwareMap.get(CRServo.class, "s1");



    }



    //open and close claw
    public void in(){
        spinner.setPower(1);
    }

    public void out(){
        spinner.setPower(-1);

    }

    public void stopSpinner(){
        spinner.setPower(0);
    }






}
