package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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






        }


    }

}