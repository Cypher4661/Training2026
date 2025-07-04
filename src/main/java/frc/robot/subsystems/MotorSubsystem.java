// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MotorSubsystem extends SubsystemBase {
  private final SparkMax motor;
  
  /** Creates a new subsystems. */
  @SuppressWarnings("removal")
  public MotorSubsystem() {
    super();
    motor = new SparkMax(Constants.SubsystemsConstants.MotorID,MotorType.kBrushless);
    motor.setInverted(Constants.SubsystemsConstants.MotorInverted);
  }
 public void setPower(double power) {
  motor.set(power);
  }
 

  public void stop() {
    setPower(0);
  }
}
