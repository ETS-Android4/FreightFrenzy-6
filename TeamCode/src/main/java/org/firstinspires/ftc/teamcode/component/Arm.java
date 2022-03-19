package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {

    DcMotor arm;

    private final double POWER = 0.6;

    public enum Position{
        GROUND(0),
        BOTTOM(500),
        MIDDLE(1000),
        TOP(1500);

        double ticks;
        Position(double ticks){
            this.ticks = ticks;
        }

        double getTicks(){
            return ticks;
        }
    }

    public void init(HardwareMap hardwareMap){

        arm = hardwareMap.get(DcMotor.class, "claw");
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public void moveArm(Position position){
        int multiplier = 1;
        if(arm.getCurrentPosition()>position.getTicks()){
            multiplier = -1;
        }
        arm.setTargetPosition((int)position.getTicks());

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
