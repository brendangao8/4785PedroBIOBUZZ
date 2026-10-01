package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.StopperSubsystem;

public class RestrictCommand extends CommandBase {
    private final StopperSubsystem stopper;
    public RestrictCommand(StopperSubsystem stopper) {
        this.stopper = stopper;
        addRequirements(stopper);

    }

    @Override
    public void initialize() {
        stopper.stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}