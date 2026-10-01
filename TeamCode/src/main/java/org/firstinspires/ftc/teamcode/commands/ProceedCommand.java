package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.StopperSubsystem;

public class ProceedCommand extends CommandBase {
    private final StopperSubsystem stopper;
    public ProceedCommand(StopperSubsystem stopper) {
        this.stopper = stopper;
        addRequirements(stopper);

    }

    @Override
    public void initialize() {
        stopper.proceed();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}