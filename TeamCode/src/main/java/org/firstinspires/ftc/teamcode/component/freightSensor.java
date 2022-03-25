package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class freightSensor {

    private DcMotor light;
    private DistanceSensor color;

    private double THRESHOLD = 2.5;

    public void init(HardwareMap hardwareMap){
        light = hardwareMap.get(DcMotor.class, "light");
        color = hardwareMap.get(DistanceSensor.class, "color");
    }

    public double sense(){
        return color.getDistance(DistanceUnit.CM);
    }

    public void success(){
        if (sense()<THRESHOLD){
            light.setPower(0.36);
        }
        else{
            light.setPower(0);
        }
    }
}