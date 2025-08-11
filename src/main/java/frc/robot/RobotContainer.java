// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.FirstCommand;
import frc.robot.commands.goToPosition;
import frc.robot.subsystems.FirstSubsystems;
import frc.robot.subsystems.Swerve.SwerveModule;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Examples.DemaciaMotorExample;

public class RobotContainer {

  public static Robot robot;
  public static int N_CYCLE = 0;
  public static double CYCLE_TIME = 0.02;

  public DemaciaMotorExample demaciaMotorExample = new DemaciaMotorExample();

  public RobotContainer(Robot robot) {
    RobotContainer.robot = robot;
    RobotContainer.CYCLE_TIME = robot.getPeriod();
    configureBindings();
  }
  
   private void configureBindings() {
  }

  public static boolean isEnabled() {
    return robot.isEnabled();
  }

  public void periodic() {
    N_CYCLE++;
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autogotCommand;
  }
}
