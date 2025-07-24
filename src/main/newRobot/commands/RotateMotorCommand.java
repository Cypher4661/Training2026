package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.subsystem;

public class RotateMotorCommand extends Command {

  private final subsystem subsystem;
  public final double target1;
  private final double power;
  private double lastAngle1;
  private double startTime;

  public RotateMotorCommand(subsystem subsystem, double target1, double power) {
    this.subsystem = subsystem;
    this.target1 = target1;

    this.power = power;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
    lastAngle1 = subsystem.getAngleDegrees1();
    System.out.println("RotateTwoMotorsCommand started");
  }

  @Override
  public void execute() {
    double currentAngle1 = subsystem.getAngleDegrees1();
    double power1 = Math.signum(target1 - currentAngle1) * power;
    subsystem.setPower(power1);

  }

  @Override
  public boolean isFinished() {
    double err = Math.abs(subsystem.getAngleDegrees1() - targetAngle());
    return err <= 10.0;
  
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.stop();
    System.out.println("RotateTwoMotorsCommand ended.Time = " + (Timer.getFPGATimestamp() - startTime) + "s");
  }
}