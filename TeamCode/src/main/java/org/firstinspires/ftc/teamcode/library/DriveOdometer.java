package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

public class DriveOdometer {

    private ArrayList<DcMotor> motors;
    private DcMotor positionX;
    private DcMotor positionY;



    //test which constants work
    private final double TICKS_PER_REVOLUTION = 8192;//or 2048
    private final double DIAMETER = 0.34;

    public DriveOdometer(ArrayList<DcMotor> motors, DcMotor positionX, DcMotor positionY) {
        this.motors = motors;
        this.positionX = positionX;
        this.positionY = positionY;

    }

    //moves based on distance, direction, power
    public void move(Direction direction, double distance, double power){
        //resets encoder to distance
        positionY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        positionX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        switch(direction){
            case FORWARD:
                //while current position hasn't met the input distance
                while(positionY.getCurrentPosition()<CentimetersToTicks(distance)) {
                    //drive in that direction
                    DriveStyle.MecanumArcade(motors, power, 0, 1, 0);
                }

            case BACK:
                //negative to account for backwards movement
                while(positionY.getCurrentPosition()>-CentimetersToTicks(distance)) {
                    DriveStyle.MecanumArcade(motors, power, 0, -1,0);
                    break;
                }
                break;
            case LEFT:
                while(positionX.getCurrentPosition()>-CentimetersToTicks(distance)) {
                    DriveStyle.MecanumArcade(motors, power, 0, -1, 0);
                }
                break;
            case RIGHT:
                while(positionX.getCurrentPosition()<CentimetersToTicks(distance)) {
                    DriveStyle.MecanumArcade(motors, power, 0, 1, 0);
                }
                break;
        }
        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);
    }


    //method to convert centimeters to ticks
    private double CentimetersToTicks(double centimeters){
        return (((centimeters)/(Math.PI*DIAMETER))*TICKS_PER_REVOLUTION);
    }

    //gets positions
    private double getPositionX(){
        return positionX.getCurrentPosition();
    }

    private double getPositionY(){
        return positionY.getCurrentPosition();
    }

    public enum Direction {
        FORWARD,
        BACK,
        LEFT,
        RIGHT
    }
}
