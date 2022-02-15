package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.component.Claw;
import org.firstinspires.ftc.teamcode.component.ObjectDetector;
import org.firstinspires.ftc.teamcode.core.Moby;
import org.firstinspires.ftc.teamcode.library.DriveAuto;
import org.firstinspires.ftc.teamcode.library.DriveSensor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


public class  AutonomousParent extends LinearOpMode {

    ObjectDetector.Location position;

    StartingPosition startingPosition = StartingPosition.RED_OUTSIDE;

    //constants for power
    final double POWER = 0.7;
    final double STRAIGHTEN_POWER = 0.3;
    final double LOW_POWER = 0.5;
    double distance = 0;


    //drivetrains
    private DriveAuto drivetrain = new DriveAuto(Moby.driveMotors);
    private DriveSensor drivetrain2 = new DriveSensor(Moby.driveMotors);

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





        dropPreLoad();


        spinAndPark();
    }

    //drops shipping element at the correct spot
    public void dropPreLoad(){
        switch(startingPosition){
            case RED_INSIDE:
                //alignment
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.2, 0.36);
                sleep(250);
                drivetrain2.straighten(0);
                sleep(250);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 94, 0.6);
                sleep(500);
                moveGripperToPos();
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                sleep(500);
                drivetrain2.setHeading(1, -0.2, 83);
                sleep(500);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, LOW_POWER, 0.35);

                //drops pre-load
                sleep(1000);
                Moby.claw.openClaw();
                sleep(1000);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, LOW_POWER, 0.45);
                sleep(500);
                Moby.claw.moveMover(Claw.TurnValue.MID);
                Moby.claw.closeClaw();
                break;
            case BLUE_INSIDE:
                //alignment
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.3, 0.36);
                sleep(250);
                drivetrain2.straighten(0);
                sleep(250);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 94, 0.6);
                sleep(500);
                moveGripperToPos();
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                sleep(500);
                drivetrain2.setHeading(1, 0.2, -83);
                sleep(500);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, LOW_POWER, 0.35);
                sleep(1000);
                //drops pre-load
                Moby.claw.openClaw();
                sleep(1000);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, LOW_POWER, 0.45);
                sleep(500);
                Moby.claw.moveMover(Claw.TurnValue.MID);
                Moby.claw.closeClaw();
                break;
            case BLUE_OUTSIDE_PARK:
            case BLUE_OUTSIDE:
                //for blue outside only it will spin before dropping
                //goes to spinner
                drivetrain2.straighten(0, 0.5);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 33.5, 0.3);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.3);
                drivetrain2.straighten(0, 0.5);
                sleep(500);
                //spins
                Moby.spinner.spin();
                sleep(2000);
                Moby.spinner.stop();
                drivetrain2.straighten(0, 0.3);

                //aligns with shipping hub
                if(position== ObjectDetector.Location.RIGHT){
                    drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 78, LOW_POWER);
                }else{
                    drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 40, LOW_POWER);
                }
                sleep(500);
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                sleep(200);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 96, 0.6);
                sleep(500);
                moveGripperToPos();
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                sleep(500);
                drivetrain2.setHeading(1, -0.2, 87);
                sleep(500);
//                distance = 0;
//                if(position== ObjectDetector.Location.LEFT){
//                    distance = 98;
//                }else if(position== ObjectDetector.Location.MID){
//                    distance = 100;
//                }else{
//                    distance = 95;
//                }
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 98, 0.2);
                sleep(1000);
                //drops pre-load
                Moby.claw.openClaw();
                sleep(1000);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 90, LOW_POWER);
                sleep(500);
                moveGripperBack();
                Moby.claw.closeClaw();
                break;
            case RED_OUTSIDE_PARK:
            case RED_OUTSIDE:

                //moves to avoid shipping element
                if(position== ObjectDetector.Location.MID){
                    drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, 0.5);
                }else if (position== ObjectDetector.Location.LEFT){
                    drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 74, 0.3);
                }
                //aligns with shipping hub
                drivetrain2.straighten(0, 0.3);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 95, 0.6);
                sleep(500);
                moveGripperToPos();
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                sleep(500);
                drivetrain2.setHeading(1, 0.2, -85);
                sleep(500);
//                distance = 0;
//                if(position== ObjectDetector.Location.RIGHT){
//                    distance = 105;
//                }else if(position== ObjectDetector.Location.MID){
//                    distance = 100;
//                }else{
//                    distance = 95;
//                }
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 98, 0.2);
                sleep(1000);
                //drops pre-load
                Moby.claw.openClaw();
                sleep(1000);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 90, LOW_POWER);
                sleep(500);
                moveGripperBack();
                Moby.claw.closeClaw();

                break;

        }




    }

    public void spinAndPark(){
        switch(startingPosition){
            case RED_INSIDE:
                //parks in warehouse
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.5);
                sleep(250);
                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.2, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.2, 90);
                }
                sleep(250);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 80, 0.5);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 65, 0.3);
                moveGripperBack();
//                drivetrain2.setHeading(1, -0.2, -90);
//                drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.4, 0.6);
                break;
            case BLUE_INSIDE:
                //parks in warehouse
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.5);
                sleep(250);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.2, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.2, -90);
                }
                sleep(250);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 80, 0.5);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 65, 0.3);
                moveGripperBack();
