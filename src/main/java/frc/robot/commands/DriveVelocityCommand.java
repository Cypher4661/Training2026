package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.newSubsystem;

public class DriveVelocityCommand extends Command {

    private final newSubsystem drive;
    private final double targetVelocity;

    public DriveVelocityCommand(newSubsystem drive, double velocity) {
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
