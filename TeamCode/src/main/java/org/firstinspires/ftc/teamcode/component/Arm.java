package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    DcMotor arm;

    public void init(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotor.class, "claw");
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public enum Position{
        GROUND(0),
        BOTTOM(500),
        MIDDLE(1000),
        TOP(1500);


        int ticks;
        Position(int t){
            ticks = t;
        }

        public int getTicks() {
            return ticks;
        }

    }

    public void moveArm(Position position){
        if (position.getTicks() < arm.getCurrentPosition()) {
            arm.setTargetPosition(position.getTicks());
            arm.setPower(-0.8);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else {
            arm.setTargetPosition(position.getTicks());
            arm.setPower(0.8);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

    }

    public void moveUp(){
        arm.setTargetPosition(arm.getCurrentPosition()+40);
        arm.setPower(0.2);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveDown(){
        arm.setTargetPosition(arm.getCurrentPosition()-40);
        arm.setPower(0.2);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void stopArm(){
        arm.setPower(0);
    }

    public boolean isBusy(){
        return arm.isBusy();
    }

    public int getPosition(){
        return arm.getCurrentPosition();
    }
}

























