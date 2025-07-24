public class DriveVelocityCommand extends CommandBase {

    private final DriveSubsystem drive;
    private final double targetVelocity;

    public DriveVelocityCommand(DriveSubsystem drive, double velocity) {
        this.drive = drive;
        this.targetVelocity = velocity;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.setVelocity(targetVelocity);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
