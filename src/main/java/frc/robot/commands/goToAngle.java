// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.firstSubSystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class goToAngle extends Command {

  firstSubSystem subSystem;

  public goToAngle(firstSubSystem subSystem) {
    this.subSystem = subSystem;
    

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double wantedAngle = SmartDashboard.getNumber("wantedAngle", 90);
    if (subSystem.getPos() < wantedAngle) {
      subSystem.setPower(0.05);
    }
    else if(wantedAngle < subSystem.getPos())
      subSystem.setPower(-0.05);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subSystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(subSystem.getPos() - SmartDashboard.getNumber("wantedAngle", 90)) < 10;
  }
}
