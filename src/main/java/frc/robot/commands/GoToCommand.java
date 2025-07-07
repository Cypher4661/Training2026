package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.MyFirstSubsystem;

public class GoToCommand extends Command {

    double targetPosition;
    MyFirstSubsystem subsystem;
    final double tolerance = 5;

    public GoToCommand(double targetPosition, MyFirstSubsystem subsystem) {
        this.subsystem = subsystem;
        this.targetPosition = targetPosition;
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double currentPosition = subsystem.getPosition();
        if (currentPosition < targetPosition) {
            subsystem.setPower(0.01);
        } else {
            subsystem.setPower(-0.01); // Adjust power as needed
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
        System.out.println("GoToCommand ended at: " + subsystem.getPosition() + " with target: " + targetPosition);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(subsystem.getPosition() - targetPosition) < tolerance; 
    }

}
