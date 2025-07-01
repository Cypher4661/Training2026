package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystem;

public class SecondCommand extends Command {
    FirstSubsystem subsystem;
    double angle;
    double duration;
    double targetAngle;

    public static final double maxPower = 0.5;
    public static final double tolerance = 1;
    public static final double kp = 0.05;


    public SecondCommand(FirstSubsystem subsystem, double angle) {
        this.subsystem = subsystem;
        this.angle = angle;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        targetAngle = subsystem.getPosition();
        System.out.println(" Command started at " + Timer.getFPGATimestamp() + " at " + targetAngle + " degrees");
        targetAngle += angle;
        System.out.println(" Command target is " + targetAngle);
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
