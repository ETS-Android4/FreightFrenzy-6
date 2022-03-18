package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {

    DcMotor arm;

    private final double POWER = 0.6;

    //motor ticks
    private final double GROUND = 0;
    private final double BOTTOM = 500;
    private final double MIDDLE = 1000;
    private final double TOP = 1500;

    public enum Position{
        GROUND,
        BOTTOM,
        MIDDLE,
        TOP
    }

    public void init(HardwareMap hardwareMap){

        arm = hardwareMap.get(DcMotor.class, "claw");
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public void moveArm(Position position){
        int multiplier = 1;
        switch(position){
            case TOP:
                if(arm.getCurrentPosition()>TOP){
                    multiplier = -1;
                }
                arm.setTargetPosition((int)TOP);
                break;
            case MIDDLE:
                break;
        }

        arm.setPower(multiplier * POWER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        while(arm.isBusy()){
//
//        }
//        arm.setPower(0);

    }

    public void moveUp(){
        arm.setTargetPosition(arm.getCurrentPosition()+40);
        arm.setPower(0.2);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean isBusy(){
        return arm.isBusy();
    }

    public void stopArm(){
        arm.setPower(0);
    }




}
