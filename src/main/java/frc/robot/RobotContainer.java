// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.GoToAngle;
import frc.robot.commands.armcommand;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final Arm subsystem = new Arm();
 private Command autoCommand = new GoToAngle(subsystem, 90);
 



  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings();
    configureDefaultCommands();

  }
private void configureBindings(){}
private void configureDefaultCommands(){
  subsystem.setDefaultCommand(new armcommand(subsystem, 0, 0));
}
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoCommand;
  }
}
