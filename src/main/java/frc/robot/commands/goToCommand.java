// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.shiraConstants;
import frc.robot.subsystems.shiraSubSystem;
import edu.wpi.first.units.measure.Angle;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class goToCommand extends Command {
  private shiraSubSystem subsystem;
  private double power;
  private double angle; 
  private double startAngle;

  public goToCommand(shiraSubSystem subsystem , double power, double angle) {
    this.subsystem = subsystem;
    this.power = power;
    this.angle = angle;
    addRequirements(subsystem);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startAngle = subsystem.getPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    subsystem.setPower(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.setPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (power > 0 && subsystem.getPosition() >= startAngle + angle) ||
           (power < 0 && subsystem.getPosition() <= startAngle + angle);
  }

  
}
