package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MotorSubsystem;

public class goToPosition extends Command {
    private double target;
    private MotorSubsystem subsystem;

    private final double tolerance =5;
    public goToPosition(double target, MotorSubsystem subsystem) {
        this.target = target;
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double current = subsystem.getPosition();
        double error = target - current;
        if(error > 0) {
            subsystem.setPower(0.015);
        } else {
            subsystem.setPower(-0.015);
        }
    }

    @Override
    public boolean isFinished( ) {
        double current = subsystem.getPosition();
        double error = target - current;
        return Math.abs(error) < tolerance;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
    }
}
