// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.goToAngle;
import frc.robot.commands.goToPosition;

public class MotorSubsystem extends SubsystemBase {
  private final SparkMax motor;
  
  /** Creates a new subsystems. */
  public MotorSubsystem() {
    super();
    motor = new SparkMax(Constants.MyFirstSubsystemConstants.MotorID,MotorType.kBrushless);
    SmartDashboard.putData("cmd", new goToAngle(this));
  }
 public void setPower(double power) {
  motor.set(power);
  }
  public double getPosition() {
    return motor.getEncoder().getPosition()  * Constants.MyFirstSubsystemConstants.GearRatio *360;
  }
 

  public void stop() {
    setPower(0);
  }

  @Override
  public void periodic() {
      SmartDashboard.putNumber("motor Position", getPosition());
      SmartDashboard.putNumber("motor power", motor.getAppliedOutput());
  }
}
