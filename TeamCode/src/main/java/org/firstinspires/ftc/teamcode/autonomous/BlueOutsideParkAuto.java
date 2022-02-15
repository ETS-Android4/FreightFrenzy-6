package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Outside Park", group = "competition")
public class BlueOutsideParkAuto extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {

        super.startingPosition = StartingPosition.BLUE_OUTSIDE_PARK;
        super.runOpMode();
    }
}