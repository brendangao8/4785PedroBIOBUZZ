package org.firstinspires.ftc.teamcode.robot;

import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
public class Robot {
    public static double F = 0.0;
    public static double P = 0.01;
    public static double I = 0.0;
    public static double D = 0.0;
    public class DriveTrain {
        public DcMotorEx frontLeft, frontRight, backLeft, backRight;
        public void init(HardwareMap hardwareMap){
            frontLeft = hardwareMap.get(DcMotorEx.class,"leftFront");
            backLeft = hardwareMap.get(DcMotorEx.class,"leftBack");
            frontRight = hardwareMap.get(DcMotorEx.class,"rightFront");
            backRight = hardwareMap.get(DcMotorEx.class,"rightBack");

            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        public void driveTrainPower(double ly, double lx, double rx){
            double y = ly;
            double x = lx;
            double z = rx;
            double max;
            double frontLeftPower  = y + x + z;
            double frontRightPower = y - x - z;
            double backLeftPower   = y - x + z;
            double backRightPower  = y + x - z;
            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            max = Math.max(max, Math.abs(backLeftPower));
            max = Math.max(max, Math.abs(backRightPower));

            if (max > 1.0) {
                frontLeftPower  /= max;
                frontRightPower /= max;
                backLeftPower   /= max;
                backRightPower  /= max;
            }

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);
        }

    }
    public class Balling{
        public DcMotorEx intake1, shooter1, shooter2;
        public Servo stopper, protector1, protector2, light;
        public final int TICKS = 28;
        public double stopValue = 0, proceedValue = .4, protectValue = 0.0, collectValue = .50;
        public final double GREEN = .5, RED = .279;

        public void init(HardwareMap hardwareMap){
            intake1 = hardwareMap.get(DcMotorEx.class,"intake1");
            intake1.setDirection(DcMotorSimple.Direction.FORWARD);
            intake1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intake1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            shooter1 = hardwareMap.get(DcMotorEx.class,"shooter1");
            shooter1.setDirection(DcMotorSimple.Direction.FORWARD);
            shooter1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shooter1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooter1.setVelocityPIDFCoefficients(P,I,D,F);

            shooter2 = hardwareMap.get(DcMotorEx.class,"shooter2");
            shooter2.setDirection(DcMotorSimple.Direction.REVERSE);
            shooter2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooter2.setVelocityPIDFCoefficients(P,I,D,F);

            light = hardwareMap.get(Servo.class, "light");
            setColor(0);

            stopper = hardwareMap.get(Servo.class,"stopper");
            stopper.setPosition(0.73);

            protector1 = hardwareMap.get(Servo.class,"protector1");
            protector1.setPosition(0.0);
            protector1.setDirection(Servo.Direction.REVERSE);
            protector2 = hardwareMap.get(Servo.class,"protector2");
            protector2.setPosition(0.0);

        }

        public void setColor(double color) {
            light.setPosition(color);
        }
        public void stop () { stopper.setPosition(stopValue); }
        public void proceed () { stopper.setPosition(proceedValue);  }

        public void protect () {
            protector1.setPosition(protectValue);
            protector2.setPosition(protectValue);
        }

        public void liftCollect () {
            protector1.setPosition(collectValue);
            protector2.setPosition(collectValue);
        }

        public void stopIntake () {
            intake1.setPower(0);
        }

        public void intake () {
            intake1.setPower(1);
        }

        public void eject () {
            intake1.setPower(-1);
        }

        public void outtake(double RPS){
            shooter1.setVelocity(RPS * TICKS);
            shooter2.setVelocity(RPS * TICKS);
        }
        public void shooterBrake(){
            shooter1.setPower(0);
            shooter2.setPower(0);
            shooter1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            shooter2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
        public double getRPS(){
            return ((shooter1.getVelocity() + shooter2.getVelocity()) / TICKS) / 2;
        }
        public void setPValue(double value) { P = value; }

    }
    public class Vision{
        public Limelight3A limelight;

        public void init(HardwareMap hardwareMap){
            limelight = hardwareMap.get(Limelight3A.class,"limelight");
            limelight.setPollRateHz(90);
            limelight.pipelineSwitch(0);
            limelight.start();
        }
    }

    public class Voltage {
        public VoltageSensor voltageSensor;
        public double initialVoltage;
        public void init(HardwareMap hardwareMap) {
            voltageSensor = hardwareMap.voltageSensor.iterator().next();
            initialVoltage = voltageSensor.getVoltage();
        }

        public double findVoltage() {
            return voltageSensor.getVoltage();
        }
        public double getInitialVoltage() {
            return initialVoltage;
        }
    }

    public DriveTrain driveTrain = new DriveTrain();
    public Balling balling = new Balling();
    public Vision vision = new Vision();
    public Voltage voltageSensor = new Voltage();


    public void init (HardwareMap hardwareMap){
        driveTrain.init(hardwareMap);
        balling.init(hardwareMap);
        vision.init(hardwareMap);
        voltageSensor.init(hardwareMap);
    }
}

