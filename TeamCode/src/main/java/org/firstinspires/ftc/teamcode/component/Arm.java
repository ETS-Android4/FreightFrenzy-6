package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    //spinner
    private DcMotor arm;

    //constants for each shipping hub level
    private final int TOP = 3169 ;//3088
    private final int MID = 1920;//1392
    private final int BOTTOM = 840;//522
    private final int GROUND = 0;

    //constants for each shipping hub level
    private final int TOP_TELEOP = 3009 ;//3088
    private final int MID_TELEOP = 1897;//1392
    private final int BOTTOM_TELEOP = 781;//522



    //tetrix: 1440 ticks per revolution
    //andymark: 1120 ticks per rev

    //init
    public void init(HardwareMap hardwareMap){
        arm = hardwareMap.get(DcMotor.class, "claw");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    public void init(HardwareMap hardwareMap, boolean init2){
        arm = hardwareMap.get(DcMotor.class, "claw");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm.setDirection(DcMotorSimple.Direction.REVERSE);


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
        arm.setPower(multiplier * 0.92);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //garbage way to determine when to stop mover
//        while(mover.isBusy()){
//
//        }
//        stopMover();
    }

    //takes in input location
    public void moveArmTeleOp(TurnValue location){
        int multiplier = 1;//positive if the claw needs to go up, negative if it needs to go down


        if(location == TurnValue.BOTTOM){
            //determines multiplier based on current position
            if(arm.getCurrentPosition()>BOTTOM_TELEOP){
                multiplier = -1;
            }
            //sets target position
            arm.setTargetPosition(BOTTOM_TELEOP);

        }else if(location == TurnValue.MID){
            if(arm.getCurrentPosition()>MID_TELEOP){
                multiplier = -1;
            }
            arm.setTargetPosition(MID_TELEOP);
        }else if(location == TurnValue.TOP){
            if(arm.getCurrentPosition()>TOP_TELEOP){
                multiplier = -1;
            }
            arm.setTargetPosition(TOP_TELEOP);
        }else if(location == TurnValue.GROUND){
            if(arm.getCurrentPosition()>GROUND){
                multiplier = -1;
            }
            arm.setTargetPosition(GROUND);
        }

        //sets power and mode
        arm.setPower(multiplier * 0.92);
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
        arm.setPower(0.6);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //move claw down by small increments
    public void moveDown(){
        arm.setTargetPosition(arm.getCurrentPosition() - 40);
        arm.setPower(-0.6);
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

