package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Outside", group = "competition")
public class RedOutsideAuto extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {

        super.startingPosition = StartingPosition.RED_OUTSIDE;
        super.runOpMode();
    }
}