// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.firstSubSystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class firstConstractor extends Command {

  double power;
  double timew;
  firstSubSystem subSystem;
  double startTime;
  /** Creates a new firstConstractor. */
  public firstConstractor(double speed, double timew, firstSubSystem subSystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    power = speed;
    this.timew = timew;
    this.subSystem = subSystem;
    addRequirements(subSystem);
    startTime = Timer.getFPGATimestamp();

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
  subSystem.setPower(power);
}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return startTime + timew < Timer.getFPGATimestamp();
  }
}
