package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.ArmFeedforward;

public class newSubsystem extends SubsystemBase {

    private final SparkMax driveMotor = new SparkMax(Constants.DriveMotor.ID, MotorType.kBrushless);
    private final RelativeEncoder driveEncoder = driveMotor.getEncoder();
    private final PIDController drivePid = new PIDController(Constants.DriveMotor.kP, Constants.DriveMotor.kI, Constants.DriveMotor.kD);
    private final SimpleMotorFeedforward driveFf = new SimpleMotorFeedforward(Constants.DriveMotor.kS, Constants.DriveMotor.kV, Constants.DriveMotor.kA);

    private final SparkMax steerMotor = new SparkMax(Constants.steerMotor.ID, MotorType.kBrushless);
    private final RelativeEncoder steerEncoder = steerMotor.getEncoder();
    private final PIDController steerPid = new PIDController(Constants.steerMotor.kP, Constants.steerMotor.kI, Constants.steerMotor.kD);
    private final ArmFeedforward steerFf = new ArmFeedforward(Constants.steerMotor.kS, Constants.steerMotor.kG, Constants.steerMotor.kV, Constants.steerMotor.kA);

    private final CANcoder cancoder = new CANcoder(Constants.cancoderId);
    public newSubsystem() {
        SparkMaxConfig cfg = new SparkMaxConfig();
        cfg.inverted(Constants.DriveMotor.INVERTED);
        driveMotor.configure(cfg, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        steerMotor.configure(cfg, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        SmartDashboard.putData("Set Drive to 0.3",
            new StartEndCommand(()->setDrivePower(0.3), ()->setDrivePower(0),this));
        SmartDashboard.putData("Set steer to 0.3",
            new StartEndCommand(()->setSteerPower(0.3), ()->setSteerPower(0),this));

        SmartDashboard.putData("Set Drive to 0.5",
            new StartEndCommand(()->setDrivePower(0.5), ()->setDrivePower(0),this));
        SmartDashboard.putData("Set steer to 0.5",
            new StartEndCommand(()->setSteerPower(0.5), ()->setSteerPower(0),this));
    }

    public void setDrivePower(double power) {
        driveMotor.set(power);
    }
    public void setSteerPower(double power) {
        driveMotor.set(power);
    }
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Drive/Velocity", getVelocity());
        SmartDashboard.putNumber("Drive/PID Error", drivePid.getPositionError());
        SmartDashboard.putNumber("Drive/Motor Output (%)", driveMotor.getAppliedOutput());

        SmartDashboard.putNumber("Angle/Position", getPosition());
        SmartDashboard.putNumber("Angle/PID Error", steerPid.getPositionError());
        SmartDashboard.putNumber("Angle/Motor Output (%)", steerMotor.getAppliedOutput());
    }

    public void setVelocity(double targetVelocity) {
        double ffOutput = driveFf.calculate(targetVelocity);
        double pidOutput = drivePid.calculate(getVelocity(), targetVelocity);
        driveMotor.setVoltage(ffOutput + pidOutput);
    }

    public void setAngle(double targetAngle) {
        double ffOutput = steerFf.calculate(Math.toRadians(targetAngle), 0);
        double pidOutput = steerPid.calculate(getPosition(), targetAngle);
        steerMotor.setVoltage(ffOutput + pidOutput);

    }
    public double getVelocity() {
        return driveEncoder.getVelocity();
    }
    public double getPosition() {
        return steerEncoder.getPosition(); // צריך לוודא שהתרגום נכון לזווית
    }
    public void stop() {
        driveMotor.stopMotor();
        steerMotor.stopMotor();
    }
}

