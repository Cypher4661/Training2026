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
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ModuleSubsystem extends SubsystemBase {
  /** Creates a new FFsubsystem. */
  private final SparkMax DriveMoter;
  private final SparkMax SteerMoter;
  private final CANcoder canCoder;

  private final SimpleMotorFeedforward steerFF = new SimpleMotorFeedforward(0.0075,0.000625 );
  private final PIDController steerPID = new PIDController(0.001, 0, 0);
  private final SimpleMotorFeedforward driverFF = new SimpleMotorFeedforward(1.0/150.0,2.0/9.0);
  private final PIDController driverPID = new PIDController(0.001, 0, 0);



  public ModuleSubsystem() {
    super();
    //create motors and canCoder
    DriveMoter = new SparkMax(Constants.modela1.DriveMoterId, MotorType.kBrushless);
    SteerMoter = new SparkMax(Constants.modela1.SteerMOterId, MotorType.kBrushless);
    canCoder = new CANcoder(Constants.modela1.CANcoderId);
    //configure steer and drive motors
    var cfg = new SparkMaxConfig();
    cfg.inverted(Constants.modela1.SteerMoterInverted);
    SteerMoter.configure(cfg, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    var cfg2 = new SparkMaxConfig();
    cfg.inverted(Constants.modela1.DriveMoterInverted);
    DriveMoter.configure(cfg, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    
    calibrateSteer();
    addCommands();

  }

  private void addCommands() {
    //create commands for testing 
    SmartDashboard.putData("modela", this);
    SmartDashboard.putData("set steer 0.3",new StartEndCommand(()->setSteerPower(0.3), ()->setSteerPower(0),this));
    SmartDashboard.putData("set steer 0.4",new StartEndCommand(()->setSteerPower(0.4), ()->setSteerPower(0),this));
    SmartDashboard.putData("set steer 0.5",new StartEndCommand(()->setSteerPower(0.5), ()->setSteerPower(0),this));
    SmartDashboard.putData("set drive 0.3",new StartEndCommand(()->setDriverPower(0.3), ()->setDriverPower(0),this));
    SmartDashboard.putData("set drive 0.4",new StartEndCommand(()->setDriverPower(0.4), ()->setDriverPower(0),this));
    SmartDashboard.putData("set drive 0.5",new StartEndCommand(()->setDriverPower(0.5), ()->setDriverPower(0),this));
    //create commands for setting velocity
    SmartDashboard.putNumber("Steer Velocity Target", 180);
    SmartDashboard.putData("Set Steer Velocity", new RunCommand(
      () -> setSteerVelocity(SmartDashboard.getNumber("Steer Velocity Target", 0)),
      
      this));
    SmartDashboard.putNumber("Driver Velocity Target", 1.0);
    SmartDashboard.putData("Set Driver Velocity", new RunCommand(
      () -> setDriverVelocity(SmartDashboard.getNumber("Driver Velocity Target", 0)),
      this
    ));
    //create commands for stopping motors
    SmartDashboard.putData("stop steer", new RunCommand(
      () -> setSteerPower(0),
      this
      ));
    SmartDashboard.putData("stop driver", new RunCommand(
      () -> setDriverPower(0),
      this
    
    ));
    SmartDashboard.putNumber("Target steer angle", 0);
    SmartDashboard.putData("Set Steer Angle", new RunCommand(
      () -> setSteerAngle(SmartDashboard.getNumber("Target steer angle", 0)),
      this
    ));

  }
  public void setSteerAngle(double angle) {
    // Set the steer motor to the desired angle
    
    double error = MathUtil.inputModulus(angle,-180, 180) - getSteerPosition();
    if(error > 180) {
      error -= 360;
    } else if(error < -180) {
      error += 360;
    }
    double velocity = error * 1.1; // Proportional control for angle
    if (Math.abs(velocity) < 4.0) {
      velocity = 0; // Stop if the error is small
    }
    setSteerVelocity(velocity);


  }
  public void setSteerPower(double power) {
    SteerMoter.set(power);
  }
  public void setDriverPower(double power) {
    DriveMoter.set(power);
  }
  private void calibrateSteer() {
    double absolotAngel = getAbseloteAngele() - Constants.modela1.CANcoderOffset;
    SteerMoter.getEncoder().setPosition(absolotAngel * Constants.modela1.SteerMoterRatio / 360);
  }
  public double getSteerPosition() {
    double angle =  SteerMoter.getEncoder().getPosition() / Constants.modela1.SteerMoterRatio * 360;
    return MathUtil.inputModulus(angle, -180, 180);
    
  }
  public double getDriverPosition() {
    return DriveMoter.getEncoder().getPosition() / Constants.modela1.DriveMoterRatio * 360;
  }

  public double getSteerVelocity() {
    return SteerMoter.getEncoder().getVelocity() / Constants.modela1.SteerMoterRatio *360 / 60;
  }
  public double getdriverVelocity() {
    return DriveMoter.getEncoder().getVelocity() / Constants.modela1.DriveMoterRatio / 60 * Math.PI * Constants.modela1.diameter; // Convert to radians per second
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
