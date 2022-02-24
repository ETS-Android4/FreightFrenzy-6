package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    //spinner
    private DcMotor arm;

    //constants for each shipping hub level
    private final int TOP = 2016;
    private final int MID = 1156;
    private final int BOTTOM = 402;
    private final int GROUND = 0;



    //tetrix: 1440 ticks per revolution
    //andymark: 1120 ticks per rev

    //init
    public void init(HardwareMap hardwareMap){
        arm = hardwareMap.get(DcMotor.class, "claw");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        mover.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    public void stopArm(){
        arm.setPower(0);
    }

    public int getTicks(){
        return arm.getCurrentPosition();
    }

    //takes in input location
    public void moveArm(TurnValue location){
        int multiplier = 1;//positive if the claw needs to go up, negative if it needs to go down


        if(location == TurnValue.BOTTOM){
            //determines multiplier based on current position
            if(arm.getCurrentPosition()>BOTTOM){
                multiplier = -1;
            }
            //sets target position
            arm.setTargetPosition(BOTTOM);

        }else if(location == TurnValue.MID){
            if(arm.getCurrentPosition()>MID){
                multiplier = -1;
            }
            arm.setTargetPosition(MID);
        }else if(location == TurnValue.TOP){
            if(arm.getCurrentPosition()>TOP){
                multiplier = -1;
            }
            arm.setTargetPosition(TOP);
        }else if(location == TurnValue.GROUND){
            if(arm.getCurrentPosition()>GROUND){
                multiplier = -1;
            }
            arm.setTargetPosition(GROUND);
        }

        //sets power and mode
        arm.setPower(multiplier * 0.6);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //garbage way to determine when to stop mover
//        while(mover.isBusy()){
//
//        }
//        stopMover();
    }

    //move claw up by small increments
    public void moveUp(){
        arm.setTargetPosition(arm.getCurrentPosition() + 40    );
        arm.setPower(0.45);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //move claw down by small increments
    public void moveDown(){
        arm.setTargetPosition(arm.getCurrentPosition() - 40);
        arm.setPower(-0.45);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //mover is busy or not
    public boolean isBusy(){
        return arm.isBusy();
    }



    public enum TurnValue {
        TOP,
        BOTTOM,
        MID,
        GROUND
    }




}

