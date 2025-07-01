package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystem;

public class GoToAngle extends Command {
    FirstSubsystem subsystem;
    double targetAngle;

    public static final double maxPower = 0.5;
    public static final double tolerance = 1;
    public static final double kp = 0.05;

    public GoToAngle(FirstSubsystem subsystem, double targetAngle) {
        this.subsystem = subsystem;
        this.targetAngle = targetAngle;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        System.out.println(" Command started at " + subsystem.getPosition() + " going to " + targetAngle + " degrees");
    }

    @Override
    public void execute() {
        double error = targetAngle - subsystem.getPosition();
        if(Math.abs(error) < tolerance) {
            error = 0;
        }
        subsystem.setPower(error * kp);
    }

    @Override
    public boolean isFinished() {
        double error = targetAngle - subsystem.getPosition();
        return Math.abs(error) < tolerance;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
        System.out.println(" Command ended at " + subsystem.getPosition() + " degrees, error = " + (targetAngle - subsystem.getPosition()));
    }


}
