
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.PIDCommand;

import com.revrobotics.RelativeEncoder;

public class subsystem extends SubsystemBase {
  private final SparkMax motor1;
  private final RelativeEncoder encoder1;
  /** Creates a new subsystem. */
  public subsystem() {
    super();
    motor1 = new SparkMax(Constants.subsystem_Constatants.MotorId1, MotorType.kBrushless);
   //?? motor1.setInverted(Constants.subsystem_Constatants.MotorInverted1);
    encoder1 = motor1.getEncoder();
//    SmartDashboard.putData("cmd90", new RotateMotorCommand(this, 90, 0.015));
//    SmartDashboard.putData("cmd180", new RotateMotorCommand(this, 180, 0.015));
    SmartDashboard.putData("cmdX", new PIDCommand(this));

  }
 
  public void setPower(double power1) {
    motor1.set(power1);

  }

  public double getAngleDegrees1() {
    double motorRotations = encoder1.getPosition();
    return motorRotations * 360 / Constants.subsystem_Constatants.GearRatio;
  }

  
  public double getVelocity1() {
    return encoder1.getVelocity(); // RPM
  }

  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Motor1 Angle (°)", getAngleDegrees1());
    SmartDashboard.putNumber("Motor1 Velocity (RPM)", getVelocity1());
    SmartDashboard.putNumber("Motor1 power", motor1.getAppliedOutput());
    
    // כתיבה של ערך נוכחי (או ברירת מחדל) של זוית מבוקשת
  double targetAngle = SmartDashboard.getNumber("Target Angle", 90);
  SmartDashboard.putNumber("Target Angle", targetAngle);
  }

  public void stop() {
    setPower( 0);

  }
}
