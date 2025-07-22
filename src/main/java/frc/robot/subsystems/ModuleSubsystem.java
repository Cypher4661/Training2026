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
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ModuleSubsystem extends SubsystemBase {
  private final SparkMax  steerMotor;
  private final SparkMax  driverMotor;
  private final CANcoder CANcoder;
  private final SimpleMotorFeedforward steerFF = new SimpleMotorFeedforward(0.0075, 0.000625);
  private final SimpleMotorFeedforward driverFF = new SimpleMotorFeedforward(1.0/150, 2.0/9);
  private final PIDController steerPID = new PIDController(0.001, 0, 0);
  private final PIDController driverPID = new PIDController(0.001, 0, 0);
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
    calibrateSteer();
    addCommands();
    SmartDashboard.putData("modula", this);
  }
  private void addCommands() {
    SmartDashboard.putData("modela", this);
    SmartDashboard.putData("set steer 0.3",new StartEndCommand(()->setSteerPower(0.3), ()->setSteerPower(0),this));
    SmartDashboard.putData("set steer 0.4",new StartEndCommand(()->setSteerPower(0.4), ()->setSteerPower(0),this));
    SmartDashboard.putData("set steer 0.5",new StartEndCommand(()->setSteerPower(0.5), ()->setSteerPower(0),this));
    SmartDashboard.putData("set drive 0.3",new StartEndCommand(()->setDriverPower(0.3), ()->setDriverPower(0),this));
    SmartDashboard.putData("set drive 0.4",new StartEndCommand(()->setDriverPower(0.4), ()->setDriverPower(0),this));
    SmartDashboard.putData("set drive 0.5",new StartEndCommand(()->setDriverPower(0.5), ()->setDriverPower(0),this));
    
    SmartDashboard.putNumber("Steer Velocity Target", 180);
    SmartDashboard.putData("Set Steer Velocity", new RunCommand(
      () -> setSteerVelocity(SmartDashboard.getNumber("Steer Velocity Target", 0)),
      this));
    SmartDashboard.putNumber("Driver Velocity Target", 1.0);
    SmartDashboard.putData("Set Driver Velocity", new RunCommand(
      () -> setDriverVelocity(SmartDashboard.getNumber("Driver Velocity Target", 0)),
      this
    ));
    SmartDashboard.putData("stop steer", new StartEndCommand(
      () -> setSteerPower(0),
      () -> setSteerPower(0),
      this
      ));
    SmartDashboard.putData("stop driver", new StartEndCommand(
      () -> setDriverPower(0),
      () -> setDriverPower(0),
      this
      ));
  }
  public void setSteerPower(double power) {
    steerMotor.set(power);
  }
  public void setDriverPower(double power) {
    driverMotor.set(power);
  }

  private void calibrateSteer() {
    // Calibrate the steer motor to the absolute position of the CANcoder
    double absoluteAngle = getAbsoluteAngle() - Constants.MyFirstSubsystem.CANcoderOffSet;
    steerMotor.getEncoder().setPosition(absoluteAngle * Constants.MyFirstSubsystem.steerMotorGearRatio / 360);
  }
  
  public double getSteerPosition() {
    double angle = steerMotor.getEncoder().getPosition() / Constants.MyFirstSubsystem.steerMotorGearRatio * 360;
    return MathUtil.inputModulus(angle, -180, 180);

  }
  public double getDriverPosition() {
    return driverMotor.getEncoder().getPosition() / Constants.MyFirstSubsystem.driverMotorGearRatio * 360;
  }

  public double getSteerVelocity() {
    return steerMotor.getEncoder().getVelocity() / Constants.MyFirstSubsystem.steerMotorGearRatio * 6;
  }
  public double getdriverVelocity() {
    return driverMotor.getEncoder().getVelocity() / Constants.MyFirstSubsystem.driverMotorGearRatio / 60 * Math.PI * Constants.MyFirstSubsystem.diameter;
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

  public void setSteerVelocity(double velocity) {
    double ff = steerFF.calculate(velocity);
    double pid = steerPID.calculate(getSteerVelocity(), velocity);
    setSteerPower(ff + pid);
    
  }
  public void setDriverVelocity(double velocity) {
    double ff = driverFF.calculate(velocity);
    double pid = driverPID.calculate(getdriverVelocity(), velocity);
    setDriverPower(ff + pid);
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

  }
}
