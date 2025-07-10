package frc.robot.commands;

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
   private PIDController controller = new PIDController(Kp, Ki, Kd);

   public goToAngle(FirstSubsystems subsystem){
      this.position = subsystem.getPosition();
      this.subsystem = subsystem;
      this.target = SmartDashboard.getNumber("Target", 0.0);
      Kp = SmartDashboard.getNumber("Kp", 0.0);
      Ki = SmartDashboard.getNumber("Ki", 0.0);
      Kd = SmartDashboard.getNumber("Kd", 0.0);
      addRequirements(subsystem);
   }
    

    public void execute(){
        controller.enableContinuousInput(-180, 180);
        double Power = controller.calculate(position, target);
        subsystem.setPower(Power);
    }
    
    public boolean isFinished(){
        double error = position-target;
        return Math.abs(error) < 0;
    }

    public void end(boolean interrupted) {
        subsystem.stop();
    }

}
