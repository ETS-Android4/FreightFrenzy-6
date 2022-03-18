package org.firstinspires.ftc.teamcode.components;

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
    public enum Location {
        LEFT,
        RIGHT,
        MID
    }
    private Location location;

    //change ROI coordinates, see if its out of bounds
    static final Rect LEFT_ROI = new Rect(
            new Point(20, 35),
            new Point(80, 75));
    static final Rect RIGHT_ROI = new Rect(
            new Point(190, 35),
            new Point(250, 75));
    static final Rect MID_ROI = new Rect(
            new Point(105, 35),
            new Point(165, 75));

    //change Threshold
    static double PERCENT_COLOR_THRESHOLD = 0.4;

    public ObjectDetector(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //Keep in mind different ranges for H, S, and V
        Scalar lowHSV = new Scalar(23, 50, 70);//change color HSV
        Scalar highHSV = new Scalar(32, 255, 255);//change color HSV

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat right = mat.submat(RIGHT_ROI);
        Mat mid = mat.submat(MID_ROI);

        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;
        double midValue = Core.sumElems(mid).val[0] / MID_ROI.area() / 255;


        left.release();
        right.release();
        mid.release();

        telemetry.addData("Left raw value", (int) Core.sumElems(left).val[0]);
        telemetry.addData("Right raw value", (int) Core.sumElems(right).val[0]);
        telemetry.addData("Middle raw value", (int) Core.sumElems(mid).val[0]);
        telemetry.addData("Left percentage", Math.round(leftValue * 100) + "%");
        telemetry.addData("Right percentage", Math.round(rightValue * 100) + "%");
        telemetry.addData("Middle percentage", Math.round(midValue * 100) + "%");


        boolean objectLeft = leftValue > PERCENT_COLOR_THRESHOLD;
        boolean objectRight = rightValue > PERCENT_COLOR_THRESHOLD;
        boolean objectMid = midValue > PERCENT_COLOR_THRESHOLD;

        if (objectLeft) {
            location = Location.LEFT;
            telemetry.addData("Object Location", "left");
        }else if (objectRight){
            location = Location.RIGHT;
            telemetry.addData("Object Location", "right");
        }else if(objectMid){
            location = Location.MID;
            telemetry.addData("Object Location", "middle");
        }
        telemetry.update();

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar colorObject = new Scalar(255, 0, 0);
        Scalar colorNoObject = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LEFT_ROI, location == Location.LEFT? colorNoObject:colorObject);
        Imgproc.rectangle(mat, RIGHT_ROI, location == Location.RIGHT? colorNoObject:colorObject);
        Imgproc.rectangle(mat, MID_ROI, location == Location.MID? colorNoObject:colorObject);

        return mat;
    }

    public Location getLocation() {
        return location;
    }
}


