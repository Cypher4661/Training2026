// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Swerve.SwerveModule;
import static frc.robot.Constants.*;

public class Chassis extends SubsystemBase {
 public final SwerveModule FL= new SwerveModule(ModuleConstants.FL_CONFIG);
 public final SwerveModule FR= new SwerveModule(ModuleConstants.FR_CONFIG);
 public final SwerveModule BR= new SwerveModule(ModuleConstants.BR_CONFIG);
 public final SwerveModule BL= new SwerveModule(ModuleConstants.BL_CONFIG);
  public Chassis(){


  }

  @Override
  public void periodic() {
  }
}
