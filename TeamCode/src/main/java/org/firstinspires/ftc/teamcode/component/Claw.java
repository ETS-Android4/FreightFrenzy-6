package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    //spinner
    private DcMotor mover;
    private Servo gripper;

    //constants for each shipping hub level
    private final int TOP = 2016;
    private final int MID = 1156;
    private final int BOTTOM = 402;
    private final int GROUND = 0;

    //constants for claw servo
    private final double CLOSE = 0.3;
    private final double OPEN = 0;



    //tetrix: 1440 ticks per revolution
    //andymark: 1120 ticks per rev

    //init
    public void init(HardwareMap hardwareMap){
        mover = hardwareMap.get(DcMotor.class, "claw");
        mover.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        gripper = hardwareMap.get(Servo.class, "s1");

        gripper.setPosition(CLOSE);
//        mover.setDirection(DcMotorSimple.Direction.REVERSE);
        mover.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    public void stopMover(){
        mover.setPower(0);
    }

    public int getTicks(){
        return mover.getCurrentPosition();
    }

    //takes in input location
    public void moveMover(TurnValue location){
        int multiplier = 1;//positive if the claw needs to go up, negative if it needs to go down


        if(location == TurnValue.BOTTOM){
            //determines multiplier based on current position
            if(mover.getCurrentPosition()>BOTTOM){
                multiplier = -1;
            }
            //sets target position
            mover.setTargetPosition(BOTTOM);

        }else if(location == TurnValue.MID){
            if(mover.getCurrentPosition()>MID){
                multiplier = -1;
            }
            mover.setTargetPosition(MID);
        }else if(location == TurnValue.TOP){
            if(mover.getCurrentPosition()>TOP){
                multiplier = -1;
            }
            mover.setTargetPosition(TOP);
        }else if(location == TurnValue.GROUND){
            if(mover.getCurrentPosition()>GROUND){
                multiplier = -1;
            }
            mover.setTargetPosition(GROUND);
        }

        //sets power and mode
        mover.setPower(multiplier * 0.6);
        mover.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //garbage way to determine when to stop mover
//        while(mover.isBusy()){
//
//        }
//        stopMover();
    }

    //move claw up by small increments
    public void moveUp(){
        mover.setTargetPosition(mover.getCurrentPosition() + 40    );
        mover.setPower(0.45);
        mover.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //move claw down by small increments
    public void moveDown(){
        mover.setTargetPosition(mover.getCurrentPosition() - 40);
        mover.setPower(-0.45);
        mover.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //mover is busy or not
    public boolean isBusy(){
        return mover.isBusy();
    }


    //open and close claw
    public void closeClaw(){
        gripper.setPosition(CLOSE);
    }

    public void openClaw(){
        gripper.setPosition(OPEN);

    }

    public enum TurnValue {
        TOP,
        BOTTOM,
        MID,
        GROUND
    }




}
