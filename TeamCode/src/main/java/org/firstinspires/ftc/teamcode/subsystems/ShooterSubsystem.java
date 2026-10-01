package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private DcMotorEx shooter1, shooter2;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        shooter1 = hardwareMap.get(DcMotorEx.class, "shooter1");
        shooter2 = hardwareMap.get(DcMotorEx.class, "shooter2");
        shooter1.setVelocityPIDFCoefficients(50, 0, 0, 17.5);
        shooter2.setVelocityPIDFCoefficients(50, 0, 0, 17.5);

    }
    public void shoot() {
        shooter1.setVelocity(30.0 * 28.0);
        shooter2.setVelocity(30.0 * 28.0);
    }

    public void shootFar() {
        shooter1.setVelocity(65.0 * 28.0);
        shooter2.setVelocity(65.0 * 28.0);
    }
    public void stopShoot() {
        shooter1.setVelocity(0.0);
        shooter2.setVelocity(0.0);
    }
}