// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

<<<<<<< HEAD
import frc.robot.commands.MotorCommand;
import frc.robot.commands.goToAngle;
import frc.robot.commands.goToPosition;
import frc.robot.subsystems.MotorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;



/**
 * This class is where the bulk of   the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final MotorSubsystem subsystem = new MotorSubsystem ();
  private Command autoCommand = new MotorCommand(subsystem, 0.3, 10.0);



  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
=======
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  
  // private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

>>>>>>> origin/DemaciaUtils
  public RobotContainer() {
    configureBindings();
    configureDefaultCommands();
  }

  private void configureBindings() {
  }
 
  private void configureDefaultCommands() {
  
  }   

  public Command getAutonomousCommand() {
<<<<<<< HEAD
    // An example command will be run in autonomous
    return  new goToPosition(90, subsystem)
      .andThen(new WaitCommand(5))
      .andThen(new goToPosition(135, subsystem))
      .andThen(new WaitCommand(5))
      .andThen(new goToPosition(0, subsystem));
=======
    return null;
>>>>>>> origin/DemaciaUtils
  }
}