//                drivetrain2.setHeading(1, 0.2, 90);
//                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.4, 0.6);
                break;
            case RED_OUTSIDE:
                //aligns with carousel
                drivetrain2.setHeading(1, -0.2, -90);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 15, LOW_POWER);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.2, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.2, -90);
                }
                sleep(500);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 45, 0.3);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.3);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.2, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.2, -90);
                }
                sleep(500);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 8, 0.3);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 31.2, 0.3);

                //spins carousel
                sleep(500);
                Moby.spinner.reverse();
                sleep(2000);
                Moby.spinner.stop();
                //parks in storage unit
                sleep(250);
                sleep(250);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.2, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.2, -90);
                }
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 72, 0.5);
                break;
            case BLUE_OUTSIDE:
                //parks in storage unit
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.3, 0.3);
                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.2, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.2, 90);
                }
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 25, LOW_POWER);
                sleep(250);

                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.2, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.2, 90);
                }
                sleep(250);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 86, 0.5);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 10, LOW_POWER);

                break;
            case RED_OUTSIDE_PARK:
                //aligns with carousel
                drivetrain2.setHeading(1, -0.2, -90);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 15, LOW_POWER);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.2, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.2, -90);
                }
                sleep(500);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 45, 0.3);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.3);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.2, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.2, -90);
                }
                sleep(500);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 5, 0.3);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 30.7, 0.3);

                //spins carousel
                Moby.spinner.reverse();
                sleep(2000);
                Moby.spinner.stop();
                //parks in warehouse
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 42, 0.5);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.1, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.1, -90);
                }

                //
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 50, 0.5);
//                if(Moby.imu.getHeading()>-90){
//                    drivetrain2.setHeading(1, 0.2, -90);
//                }else if(Moby.imu.getHeading()<-90) {
//                    drivetrain2.setHeading(1, -0.2, -90);
//                }
//                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 12, 0.5);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.2, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.2, -90);
                }
//                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 140, 0.5);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD,0.5, 1.8);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.1, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.1, -90);
                }
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.5);
                if(Moby.imu.getHeading()>-90){
                    drivetrain2.setHeading(1, 0.1, -90);
                }else if(Moby.imu.getHeading()<-90) {
                    drivetrain2.setHeading(1, -0.1, -90);
                }
                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 70, 0.5);
                break;
            case BLUE_OUTSIDE_PARK:
                //parks in warehouse
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.3, 0.3);
                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.2, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.2, 90);
                }
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 10, LOW_POWER);
                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.2, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.2, 90);
                }
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 85, 0.5);



                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 45, 0.5);
                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.2, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.2, 90);
                }
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 50, 0.5);
//                if(Moby.imu.getHeading()>90){
//                    drivetrain2.setHeading(1, 0.2, 90);
//                }else if(Moby.imu.getHeading()<90) {
//                    drivetrain2.setHeading(1, -0.2, 90);
//                }
//                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 12, 0.5);
                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.2, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.2, 90);
                }
//                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 140, 0.5);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD,0.5, 1.8);
                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.2, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.2, 90);
                }
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.5);
                if(Moby.imu.getHeading()>90){
                    drivetrain2.setHeading(1, 0.1, 90);
                }else if(Moby.imu.getHeading()<90) {
                    drivetrain2.setHeading(1, -0.1, 90);
                }
                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 75 , 0.5);
                break;

        }




    }

    //moves gripper to correct position based on computer vision input
    public void moveGripperToPos (){
        switch (position) {
            case RIGHT:
                    for(int i=0;i<3;i++){
                        Moby.claw.moveMover(Claw.TurnValue.TOP);
                        while(Moby.claw.isBusy()){

                        }
                        Moby.claw.stopMover();
                    }


                for(int i=0;i<1;i++){
                    Moby.claw.moveUp();
                    while(Moby.claw.isBusy()){

                    }
                    Moby.claw.stopMover();
                }

                break;
            case LEFT:
                    for(int i=0;i<3;i++){
                        Moby.claw.moveMover(Claw.TurnValue.BOTTOM);
                        while(Moby.claw.isBusy()){

                        }
                        Moby.claw.stopMover();
                    }

                for(int i=0;i<1;i++){
                    Moby.claw.moveUp();
                    while(Moby.claw.isBusy()){

                    }
                    Moby.claw.stopMover();
                }
                break;
            case MID:
                for(int i=0;i<3;i++){
                    Moby.claw.moveMover(Claw.TurnValue.MID);
                    while(Moby.claw.isBusy()){

                    }
                    Moby.claw.stopMover();
                }
                break;
        }
    }

    //moves gripper to original position
    public void moveGripperBack(){
        Moby.claw.moveMover(Claw.TurnValue.GROUND);
        while(Moby.claw.isBusy()){

        }
        Moby.claw.stopMover();
    }


    //old method used during first scrimmage
    //garbage auto 10 pts
    public void park(){
        switch(startingPosition){
            case RED_INSIDE:
//                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.1, 0.1);
                //                drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.1, 0.1);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.7, 1.0);
                drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.5, 0.75);
                break;
            case BLUE_INSIDE:
//                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.1, 0.1);
                //                drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.1, 0.1);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.7, 1.0);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.5, 0.75);
                break;
            case RED_OUTSIDE:
                //                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.1, 0.1);
                //                drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.1, 0.1);
                sleep(25000);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.7, 2);
                drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.5, 0.75);
            case BLUE_OUTSIDE:
//                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.1, 0.1);
                //                drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.1, 0.1);
                sleep(25000);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.7, 2);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.5, 0.75);
                break;
        }
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



