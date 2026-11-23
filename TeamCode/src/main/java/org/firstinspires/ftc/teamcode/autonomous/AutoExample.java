package org.firstinspires.ftc.teamcode.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.commands.GoToPoseCommand;
import org.firstinspires.ftc.teamcode.commands.ShootCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.commands.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.RestrictCommand;
import org.firstinspires.ftc.teamcode.commands.ProceedCommand;
import org.firstinspires.ftc.teamcode.commands.StopShootCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.StopperSubsystem;


@Autonomous(name = "12 blue close", group = "auto")
public class AutoExample extends CommandOpMode {
    SequentialCommandGroup autonomousSequence;
    private Follower follower;
    private ShooterSubsystem shooterSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private StopperSubsystem stopperSubsystem;
//    private final Pose oppositeScorePose = new Pose(56, 132, Math.toRadians(-90)); // Start Pose
//    private final Pose collect1Pose = new Pose(5, 48, Math.toRadians(180)); // First Collect
//    private final Pose pickup2Pose = new Pose(56, 132, Math.toRadians(90)); // Third Set
//
//    private final Pose collect2Pose = new Pose(56, 139, Math.toRadians(90)); // Second Collect
//    private final Pose return3Pose = new Pose(10, 18, Math.toRadians(-90)); // Third Set
    private final Pose startPose = new Pose(56, 8, Math.toRadians(90)); // Start Pose
    private final Pose oppositeScorePose = new Pose(50, 132, Math.toRadians(-85)); // Start Pose
    private final Pose collect1Pose = new Pose(0, 8, Math.toRadians(180)); // First Collect
    private final Pose collect2Pose = new Pose(8, 47, Math.toRadians(180)); // Second Collect
    private final Pose pickup3Pose = new Pose(56, 132, Math.toRadians(-90)); // Third Set
    private final Pose collect3Pose = new Pose(56, 139, Math.toRadians(-90)); // Third Collect
    private final Pose collect4Pose = new Pose(8, 47, Math.toRadians(180)); // Fourth Collect
    private final Pose outOfStartPose = new Pose(56, 30, Math.toRadians(90)); // Escape

    @Override
    public void initialize() {
        follower = Constants.createFollower(hardwareMap);

        PathChain scorePreload, scoreCollect1, collectScore1, scoreCollect2, collectScore2,
                scorePickup3, pickupCollect3, collectScore3, scoreCollect4, collectScore4, escape;
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
        stopperSubsystem = new StopperSubsystem(hardwareMap);

        super.reset();

        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, startPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), startPose.getHeading())
                .build();

        scoreCollect1 = follower.pathBuilder()
                .addPath(new BezierLine(startPose, collect1Pose))
                .setLinearHeadingInterpolation(startPose.getHeading(), collect1Pose.getHeading())
                .build();

        collectScore1 = follower.pathBuilder()
                .addPath(new BezierLine(collect1Pose, startPose))
                .setLinearHeadingInterpolation(collect1Pose.getHeading(), startPose.getHeading())
                .build();

        scoreCollect2 = follower.pathBuilder()
                .addPath(new BezierLine(startPose, collect2Pose))
                .setLinearHeadingInterpolation(startPose.getHeading(), collect2Pose.getHeading())
                .build();

        collectScore2 = follower.pathBuilder()
                .addPath(new BezierLine(collect2Pose, startPose))
                .setLinearHeadingInterpolation(collect2Pose.getHeading(), startPose.getHeading())
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(startPose, pickup3Pose))
                .setLinearHeadingInterpolation(startPose.getHeading(), pickup3Pose.getHeading())
                .build();
        pickupCollect3 = follower.pathBuilder()
                .addPath(new BezierLine(pickup3Pose, collect3Pose))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), collect3Pose.getHeading())
                .build();
        collectScore3 = follower.pathBuilder()
                .addPath(new BezierLine(collect2Pose, startPose))
                .setLinearHeadingInterpolation(collect2Pose.getHeading(), startPose.getHeading())
                .build();
        scoreCollect4 = follower.pathBuilder()
                .addPath(new BezierLine(startPose, collect4Pose))
                .setLinearHeadingInterpolation(startPose.getHeading(), collect4Pose.getHeading())
                .build();
        collectScore4 = follower.pathBuilder()
                .addPath(new BezierLine(collect4Pose, startPose))
                .setLinearHeadingInterpolation(collect4Pose.getHeading(), startPose.getHeading())
                .build();
        escape = follower.pathBuilder()
                .addPath(new BezierLine(startPose, outOfStartPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), outOfStartPose.getHeading())
                .build();


        follower.setStartingPose(startPose);

        autonomousSequence = new SequentialCommandGroup(
                // score pre-load
                new ParallelCommandGroup(new ShootCommand(shooterSubsystem),
                        new GoToPoseCommand(follower, startPose)),
                new WaitCommand(500),
                new ProceedCommand(stopperSubsystem),
                new IntakeCommand(intakeSubsystem),
                new WaitCommand(500),
                new StopShootCommand(shooterSubsystem),

                // collect first batch
                new ParallelCommandGroup(new FollowPathCommand(follower, scoreCollect1),
                        new IntakeCommand(intakeSubsystem), new RestrictCommand(stopperSubsystem)),
                new WaitCommand(750),

                new StopIntakeCommand(intakeSubsystem),
                new ShootCommand(shooterSubsystem),

                // score first batch
                new FollowPathCommand(follower, collectScore1),
                new ProceedCommand(stopperSubsystem),
                new IntakeCommand(intakeSubsystem),
                new WaitCommand(750),
                new StopShootCommand(shooterSubsystem),

                // collect second batch
                new ParallelCommandGroup(new FollowPathCommand(follower, scoreCollect2),
                        new IntakeCommand(intakeSubsystem)),
                new WaitCommand(100),
                new StopIntakeCommand(intakeSubsystem),
                new ShootCommand(shooterSubsystem),

                // score second batch
                new FollowPathCommand(follower, collectScore2),
                new ProceedCommand(stopperSubsystem),
                new IntakeCommand(intakeSubsystem),
                new WaitCommand(750),
                new StopShootCommand(shooterSubsystem),

                // collect third batch
                new SequentialCommandGroup(new FollowPathCommand(follower, scorePickup3),
                        new RestrictCommand(stopperSubsystem)),
                new WaitCommand(100),
                new ParallelCommandGroup(new FollowPathCommand(follower, pickupCollect3),
                        new IntakeCommand(intakeSubsystem)),
                new WaitCommand(1000),
                new StopIntakeCommand(intakeSubsystem),
                new ShootCommand(shooterSubsystem),

                // score third batch
                new FollowPathCommand(follower, collectScore3),
                new ProceedCommand(stopperSubsystem),
                new IntakeCommand(intakeSubsystem),
                new WaitCommand(750),
                new StopShootCommand(shooterSubsystem),

                new FollowPathCommand(follower, escape),
                new WaitCommand(10000)
        );
    }

    @Override
    public void run() {
        super.run();
        schedule(autonomousSequence);
        follower.update();
    }
}
