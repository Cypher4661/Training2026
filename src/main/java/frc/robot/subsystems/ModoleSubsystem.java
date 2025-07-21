package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ModoleSubsystem extends SubsystemBase {
    private final SparkMax steerMotor;
    private final SparkMax driveMotor;
  
  public ModoleSubsystem() {
    super();
    steerMotor = new SparkMax(Constants.steerMotorConstants.steerMotorID, MotorType.kBrushless);
    driveMotor = new SparkMax(Constants.driveMotorConstants.driveMotorID, MotorType.kBrushless);
    SmartDashboard.putData("Modole", this);
    // Initialization code can be added here if needed.
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
      public double getdriverPower() {
        return driveMotor.getAppliedOutput();
    }
    public double getSteerMotorPosition() {
        return steerMotor.getEncoder().getPosition() * Constants.steerMotorConstants.steerMotorGearRatio * 360;
    }
    public double getDriveMotorPosition() {
        return driveMotor.getEncoder().getPosition() * Constants.driveMotorConstants.driveMotorGearRatio * 360;
    }
    public void stopSteerMotor() {
        setSteerMotorPower(0);
    }
    public void stopDriveMotor() {
        setDriveMotorPower(0);
    }
    public void stopMotors() {
        stopSteerMotor();
        stopDriveMotor();
    }
    public double getSteerMotorVelocity() {
        return steerMotor.getEncoder().getVelocity() * Constants.steerMotorConstants.steerMotorGearRatio * 60;
    }
    public double getdriveMotorVelocity() {
        return driveMotor.getEncoder().getVelocity() * Constants.driveMotorConstants.driveMotorGearRatio * 60;
    }
    public void setSteerMotorVelocity(double velocity) {
    }
    public void setDriveMotorVelocity(double velocity) {
    }
    public double getAbseloteAngele() {
        return canCoder.getAbsolutePosition().getValueAsDouble() * 360;

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
        builder.addDoubleProperty("Drive Motor Power", this::getdriverPower, null);
        builder.addDoubleProperty("Abselote Angle", this::getAbseloteAngele, null);
    }
}
