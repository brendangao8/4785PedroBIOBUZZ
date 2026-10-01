package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

public class StopShootCommand extends CommandBase {
    private final ShooterSubsystem shooter;
    public StopShootCommand(ShooterSubsystem shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.stopShoot();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}