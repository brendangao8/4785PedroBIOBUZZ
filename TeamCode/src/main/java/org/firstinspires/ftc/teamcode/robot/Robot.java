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
        public DcMotorEx intake, shooter1, shooter2;
        public Servo stopper, light;
        public final int TICKS = 28;
        public double stopValue = 0, proceedValue = .4;
        public final double GREEN = .5, RED = .279;

        public void init(HardwareMap hardwareMap){
            intake = hardwareMap.get(DcMotorEx.class,"intake");
            intake.setDirection(DcMotorSimple.Direction.FORWARD);
            intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            shooter1 = hardwareMap.get(DcMotorEx.class,"shooter");
            shooter1.setDirection(DcMotorSimple.Direction.FORWARD);
            shooter1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shooter1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooter1.setVelocityPIDFCoefficients(P,I,D,F);

            shooter2 = hardwareMap.get(DcMotorEx.class,"shooter");
            shooter2.setDirection(DcMotorSimple.Direction.FORWARD);
            shooter2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooter2.setVelocityPIDFCoefficients(P,I,D,F);

            light = hardwareMap.get(Servo.class, "light");
            setColor(0);

            stopper = hardwareMap.get(Servo.class,"stopper");
            stopper.setPosition(0.73);
        }

        public void setColor(double color) {
            light.setPosition(color);
        }
        public void stop () { stopper.setPosition(stopValue); }
        public void proceed () { stopper.setPosition(proceedValue);  }

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

