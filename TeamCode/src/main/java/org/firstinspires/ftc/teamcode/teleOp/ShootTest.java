package org.firstinspires.ftc.teamcode.teleOp;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.robot.Robot;

// http://192.168.43.1:8001/
// 192.168.43.1:8080/dash
@TeleOp(name="shooting test", group = "teleop")
@Configurable
public class ShootTest extends LinearOpMode {
    //set number
    Robot robot = new Robot();
    public static double RPS = 30.0;
    double tick = 28.0;
    public boolean lastLeftBumperState = false, lastRightBumperState = false;

    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            boolean currentLeftBumperState = gamepad1.left_bumper;
            if (currentLeftBumperState && !lastLeftBumperState) {
                RPS -= .25;
            }
            lastLeftBumperState = currentLeftBumperState;

            boolean currentRightBumperState = gamepad1.right_bumper;
            if (currentRightBumperState && !lastRightBumperState) {
                RPS += .25;
            }
            lastRightBumperState = currentRightBumperState;

            robot.balling.shooter.setVelocity(RPS * tick);

            robot.balling.intake.setPower(1);

            robot.balling.proceed();
            telemetry.addData("Target RPS: ", RPS);
            telemetry.addData("Current RPS: ", robot.balling.getRPS());

            telemetry.update();
        }
    }
}