package org.firstinspires.ftc.teamcode.teleOp;


import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.teamcode.robot.Robot;

@Configurable
@TeleOp(name= "main teleop", group = "Teleop")

public class fixed4785BB extends LinearOpMode {

    public static double RPS = 30;

    public Robot robot = new Robot();

    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

//            boolean currentButtonState = gamepad#.button;
//            if (currentButtonState && !lastButtonState) {
//                if (buttonCount % 2 == 0) {
//                    something
//                } else if (buttonCount % 2 == 1) {
//                    something else
//                }
//                buttonCount++;
//            }
//            lastButtonState = currentButtonState;


            telemetry.addData("Target RPS: ", RPS);
            telemetry.addData("Current RPS: ", robot.balling.getRPS());
            telemetry.update();
        }
    }
}
