package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.DriveStyle;

@TeleOp(name = "Tank", group = "competition")
public class Tank extends TeleOpParent {

    @Override
    public void runOpMode() throws InterruptedException {
        super.type = DriveStyle.DriveType.TANK;
        super.slow = 1;
        super.runOpMode();
    }
}