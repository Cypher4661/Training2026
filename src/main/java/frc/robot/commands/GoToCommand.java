package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.MyFirstSubsystem;

public class GoToCommand extends Command {

    MyFirstSubsystem subsystem;
    PIDController pid = new PIDController(0, 0, 0);
    

    public GoToCommand( MyFirstSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
        SmartDashboard.putData("pid",pid);
        SmartDashboard.putNumber("targetPosition", 0);
        pid.setTolerance(5, 30);
        pid.enableContinuousInput(-180,180);
        pid.setIZone(10);
        pid.setIntegratorRange(-0.01,0.01);
        SmartDashboard.putNumber("targetPosition", 0);


        addRequirements(subsystem);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pid.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double currentPosition = subsystem.getPosition();
        double target = SmartDashboard.getNumber("targetPosition", 0);
       subsystem.setPower(pid.calculate(currentPosition, target)); // Adjust power as needed
    
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
      
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }

}
