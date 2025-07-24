package frc.newRobot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.newRobot.Constants;
import frc.newRobot.commands.PIDCommand;

import com.revrobotics.RelativeEncoder;

public class DriveSubsystem extends SubsystemBase {

    private final CANSparkMax driveMotor = new CANSparkMax(Constants.DriveMotor.ID, MotorType.kBrushless);
    private final RelativeEncoder driveEncoder = driveMotor.getEncoder();
    private final PIDController pid = new PIDController(Constants.DriveMotor.kP, Constants.DriveMotor.kI, Constants.DriveMotor.kD);
    private final SimpleMotorFeedforward ff = new SimpleMotorFeedforward(Constants.DriveMotor.kS, Constants.DriveMotor.kV, Constants.DriveMotor.kA);

    public DriveSubsystem() {
        driveMotor.restoreFactoryDefaults();
        driveMotor.setInverted(Constants.DriveMotor.INVERTED);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Drive/Velocity", getVelocity());
        SmartDashboard.putNumber("Drive/PID Error", pid.getPositionError());
        SmartDashboard.putNumber("Drive/Motor Output (%)", driveMotor.getAppliedOutput());
    }

    public void setVelocity(double targetVelocity) {
        double ffOutput = ff.calculate(targetVelocity);
        double pidOutput = pid.calculate(getVelocity(), targetVelocity);
        driveMotor.setVoltage(ffOutput + pidOutput);
    }

    public double getVelocity() {
        return driveEncoder.getVelocity();
    }

    public void stop() {
        driveMotor.stopMotor();
    }
}
