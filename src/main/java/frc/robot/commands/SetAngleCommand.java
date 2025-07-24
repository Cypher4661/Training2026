package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.newSubsystem;

public class SetAngleCommand extends Command {

    private final newSubsystem newSubsystem;
    private final double targetAngle;

    public SetAngleCommand(newSubsystem newSubsystem, double angle) {
        this.newSubsystem = newSubsystem;
        this.targetAngle = angle;
        addRequirements(newSubsystem);
    }

    @Override
    public void execute() {
        newSubsystem.setAngle(targetAngle);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(newSubsystem.getPosition() - targetAngle) < 1; // ±1 degree
    }

    @Override
    public void end(boolean interrupted) {
        newSubsystem.stop();
    }
}
