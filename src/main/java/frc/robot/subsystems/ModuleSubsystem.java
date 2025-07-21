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
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ModuleSubsystem extends SubsystemBase {
  private final SparkMax  steerMotor;
  private final SparkMax  driverMotor;
  private final CANcoder CANcoder;


  public ModuleSubsystem() {
    super();
    steerMotor = new SparkMax(Constants.MyFirstSubsystem.steerMotorId, MotorType.kBrushless);
    driverMotor = new SparkMax(Constants.MyFirstSubsystem.driverMotorId, MotorType.kBrushless);
    CANcoder = new CANcoder(Constants.MyFirstSubsystem.CANcoderId);
    var cfg = new SparkMaxConfig();
    cfg.inverted(Constants.MyFirstSubsystem.steerMotorIsInverted);
    steerMotor.configure(cfg, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    var cfg2 = new SparkMaxConfig();
    cfg2.inverted(Constants.MyFirstSubsystem.driverMotorIsInverted);
    driverMotor.configure(cfg2, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    SmartDashboard.putData("modula", this);
    SmartDashboard.putData("set steer 0.3",new StartEndCommand(()->setSteerPower(0.3), ()->setSteerPower(0),this));
    SmartDashboard.putData("set steer 0.4",new StartEndCommand(()->setSteerPower(0.4), ()->setSteerPower(0),this));
    SmartDashboard.putData("set drive 0.3",new StartEndCommand(()->setDriverPower(0.3), ()->setDriverPower(0),this));
    SmartDashboard.putData("set drive 0.4",new StartEndCommand(()->setDriverPower(0.4), ()->setDriverPower(0),this));
  }
  public void setSteerPower(double power) {
    steerMotor.set(power);
  }
  public void setDriverPower(double power) {
    driverMotor.set(power);
  }
  
  public double getSteerPosition() {
    double angle = steerMotor.getEncoder().getPosition() / Constants.MyFirstSubsystem.steerMotorGearRatio * 360;
    return MathUtil.inputModulus(angle, -180, 180);

  }
  public double getDriverPosition() {
    return driverMotor.getEncoder().getPosition() * Constants.MyFirstSubsystem.driverMotorGearRatio * 360;
  }

  public double getSteerVelocity() {
    return steerMotor.getEncoder().getVelocity() * Constants.MyFirstSubsystem.steerMotorGearRatio * 6;
  }
  public double getdriverVelocity() {
    return driverMotor.getEncoder().getVelocity() * Constants.MyFirstSubsystem.driverMotorGearRatio * 6;
  }

  public double getSteerPower() {
    return steerMotor.getAppliedOutput();
  }
  public double getdriverPower() {
    return driverMotor.getAppliedOutput();
  }

  private double getAbsoluteAngle() {
    return CANcoder.getAbsolutePosition().getValueAsDouble() * 360; // Assuming CANcoder returns a value between 0 and 1
  }



  @Override
  public void initSendable(SendableBuilder builder) {
      // TODO Auto-generated method stub
      super.initSendable(builder);
      builder.addDoubleProperty("steer position", this::getSteerPosition, null);
      builder.addDoubleProperty("driver position", this::getDriverPosition, null);
      builder.addDoubleProperty("steer velocity", this::getSteerVelocity, null);
      builder.addDoubleProperty("driver velocity", this::getdriverVelocity, null);
      builder.addDoubleProperty("steer power", this::getSteerPower, null);
      builder.addDoubleProperty("driver power", this::getdriverPower, null);
      builder.addDoubleProperty("absolute angle", this::getAbsoluteAngle, null);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Steer Position", getSteerPosition());
    SmartDashboard.putNumber("Driver Position", getDriverPosition());
    SmartDashboard.putNumber("Steer Velocity", getSteerVelocity());
    SmartDashboard.putNumber("Driver Velocity", getdriverVelocity());
    SmartDashboard.putNumber("Steer Power", getSteerPower());
    SmartDashboard.putNumber("Driver Power", getdriverPower());
    SmartDashboard.putNumber("Absolute Angle", getAbsoluteAngle());
  }
}
