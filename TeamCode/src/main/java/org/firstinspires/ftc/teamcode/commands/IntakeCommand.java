package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {
    private final IntakeSubsystem intake;
    public IntakeCommand(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(intake);

    }

    @Override
    public void initialize() {
        intake.collect();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}