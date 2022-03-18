package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import static org.firstinspires.ftc.teamcode.core.Moby.hardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class FreightSensor {
    private DistanceSensor fSense;
    public DcMotor light;

    public void init(){
        fSense = hardwareMap.get(DistanceSensor.class, "color");
        light = hardwareMap.get(DcMotor.class, "light");
    }

    public boolean hasFreight() {
        return fSense.getDistance(DistanceUnit.CM) <= 2.5;
    }
}
