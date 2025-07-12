package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MyFirstSubsystem;

public class GoToCommand extends Command {

    MyFirstSubsystem subsystem;
    PIDController pid = new PIDController(0.005, 0.001, 0.0001);
    

    public GoToCommand( MyFirstSubsystem subsystem) {
        System.out.println("nnnnnnnnnnnnnnnnnnnnnnn");
        this.subsystem = subsystem;
        addRequirements(subsystem);
        SmartDashboard.putData("pid",pid);
        SmartDashboard.putNumber("targetPosition", 0);
        pid.setTolerance(5, 30);
        pid.enableContinuousInput(-180,180);
        pid.setIZone(10);
        pid.setIntegratorRange(-0.0015,0.0015);
        
        


        addRequirements(subsystem);
        System.out.println("dd");
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
        System.out.println("d");
    
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