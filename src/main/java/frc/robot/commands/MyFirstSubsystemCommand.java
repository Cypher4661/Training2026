// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MyFirstSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MyFirstSubsystemCommand extends Command {
  private final MyFirstSubsystem subsystem;
  private final double power; private final double duration; private double startTime;
  private double calculatedDuration;
  private final double durationFor360Degrees = 0.26;
  private final double degrees;
  
  public MyFirstSubsystemCommand (MyFirstSubsystem subsystem, double power, double duration, double degrees) {
    this.subsystem = subsystem;
    this.power = power;
    this.duration = duration;
    this.degrees = degrees;
    addRequirements(subsystem);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (degrees > 0) {
      calculatedDuration = (degrees / 360.0) * durationFor360Degrees; // חישוב זמן לפי זוויות
      System.out.println("Command initialized for " + degrees + " degrees.");
    } else if (duration > 0) {
      calculatedDuration = duration; // שימוש בזמן ידני
      System.out.println("Command initialized for " + duration + " seconds.");
    } else {
      System.out.println("Error: Neither degrees nor duration specified!");
      calculatedDuration = 0; // לא לעשות כלום אם אין ערכים
    }
    startTime = Timer.getFPGATimestamp();
    System.out.println("Command started at: " + startTime + " seconds for "+ duration + " seconds with power: " + power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    subsystem.setPower(power);
  }
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() >= startTime + calculatedDuration;
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.stop();
    System.out.println("Command ended at: " + Timer.getFPGATimestamp());
  }

  // Returns true when the command should end.

}
