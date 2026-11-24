//package org.firstinspires.ftc.teamcode.teleOp;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.math.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.seattlesolvers.solverslib.geometry.Translation2d;
//import com.seattlesolvers.solverslib.util.InterpLUT;
//import com.seattlesolvers.solverslib.util.LUT;
//
//import org.firstinspires.ftc.teamcode.pedro.Constants;
//import org.firstinspires.ftc.teamcode.robot.Robot;
//
//public class InterpolationExample extends LinearOpMode {
//
//    private Follower follower;
//    public static double RPS = 30;
//
//    public Robot robot = new Robot();
//
//    private final Pose startPose = new Pose(120, 120, Math.toRadians(90)); // Start Pose
//
//    public void runOpMode() {
//
//        follower = Constants.createFollower(hardwareMap);
//        robot.init(hardwareMap);
//        follower.setStartingPose(startPose);
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//
//
////            boolean currentButtonState = gamepad#.button;
////            if (currentButtonState && !lastButtonState) {
////                if (buttonCount % 2 == 0) {
////                    something
////                } else if (buttonCount % 2 == 1) {
////                    something else
////                }
////                buttonCount++;
////            }
////            lastButtonState = currentButtonState;
//
//            InterpLUT lut = new InterpLUT();
//
////Adding each val with a key
//            lut.add(1.1, 0.2);
//            lut.add(2.7, .5);
//            lut.add(3.6, 0.75);
//            lut.add(4.1, 0.9);
//            lut.add(5, 1);
////generating final equation
//            lut.createLUT();
//
//            double distance = localizer.getPose().getTranslation().getDistance(new Translation2d(5, 10));
//            RPS = lut.get(distance);
//
//            robot.balling.outtake(RPS);
//
//            follower.update();
//
//            telemetry.addData("Target RPS: ", RPS);
//            telemetry.addData("Current RPS: ", robot.balling.getRPS());
//            telemetry.update();
//        }
//    }
//}
