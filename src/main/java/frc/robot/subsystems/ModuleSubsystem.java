// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ModuleSubsystem extends SubsystemBase {
  /** Creates a new FFsubsystem. */
  private final SparkMax DriveMoter;
  private final SparkMax SteerMoter;
  private final CANcoder canCoder;
  public ModuleSubsystem() {
    super();
    DriveMoter = new SparkMax(Constants.MyFirstSubsystem.DriveMoterId, MotorType.kBrushless);
    SteerMoter = new SparkMax(Constants.MyFirstSubsystem.SteerMOterId, MotorType.kBrushless);
    canCoder = new CANcoder(Constants.MyFirstSubsystem.CANcoderId);
    var cfg = new SparkMaxConfig();
    cfg.inverted(Constants.MyFirstSubsystem.SteerMoterInverted);
    SteerMoter.configure(cfg, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    DriveMoter.setInverted(Constants.MyFirstSubsystem.DriveMoterInverted);
    
    SmartDashboard.putData("modela", this);
    SmartDashboard.putData("set steer 0.3",new StartEndCommand(()->setSteerPower(0.3), ()->setSteerPower(0),this));
    SmartDashboard.putData("set steer 0.4",new StartEndCommand(()->setSteerPower(0.4), ()->setSteerPower(0),this));
    SmartDashboard.putData("set drive 0.3",new StartEndCommand(()->setDriverPower(0.3), ()->setDriverPower(0),this));
    SmartDashboard.putData("set drive 0.4",new StartEndCommand(()->setDriverPower(0.4), ()->setDriverPower(0),this));
    
  }
  public void setSteerPower(double power) {
    SteerMoter.set(power);
  }
  public void setDriverPower(double power) {
    DriveMoter.set(power);
  }

  public double getSteerPosition() {
    double angle =  SteerMoter.getEncoder().getPosition() / Constants.MyFirstSubsystem.SteerMoterRatio * 360;
    return MathUtil.inputModulus(angle, -180, 180);
    
  }
  public double getDriverPosition() {
    return DriveMoter.getEncoder().getPosition() * Constants.MyFirstSubsystem.DriveMoterRatio * 360;
  }

  public double getSteerVelocity() {
    return SteerMoter.getEncoder().getVelocity() * Constants.MyFirstSubsystem.SteerMoterRatio / 60;
  }
  public double getdriverVelocity() {
    return DriveMoter.getEncoder().getVelocity() * Constants.MyFirstSubsystem.DriveMoterRatio / 60;
  }

  public double getSteerPower() {
    return SteerMoter.getAppliedOutput();
  }
  public double getdriverPower() {
    return DriveMoter.getAppliedOutput();
  }
  public double getAbseloteAngele() {
    return canCoder.getAbsolutePosition().getValueAsDouble() * 360;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
      // TODO Auto-generated method stub
      super.initSendable(builder);
      builder.addDoubleProperty("Steer Position", this::getSteerPosition, null);
      builder.addDoubleProperty("Driver Position", this::getDriverPosition, null);
      builder.addDoubleProperty("Steer Velocity", this::getSteerVelocity, null);
      builder.addDoubleProperty("Driver Velocity", this::getdriverVelocity, null);
      builder.addDoubleProperty("Steer Power", this::getSteerPower, null);
      builder.addDoubleProperty("Driver Power", this::getdriverPower, null);
      builder.addDoubleProperty("Abselote Angle", this::getAbseloteAngele, null);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
