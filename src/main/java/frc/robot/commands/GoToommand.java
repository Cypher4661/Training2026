package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MyFirstSubsystem;

public class GoToommand extends Command{

    double targetPosition;
    MyFirstSubsystem subsystem;

    public GoToommand(double targetPosition, MyFirstSubsystem subsystem) {
        this.subsystem = subsystem;
        this.targetPosition = targetPosition;
        addRequirements(subsystem);
    }
    
}
