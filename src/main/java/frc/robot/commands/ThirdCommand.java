package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystem;

public class ThirdCommand extends Command {
    FirstSubsystem subsystem;
    double targetAngle;

    public static final double maxPower = 0.5;
    public static final double tolerance = 1;
    public static final double kp = 0.05;


    public ThirdCommand(FirstSubsystem subsystem) {
        this.subsystem = subsystem;
        SmartDashboard.putNumber("3rd cmd Target", 0);
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Third Command started at " + subsystem.getPosition());
    }

    @Override
    public void execute() {
        double target = SmartDashboard.getNumber("3rd cmd Target", 0);
        double error = target - subsystem.getPosition();
        if(Math.abs(error) < tolerance) {
            error = 0;
        }
        subsystem.setPower(error * kp);
        SmartDashboard.putNumber("3rd Cmd error", error);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
        System.out.println(" Command ended at " + subsystem.getPosition());
    }

}
