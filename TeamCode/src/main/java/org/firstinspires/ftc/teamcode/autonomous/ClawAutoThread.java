package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.component.Arm;
import org.firstinspires.ftc.teamcode.component.ObjectDetector;
import org.firstinspires.ftc.teamcode.core.Moby;

public class ClawAutoThread extends Thread{

    private ObjectDetector.Location position;

    public ClawAutoThread(ObjectDetector.Location position){
        this.position = position;
    }

    @Override
    public void run(){
        switch (position) {
            case RIGHT:
                for(int i=0;i<1;i++){
                    Moby.arm.moveArm(Arm.TurnValue.TOP);
                    while(Moby.arm.isBusy()){

                    }
                    Moby.arm.stopArm();
                }




                break;
            case LEFT:
                for(int i=0;i<1;i++){
                    Moby.arm.moveArm(Arm.TurnValue.BOTTOM);
                    while(Moby.arm.isBusy()){

                    }
                    Moby.arm.stopArm();
                }


                break;
            case MID:
                for(int i=0;i<1;i++){
                    Moby.arm.moveArm(Arm.TurnValue.MID);
                    while(Moby.arm.isBusy()){

                    }
                    Moby.arm.stopArm();
                }

                break;
            case GROUND:
                for(int i=0;i<1;i++){
                    Moby.arm.moveArm(Arm.TurnValue.GROUND);
                    while(Moby.arm.isBusy()){

                    }
                    Moby.arm.stopArm();
                }

                break;

        }
    }



    public enum Level{
        TOP,
        MID,
        BOTTOM
    }
}
