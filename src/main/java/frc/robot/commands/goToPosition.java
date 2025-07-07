package frc.robot.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystems;

public class goToPosition extends Command{

   private FirstSubsystems subsystem;
   private double target;
   private double position;
   

   public goToPosition(double target, FirstSubsystems subsystem) {
    this.target = target;
    this.subsystem = subsystem;
    this.position = subsystem.getPosition();
    addRequirements(subsystem);
   }

    @Override
    public void execute() {
        if(target > position){
            subsystem.setPower(0.05);
        }
        else if (target < position) {
          subsystem.setPower(-0.05);
        }
    }

    @Override
    public boolean isFinished() {

        double error = position-target;
        return Math.abs(error) < 10;
        
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
    }



}
