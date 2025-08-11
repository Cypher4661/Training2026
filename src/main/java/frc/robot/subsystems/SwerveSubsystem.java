// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SwerveModule; // Ensure this import matches the actual package of SwerveModule


public class SwerveSubsystem extends SubsystemBase {
  private SwerveModule FL;
  private SwerveModule FR;
  private SwerveModule BL;
  private SwerveModule BR;
  private SwerveModule[] modules;


  /** Creates a new SwerveSubsystem. */
  public SwerveSubsystem() {
    modules = new SwerveModule[]{
      new SwerveModule(Constants.ModuleConfiger.FL),
      new SwerveModule(Constants.ModuleConfiger.FR),
      new SwerveModule(Constants.ModuleConfiger.BL),
      new SwerveModule(Constants.ModuleConfiger.BR)
    };




  }
  public void drive(SwerveModuleState state) {
    // Implement the logic to drive the swerve modules based on vx, vy, and omega
    // This could involve calculating the desired angles and speeds for each module
    FL.setState(state);
    FR.setState( state);
    BL.setState(state);
    BR.setState(state);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }
}
