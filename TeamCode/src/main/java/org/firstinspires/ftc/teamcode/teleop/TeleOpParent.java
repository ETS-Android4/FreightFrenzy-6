package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.component.Arm;
import org.firstinspires.ftc.teamcode.core.*;
import org.firstinspires.ftc.teamcode.library.DriveSensor;
import org.firstinspires.ftc.teamcode.library.DriveStyle;

public class  TeleOpParent extends LinearOpMode {



    // Set default DriveType
    DriveStyle.DriveType type = DriveStyle.DriveType.MECANUMARCADE;



    double slow = 0.6;
    private DriveSensor drivetrain2 = new DriveSensor(Moby.driveMotors);

    public long millis = 0;


    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Initialize the robot hardware
        Moby.init(hardwareMap);

        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();



        waitForStart();

        while (opModeIsActive()) {

            // "Gear" the drivetrain when the winches are up
            /*
            if (desiredPosition > 0.1) {
                gamepad1.left_stick_x /= 2; gamepad1.left_stick_y /= 2; gamepad1.right_stick_x /= 2;
            }
            */

            // Drivie using set drivemode (g1.ls/rs, g1.lb/rb)
            DriveStyle.driveWithType(Moby.driveMotors, gamepad2, type, slow);

            //spinner stuff
            if (gamepad1.dpad_up|| gamepad2.dpad_up) {
                Moby.spinner.spin();
            }else if(gamepad1.dpad_down || gamepad2.dpad_down) {
                Moby.spinner.reverse();
            }else {
                Moby.spinner.stop();
            }



            if(Moby.colorSensor.intakeSuccessful()){
                Moby.light.setPower(0.15);
            }else{
                Moby.light.setPower(0);
            }



//            Moby.light.setPower(0.15 );



//
            telemetry.addData("Distance", Moby.colorSensor.getDistance());
//            telemetry.addData("Red", Moby.colorSensor.getRed());
//            telemetry.addData("Green", Moby.colorSensor.getBlue());
//            telemetry.addData("Blue", Moby.colorSensor.getGreen());
            telemetry.update();

            //to switch between slow mode, normal mode, and fast mode
            if(gamepad2.left_stick_button){
                slow = 0.67;
            }

            if(gamepad2.right_stick_button){
                slow = 0.15;
            }

            if(gamepad2.dpad_right){
                slow = 1;
            }


            //garbage if statements
//            if(gamepad2.dpad_left){
//                drivetrain2.straighten(90, 0.3);
//                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.6);
//            }
//
//            if(gamepad2.dpad_right){
//                drivetrain2.straighten(90, 0.3);
//                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 10, 0.6);
//            }


            //changes claw position on controller input
            if(gamepad1.a||gamepad2.a){
                Moby.arm.moveArmTeleOp(Arm.TurnValue.GROUND);
            }

            if(gamepad1.x||gamepad2.x){
                Moby.arm.moveArmTeleOp(Arm.TurnValue.BOTTOM);
            }

            if(gamepad1.y||gamepad2.y){
                Moby.arm.moveArmTeleOp(Arm.TurnValue.MID);
            }

            if(gamepad1.b||gamepad2.b){
                Moby.arm.moveArmTeleOp(Arm.TurnValue.TOP);
            }


            //makes small increments or decrements to claw position
            if(gamepad1.right_trigger >= 0.1 || gamepad2.right_trigger >= 0.1) {
                Moby.arm.moveUp();
            }
            if(gamepad1.left_trigger >= 0.1 || gamepad2.left_trigger >= 0.1) {
                Moby.arm.moveDown();
            }

            if(gamepad1.dpad_left||gamepad2.dpad_left) {
                Moby.intake.out();
            }else if(gamepad1.dpad_right||gamepad2.dpad_right){
                Moby.intake.in();
            }else{
                Moby.intake.stopSpinner();
            }


            if(gamepad1.left_bumper||gamepad2.left_bumper){
                Moby.intake.out(1);
            }

            if(gamepad1.right_bumper||gamepad2.right_bumper){
                Moby.intake.out(1);
            }

//            telemetry.addData("Position", Moby.intake.getPosition());
//            telemetry.update();



            //to stop mover once it has reached its target position
            if(!Moby.arm.isBusy()){
                    Moby.arm.stopArm();
            }




        }


    }

}