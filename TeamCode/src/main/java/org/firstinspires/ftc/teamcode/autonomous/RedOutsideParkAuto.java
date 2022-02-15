package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Outside Park", group = "competition")
public class RedOutsideParkAuto extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {

        super.startingPosition = StartingPosition.RED_OUTSIDE_PARK;
        super.runOpMode();
    }
}