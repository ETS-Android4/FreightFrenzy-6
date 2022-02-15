package org.firstinspires.ftc.teamcode.component;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ObjectDetector extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();

    //locations
    public enum Location {
        LEFT,
        RIGHT,
        MID
    }

    double leftValue;
    double midValue;

    //location
    private Location location = Location.RIGHT;

    //Region of interest coordinates
    static final Rect MID_ROI = new Rect(
            new Point(5, 80),
            new Point(85, 150));
    static final Rect LEFT_ROI = new Rect(
            new Point(95, 80),
            new Point(175, 150));


    //Threshold to determine if there is an object
    final static double PERCENT_COLOR_THRESHOLD = 0.1;

    public ObjectDetector(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //Hue, Saturation, Value ranges
        Scalar lowHSV = new Scalar(12, 180, 130);//change color HSV
        Scalar highHSV = new Scalar(26, 255, 255);//change color HSV


        //creats submatrices
        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat mid = mat.submat(MID_ROI);
        Mat left = mat.submat(LEFT_ROI);

        //finds the raw value that fits in HSV range
        midValue = Core.sumElems(mid).val[0] / MID_ROI.area() / 255;
        leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;


        mid.release();
        left.release();

        //probably garbage telemetry
//        telemetry.addData("Middle raw value", (int) Core.sumElems(mid).val[0]);
////        telemetry.addData("Right raw value", (int) Core.sumElems(right).val[0]);
//        telemetry.addData("Right raw value", (int) Core.sumElems(left).val[0]);
//        telemetry.addData("Middle percentage", Math.round(midValue * 100) + "%");
////        telemetry.addData("Right percentage", Math.round(rightValue * 100) + "%");
//        telemetry.addData("Right percentage", Math.round(leftValue * 100) + "%");


        //determines if its above threshold
        boolean objectMid = midValue > PERCENT_COLOR_THRESHOLD;
        boolean objectLeft = leftValue > PERCENT_COLOR_THRESHOLD;

        //chooses location
        if (objectMid) {
            location = Location.MID;
        }else if(objectLeft){
            location = Location.LEFT;
        }else{
            location = Location.RIGHT;
        }
        telemetry.update();

        //stuff to make the gray scale appear on on robot phone
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar colorObject = new Scalar(255, 0, 0);
        Scalar colorNoObject = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, MID_ROI, location == Location.MID? colorNoObject:colorObject);
//        Imgproc.rectangle(mat, RIGHT_ROI, location == Location.RIGHT? c olorNoObject:colorObject);
        Imgproc.rectangle(mat, LEFT_ROI, location == Location.LEFT? colorNoObject:colorObject);
        //end of stuff
        return mat;
    }

    //some methods to get constants and vars
    public Location getLocation() {
        return location;
    }

    public String getLeft(){
        return Math.round(leftValue * 100) + "%";
    }

    public String getMid(){
        return Math.round(midValue * 100) + "%";
    }
}


