package frc.robot.commands;

import java.lang.ModuleLayer.Controller;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.MyFirstSubsystem;

public class GoToCommand extends Command {
    PIDController pidController = new PIDController(0,0,0);
    MyFirstSubsystem subsystem;

    public GoToCommand(MyFirstSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
        SmartDashboard.putData("pid",pidController );
        SmartDashboard.putNumber("targetPosition", 0);
        pidController.setTolerance(5, 20);
        pidController.enableContinuousInput(-180,180); 
        pidController.setIZone(30);
        pidController.setIntegratorRange(-0.01,0.01);
        SmartDashboard.getNumber("targetPosition", 0);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pidController.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double currentPosition = subsystem.getPosition();
        double targetPosition = SmartDashboard.getNumber("targetPosition", 0);
        subsystem.setPower(pidController.calculate(currentPosition, targetPosition));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return pidController.atSetpoint();
    }


}
