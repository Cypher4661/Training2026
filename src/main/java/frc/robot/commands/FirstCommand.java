package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystem;

public class FirstCommand extends Command {
    FirstSubsystem subsystem;
    double power;
    double duration;
    double startTime;

    public FirstCommand(FirstSubsystem subsystem, double power, double duration) {
        this.subsystem = subsystem;
        this.power = power;
        this.duration = duration;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
        System.out.println(" Command started at " + Timer.getFPGATimestamp());
    }

    @Override
    public void execute() {
        subsystem.setPower(power);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() > startTime + duration;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
        System.out.println(" Command ended at " + Timer.getFPGATimestamp());
    }

}
