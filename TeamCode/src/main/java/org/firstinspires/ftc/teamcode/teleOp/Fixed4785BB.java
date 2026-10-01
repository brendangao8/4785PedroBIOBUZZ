package org.firstinspires.ftc.teamcode.teleOp;


import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.robot.Robot;

@Configurable
@TeleOp(name= "fixed teleop", group = "Teleop")

public class Fixed4785BB extends LinearOpMode {

    public static double RPS = 30;

    public boolean last = true;

    public boolean isIntaking = false, isShooting = false;

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

            boolean openServo = false;
            boolean lastRightTriggerState = false;

            boolean currentRightTriggerState = gamepad1.right_trigger > 0.05;

            if (currentRightTriggerState == !lastRightTriggerState) {
                openServo = !openServo;
            }

            if (openServo){
                robot.balling.proceed();
                sleep(200);
                robot.balling.intake.setPower(1);
                isIntaking = true;
            }
            if (gamepad1.right_bumper) {
                robot.balling.stop();
                robot.balling.intake.setPower(1);
                isIntaking = true;
            }
            if (gamepad1.left_trigger > 0.05) {
                robot.balling.intake.setPower(-1);
                isIntaking = false;
                robot.balling.stop();
            }
            if (!gamepad1.right_bumper && !openServo && gamepad1.left_trigger < 0.05) {
                robot.balling.intake.setPower(0);
                isIntaking = false;
                robot.balling.stop();
            }


            telemetry.addData("Target RPS: ", RPS);
            telemetry.addData("Current RPS: ", robot.balling.getRPS());
            telemetry.update();
        }
    }
}
