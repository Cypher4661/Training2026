// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.goToPosition;

public class MotorSubsystem extends SubsystemBase {
  private final SparkMax motor;
  
  /** Creates a new subsystems. */
  public MotorSubsystem() {
    super();
    motor = new SparkMax(Constants.MotorID,MotorType.kBrushless);
    SmartDashboard.putData("cmd", new goToPosition(90,this));
  }
 public void setPower(double power) {
  motor.set(power);
  }
  public double GetAngle() {
    return motor.getEncoder().getPosition()  * 360 / Constants.GearRatio;
  }
 

  public void stop() {
    setPower(0);
  }

  @Override
  public void periodic() {
      SmartDashboard.putNumber("angle", GetAngle());
  }
}
