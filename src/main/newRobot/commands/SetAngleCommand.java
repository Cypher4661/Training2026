public class SetAngleCommand extends CommandBase {

    private final AngleSubsystem angleSubsystem;
    private final double targetAngle;

    public SetAngleCommand(AngleSubsystem angleSubsystem, double angle) {
        this.angleSubsystem = angleSubsystem;
        this.targetAngle = angle;
        addRequirements(angleSubsystem);
    }

    @Override
    public void execute() {
        angleSubsystem.setAngle(targetAngle);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(angleSubsystem.getPosition() - targetAngle) < 1; // ±1 degree
    }

    @Override
    public void end(boolean interrupted) {
        angleSubsystem.stop();
    }
}
