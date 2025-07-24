// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class module extends SubsystemBase {
  private final SparkMax motordrive;
  private final SparkMax motorsteer;
  private final CANcoder eNcoder; 
  /** Creates a new module. */
  public module() {
    super();
    motordrive = new SparkMax(Constants.firstSubSystem.MotorIDdrive, MotorType.kBrushless);
    motorsteer = new SparkMax(Constants.firstSubSystem.MotorID2steer,MotorType.kBrushless);
    eNcoder = new CANcoder(Constants.firstSubSystem.cancoderID);
  }
  public double getPOSITIONdriver(){
    return motordrive.getEncoder().getPosition()/Constants.firstSubSystem.GearRatio2driver*360;
    
  }
  public double getVelocitydrive(){
    return motordrive.getEncoder().getVelocity()/60;
  }
  public double getPOSITIONsteer(){
    return motorsteer.getEncoder().getPosition()/Constants.firstSubSystem.GearRatio2stear*360;
  }
  public double getVelocitysteer(){
    return motordrive.getEncoder().getVelocity()/60;
  }
  public void setPoWer(double powersteer, double powerdriver){
    motorsteer.set(powersteer);
    motordrive.set(powerdriver);
  }
  public void stop(){
    setPoWer(0, 0);
  }
  public double getPowerSteer(){
    return motorsteer.getAppliedOutput();
  }
  public double getPowerDrive(){
    return motordrive.getAppliedOutput();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("position drive", getPOSITIONdriver());
    SmartDashboard.putNumber("position steer", getPOSITIONsteer());
    SmartDashboard. putNumber("velocity drive", getVelocitydrive());
    SmartDashboard.putNumber("velocity steer", getVelocitysteer());
    // This method will be called once per scheduler run
  }

@Override
public void initSendable(SendableBuilder builder){
  super.initSendable(builder);
  builder.addBooleanArrayProperty("position steer", this::getPOSITIONsteer, null);
  builder.addBooleanArrayProperty("position drive", this::getPOSITIONdriver, null);
}