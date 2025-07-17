// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    steerMotor.setInverted(Constants.MyFirstSubsystem.steerMotorIsInverted);
    driverMotor.setInverted(Constants.MyFirstSubsystem.driverMotorIsInverted);
    SmartDashboard.putData("modula", this);
  }
  public void setSteerPower(double power) {
    steerMotor.set(power);
  }
  public void setDriverPower(double power) {
    driverMotor.set(power);
  }

  public double getSteerPosition() {
    return steerMotor.getEncoder().getPosition() * Constants.MyFirstSubsystem.steerMotorGearRatio * 360;
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

  private double getAbsolutAngle() {
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
      builder.addDoubleProperty("absolute angle", this::getAbsolutAngle, null);
  }

  @Override
  public void periodic() {

  }
}
