// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.ed

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.pidToAngle;

public class firstSubSystem extends SubsystemBase {
  private final SparkMax motor;

  private PIDController turnController = new PIDController(0.005, 0.001, 0.0005);

  public firstSubSystem() {
    super();
    motor = new SparkMax(Constants.firstSubSystem.MotorID, MotorType.kBrushless);

    turnController.setTolerance(2.0);
    turnController.setIntegratorRange(-5, 5);
    SmartDashboard.putData("Go To", new pidToAngle(this));
  }
  
  public double getPos(){
    return motor.getEncoder().getPosition() / Constants.firstSubSystem.GearRatio * 360;
  }

  public void setPower(double power){
    motor.set(power);
  }

  public void turnToAngle(double angle) {
    double currentAngle = getPos();
    double turnSpeed = turnController.calculate(currentAngle, angle);
    setPower(turnSpeed);
  }

  public void stop(){
    setPower(0);
  }



  @Override
  public void periodic() {
    SmartDashboard.putNumber("mAngle", getPos());
  
    SmartDashboard.putNumber("power", motor.getAppliedOutput());
  }
}
