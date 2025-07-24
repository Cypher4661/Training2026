public class RobotContainer {

  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final AngleSubsystem angleSubsystem = new AngleSubsystem();

  public RobotContainer() {
      configureBindings();
  }

  private void configureBindings() {
      CommandXboxController controller = new CommandXboxController(0);

      controller.a().whileTrue(new DriveVelocityCommand(driveSubsystem, 3000));
      controller.b().whileTrue(new SetAngleCommand(angleSubsystem, 90));
  }

  public Command getAutonomousCommand() {
      return new SequentialCommandGroup(
          new DriveVelocityCommand(driveSubsystem, 2500).withTimeout(5),
          new SetAngleCommand(angleSubsystem, 45).withTimeout(3)
      );
  }
}
