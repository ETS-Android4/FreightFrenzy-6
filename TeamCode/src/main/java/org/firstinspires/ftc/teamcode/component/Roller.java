package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Roller {

    //roller
    private DcMotor rollerArm;
    private CRServo roller;

    //constants for roller position
    private final int RETRACTED = 0;
    private final int IN_POSITION = 1000;

    //power to move roller
    private final double POWER = 0.5;




    //tetrix: 1440 ticks per revolution
    //andymark: 1120 ticks per rev

    //init
    public void init(HardwareMap hardwareMap){
        rollerArm = hardwareMap.get(DcMotor.class, "rollerArm");
        rollerArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        roller = hardwareMap.get(CRServo.class, "roller");

        rollerArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    public void stopMover(){
        rollerArm.setPower(0);
    }

    public int getTicks(){
        return rollerArm.getCurrentPosition();
    }


    //move roller into positions
    public void moveToPosition(){
        rollerArm.setTargetPosition(IN_POSITION);
        rollerArm.setPower(POWER);
        rollerArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void retract(){
        rollerArm.setTargetPosition(RETRACTED);
        rollerArm.setPower(-POWER);
        rollerArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //mover is busy or not
    public boolean isBusy(){
        return rollerArm.isBusy();
    }


    //roller rolling
    public void roll(){
        roller.setPower(1);
    }

    //move roller up by small increments
    public void moveUp(){
        rollerArm.setTargetPosition(rollerArm.getCurrentPosition() + 40);
        rollerArm.setPower(0.45);
        rollerArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //move claw down by small increments
    public void moveDown(){
        rollerArm.setTargetPosition(rollerArm.getCurrentPosition() - 40);
        rollerArm.setPower(-0.45);
        rollerArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }






}

