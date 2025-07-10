package frc.robot.commands;

import java.lang.ModuleLayer.Controller;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystems;

public class goToAngle extends Command {
    
   private FirstSubsystems subsystem;
   private double target;
   private double position;
   private double Kp;
   private double Ki;
   private double Kd;
   private double sumErrors = 0.0;
   private double lastError = 0.0;
   private final PIDController controller;

   public goToAngle(FirstSubsystems subsystem){
      this.position = subsystem.getPosition();
      this.subsystem = subsystem;
      controller = new PIDController(Kp, Ki, Kd);
      SmartDashboard.putNumber("Target", 0.0);
      controller.setTolerance(1, 2.0);
      controller.enableContinuousInput(-180, 180);
      controller.setIZone(20);
      controller.setIntegratorRange(-0.05, 0.05);
      SmartDashboard.putData("PID", controller);
      addRequirements(subsystem);
   }
    public void initialize(){
        controller.reset();
        controller.setSetpoint(position);
    }

    public void execute(){
        target = SmartDashboard.getNumber("Target", 0.0);
        position = subsystem.getPosition();
    
        double Power = controller.calculate(position, target);
        subsystem.setPower(Power);
    }
    
    public boolean isFinished(){
        return controller.atSetpoint();
    }

    public void end(boolean interrupted) {
        subsystem.stop();
    }

}
