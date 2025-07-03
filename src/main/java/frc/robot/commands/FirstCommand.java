package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystems;

public class FirstCommand extends Command{
   private final FirstSubsystems subsystem;
   private final double power; 
   private final double duration; 
   private double startTime;
   
   public FirstCommand (FirstSubsystems subsystem, double power, double duration) {
   this.subsystem = subsystem;
   this.power = power;
   this.duration = duration;
   addRequirements(subsystem);
 }
 @Override
 public void initialize() {
 startTime = Timer.getFPGATimestamp();
 System.out.println("Command started at: " + startTime + " seconds for "+ duration + " seconds with power: " + power);
 }
 @Override
 public void execute() {
 subsystem.setPower(power);
 }
 @Override
 public boolean isFinished() {
 return Timer.getFPGATimestamp() >= startTime + duration;
 }
 @Override
 public void end(boolean interrupted) {
 subsystem.stop();
 System.out.println("Command ended at: " + Timer.getFPGATimestamp());
 }
}
