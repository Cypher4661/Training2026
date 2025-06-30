// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MyFirstSubsystem extends SubsystemBase {
  /** Creates a new MyFirstSubsystem. */
  private final TalonFX motor;
// Constructor
  public MyFirstSubsystem () {
    super();
    motor = new TalonFX(Constants.MyFirstSubsystem.MotorID);
    motor.setInverted(Constants.MyFirstSubsystem.MotorInverted);
  }
  // Simple power -1 to 1
  public void setPower(double power) {
    motor.set(power);
  }
  // stop
  public void stop() {
    setPower(0);
  }


}
