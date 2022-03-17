package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.component.ObjectDetector;
import org.firstinspires.ftc.teamcode.core.Moby;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


public class  AutonomousParent extends LinearOpMode {

    ObjectDetector.Location position;

    StartingPosition startingPosition = StartingPosition.RED_OUTSIDE;



    OpenCvCamera phoneCam;
    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Moby.init(hardwareMap);


        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        //object detection computer vision stuff
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance()
                .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        ObjectDetector detector = new ObjectDetector(telemetry);
        phoneCam.setPipeline(detector);
        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });


        //telemetry of the vision data
        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("Starting Position", startingPosition);
            position = detector.getLocation();
            telemetry.addData("Last Position: ", position);
            telemetry.addData("Left percent: ", detector.getLeft());
            telemetry.addData("Mid Percent: ", detector.getMid());
            telemetry.update();
        }




        phoneCam.stopStreaming();








    }


    public enum StartingPosition{
        RED_OUTSIDE,
        RED_INSIDE,
        BLUE_INSIDE,
        BLUE_OUTSIDE,
        RED_OUTSIDE_PARK,
        BLUE_OUTSIDE_PARK
    }
}



