package frc.robot.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystems;

public class goToPosition extends Command{

   FirstSubsystems subsystem;
   double target;
   double position;

   public goToPosition(double target, FirstSubsystems subsystem) {
    this.target = target;
    this.subsystem = subsystem;
    this.position = subsystem.getPosition();
    addRequirements(subsystem);
   }

    @Override
    public void execute() {
        if(position>){
            subsystem.setPower(-0.5);
        }
        else{
            subsystem.setPower(0.5);
        }
        
    }

    @Override
    public boolean isFinished() {

        double error = subsystem.getPosition()-target;
        return Math.abs(error) < 5;
        
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        subsystem.stop();
    }



}
