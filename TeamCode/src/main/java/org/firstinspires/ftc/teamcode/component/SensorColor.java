package org.firstinspires.ftc.teamcode.component;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class SensorColor {

    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    private float hsvValues[] = {0F,0F,0F};

    private float lowerLimitBrick[] = {0F,0F,0F};
    private float upperLimitBrick[] = {0F,0F,0F};

    private float lowerLimitBall[] = {0F,0F,0F};
    private float upperLimitBall[] = {0F,0F,0F};

    public void init(HardwareMap hardwareMap) {
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        colorSensor.enableLed(true);

        distanceSensor = hardwareMap.get(DistanceSensor.class, "color");
    }

    public boolean matches(){
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        boolean matches = true;
        for(int i=0;i<3;i++){
            if((hsvValues[i]<lowerLimitBrick[i]||hsvValues[i]>upperLimitBrick[i])&&(hsvValues[i]<lowerLimitBall[i]||hsvValues[i]>upperLimitBall[i])){
                matches = false;
            }

        }
        return matches;
    }

    public float getHue(){
        return hsvValues[0];
    }

    public float getSaturation(){
        return hsvValues[1];
    }

    public float getValue(){
        return hsvValues[2];
    }

    public boolean intakeSuccessful(){
        return distanceSensor.getDistance(DistanceUnit.CM)<4;
    }

}
