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
      this.target = SmartDashboard.getNumber("Target", 0.0);
      Kp = SmartDashboard.getNumber("Kp", 0.0);
      Ki = SmartDashboard.getNumber("Ki", 0.0);
      Kd = SmartDashboard.getNumber("Kd", 0.0);
      controller.setTolerance(2.0, 15.0);
      controller.enableContinuousInput(-180, 180);
      addRequirements(subsystem);
   }
    public void initialize(){
        controller.reset();
        controller.setSetpoint(position);
    }

    public void execute(){
        double Power = controller.calculate(position);
        subsystem.setPower(Power);
    }
    
    public boolean isFinished(){
        return controller.atSetpoint();
    }

    public void end(boolean interrupted) {
        subsystem.stop();
    }

}
