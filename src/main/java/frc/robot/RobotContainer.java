/*package frc.robot;


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
}*/

package frc.robot;

import frc.robot.subsystems.MotorSubsystem;
import frc.robot.commands.MoveMotorPowerCommand;
import frc.robot.commands.HoldVelocityCommand;
import frc.robot.commands.MoveToAngleCommand;

import com.demacia.elastilog.ElastiLog;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
    private final MotorSubsystem armSubsystem = new MotorSubsystem();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        ElastiLog.createEntry("arm/power", 0.0);
        ElastiLog.createEntry("arm/velocity", 0.0);
        ElastiLog.createEntry("arm/angle", 0.0);

        armSubsystem.setDefaultCommand(
            new MoveMotorPowerCommand(armSubsystem, () -> ElastiLog.get("arm/power"))
        );

        // להפעלת Velocity במקום Power:
        // armSubsystem.setDefaultCommand(
        //     new HoldVelocityCommand(armSubsystem, () -> ElastiLog.get("arm/velocity"))
        // );

        // להפעלת Angle במקום Power:
        // armSubsystem.setDefaultCommand(
        //     new MoveToAngleCommand(armSubsystem, () -> ElastiLog.get("arm/angle"))
        // );
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
