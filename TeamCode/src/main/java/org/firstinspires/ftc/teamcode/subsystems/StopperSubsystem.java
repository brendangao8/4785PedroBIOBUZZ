package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
public class StopperSubsystem extends SubsystemBase {
    private Servo stopper;
    private final double stop = .73, proceed = .45;

    public StopperSubsystem(HardwareMap hardwareMap) {
        stopper = hardwareMap.get(Servo.class, "stopper");
        stopper.setPosition(stop);
    }
    public void stop () {
        stopper.setPosition(stop);
    }
    public void proceed() {
        stopper.setPosition(proceed);
    }
}
