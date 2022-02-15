package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Inside", group = "competition")
public class RedInsideAuto extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {

        super.startingPosition = StartingPosition.RED_INSIDE;
        super.runOpMode();
    }
}