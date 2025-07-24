package frc.robot;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveVelocityCommand;
import frc.robot.commands.SetAngleCommand;
import frc.robot.subsystems.newSubsystem;
public class RobotContainer {

  private final newSubsystem driveSubsystem = new newSubsystem();
  private final newSubsystem angleSubsystem = new newSubsystem();
  private DriveVelocityCommand drive = new DriveVelocityCommand(driveSubsystem, 1);

  public RobotContainer() {
      configureBindings();
  }

  private void configureBindings() {
     // CommandXboxController controller = new CommandXboxController(0);

    //  controller.a().whileTrue(new DriveVelocityCommand(driveSubsystem, 3000));
    //  controller.b().whileTrue(new SetAngleCommand(angleSubsystem, 90));
  }

  public Command getAutonomousCommand() {
    return null;
}
}