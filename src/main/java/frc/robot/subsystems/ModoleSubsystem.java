package frc.robot.subsystems;


import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ModoleSubsystem extends SubsystemBase {
    private final SparkMax steerMotor;
    private final SparkMax driveMotor;
    private final CANcoder canCoder;

    private final SimpleMotorFeedforward sFeedforward = new SimpleMotorFeedforward(0.0075,0.000625);
    private final PIDController sPIDController = new PIDController(0.1, 0, 0.01);
    private final SimpleMotorFeedforward dFeedforward = new SimpleMotorFeedforward(1.0/150.0,2.0/9.0);
    private final PIDController dPIDController = new PIDController(0.1, 0, 0.01);

    public ModoleSubsystem() {
        super();
        steerMotor = new SparkMax(Constants.steerMotorConstants.steerMotorID, MotorType.kBrushless);
        driveMotor = new SparkMax(Constants.driveMotorConstants.driveMotorID, MotorType.kBrushless);
        canCoder = new CANcoder(Constants.MyFirstSubsystemConstants.CANcoderId);
        SmartDashboard.putData("Modole", this);
        var cfg = new SparkMaxConfig();
        cfg.inverted(Constants.steerMotorConstants.steerMotorInverted);
        steerMotor.configure(cfg, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        var cfg2 = new SparkMaxConfig(); 
        cfg2.inverted(Constants.driveMotorConstants.driveMotorInverted);
        driveMotor.configure(cfg2, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        calibrateSteer();
        addCommands();
        SmartDashboard.putData("modula", this);
    }
        // Initialization code can be added here if needed.
    private void addCommands() {
        SmartDashboard.putData("modela", this);
        SmartDashboard.putData("set steer 0.3",new StartEndCommand(()->setSteerMotorPower(0.3), ()->setSteerMotorPower(0),this));
        SmartDashboard.putData("set steer 0.4",new StartEndCommand(()->setSteerMotorPower(0.4), ()->setSteerMotorPower(0),this));
        SmartDashboard.putData("set steer 0.5",new StartEndCommand(()->setSteerMotorPower(0.5), ()->setSteerMotorPower(0),this));
        SmartDashboard.putData("set drive 0.3",new StartEndCommand(()->setSteerMotorPower(0.3), ()->setSteerMotorPower(0),this));
        SmartDashboard.putData("set drive 0.4",new StartEndCommand(()->setSteerMotorPower(0.4), ()->setSteerMotorPower(0),this));
        SmartDashboard.putData("set drive 0.5",new StartEndCommand(()->setSteerMotorPower(0.5), ()->setSteerMotorPower(0),this));
            
        SmartDashboard.putNumber("Steer Velocity Target", 180);
        SmartDashboard.putData("Set Steer Velocity", new RunCommand(
            () -> setSteerMotorVelocity(SmartDashboard.getNumber("Steer Velocity Target", 0)),
            this));
        SmartDashboard.putNumber("Driver Velocity Target", 1.0);
        SmartDashboard.putData("Set Driver Velocity", new RunCommand(
            () -> setDriveMotorVelocity(SmartDashboard.getNumber("Driver Velocity Target", 0)),
            this
            ));
        SmartDashboard.putData("stop steer", new StartEndCommand(
            () -> setSteerMotorPower(0),
            () -> setSteerMotorPower(0),
            this
            ));
        SmartDashboard.putData("stop driver", new StartEndCommand(
            () -> setSteerMotorPower(0),
            () -> setSteerMotorPower(0),
            this
            ));
        }
          private void calibrateSteer() {
        // Calibrate the steer motor to the absolute position of the CANcoder
        double absoluteAngle = getAbseloteAngele() - Constants.MyFirstSubsystemConstants.CANofset;
        steerMotor.getEncoder().setPosition(absoluteAngle * Constants.steerMotorConstants.steerMotorGearRatio / 360);
        }
        public void setSteerMotorPower(double power) {
            steerMotor.set(power);
        }
        public void setDriveMotorPower(double power) {
            driveMotor.set(power);
        }
        public double getSteerPower() {
            return steerMotor.getAppliedOutput();
        }
        public double getDriverPower() {
            return driveMotor.getAppliedOutput();
        }
        public double getSteerMotorPosition() {
            double Angle = steerMotor.getEncoder().getPosition() / Constants.steerMotorConstants.steerMotorGearRatio * 360;
            return MathUtil.inputModulus(Angle, -180, 180);
        }
        public double getDriveMotorPosition() {
            return driveMotor.getEncoder().getPosition() / Constants.driveMotorConstants.driveMotorGearRatio * 360;
        }
        public void stopSteerMotor() {
            setSteerMotorPower(0);
        }
        public void stopDriveMotor() {
            setDriveMotorPower(0);
        }
        public double getSteerMotorVelocity() {
            return steerMotor.getEncoder().getVelocity() * Constants.steerMotorConstants.steerMotorGearRatio * 360 / 60;
        }
        public double getdriveMotorVelocity() {
            return driveMotor.getEncoder().getVelocity() * Constants.driveMotorConstants.driveMotorGearRatio / 60 * Math.PI*Constants.MyFirstSubsystemConstants.diameter;
        }
        public void setSteerMotorVelocity(double velocity) {
            double ff = sFeedforward.calculate(velocity);
            double pid = sPIDController.calculate(getSteerMotorPosition(), velocity);
            setSteerMotorPower(ff + pid);
        }
        public void setDriveMotorVelocity(double velocity) {
            double ff = dFeedforward.calculate(velocity);
            double pid = dPIDController.calculate(getDriveMotorPosition(), velocity);
            setDriveMotorPower(ff + pid);
        }
        public double getAbseloteAngele() {
            return canCoder.getAbsolutePosition().getValueAsDouble() * 360;
        }
        public void setEncoder(double Angle) {
            steerMotor.getEncoder().setPosition(Angle);
        }
        @Override
        public void periodic() {
        // This method can be overridden to add periodic tasks for this subsystem.

        }
        @Override
        public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
            super.initSendable(builder);
            builder.addDoubleProperty("Steer Motor Position", this::getSteerMotorPosition, null);
            builder.addDoubleProperty("Drive Motor Position", this::getDriveMotorPosition, null);
            builder.addDoubleProperty("Steer Motor Velocity", this::getSteerMotorVelocity, null);
            builder.addDoubleProperty("Drive Motor Velocity", this::getdriveMotorVelocity, null);
            builder.addDoubleProperty("Steer Motor Power", this::getSteerPower, null);
            builder.addDoubleProperty("Drive Motor Power", this::getDriverPower, null);
            builder.addDoubleProperty("Abselote Angle", this::getAbseloteAngele, null);
            
    }
}
