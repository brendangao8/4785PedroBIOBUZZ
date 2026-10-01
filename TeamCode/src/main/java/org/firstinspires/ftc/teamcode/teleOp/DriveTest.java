package org.firstinspires.ftc.teamcode.teleOp;

import com.bylazar.configurables.annotations.Configurable;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Configurable
@TeleOp(name="driveTest", group = "Teleop")
public class DriveTest extends LinearOpMode {
    double x, y, z;
    Robot robot = new Robot();
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            y = -gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            z = gamepad1.right_stick_x;
            robot.driveTrain.driveTrainPower(y, x, z);
        }

    }
}
