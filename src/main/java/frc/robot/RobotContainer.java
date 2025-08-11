// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.SwerveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here... 
  private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  
  private CommandXboxController controller = new CommandXboxController(Constants.DriverConstants.DriverControllerPort);
  
    

  // Replace with CommandPS4Controller or CommandJoystick if needed




  public RobotContainer() {
    configureBindings();
    //configureDefaultCommands();
 

  }

  private void configureBindings() {
    //drive
    double vx = -controller.getLeftY()*20;
    System.out.println("Left Y: " + vx);
    //turn
    double leftTrigger = -controller.getLeftTriggerAxis();
    System.out.println("Left Trigger: " + leftTrigger);
    double rightTrigger = controller.getRightTriggerAxis();
    
    double radPerSec = (leftTrigger-rightTrigger)*2;
    
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
