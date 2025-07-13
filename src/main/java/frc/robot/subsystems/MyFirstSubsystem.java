package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.GoToCommand;

public class MyFirstSubsystem extends SubsystemBase {
  /** Creates a new MyFirstSubsystem. */
  private final SparkMax  motor;
// Constructor
  public MyFirstSubsystem () {
    super();
    motor = new SparkMax(Constants.MyFirstSubsystem.MotorID, MotorType.kBrushless);
    motor.setInverted(false);
    SmartDashboard.putData("go to angle", new GoToCommand(this));

  }
  // Simple power -1 to 1
  public void setPower(double power) {
    motor.set(power);
  }
  // stop
  public void stop() {
    setPower(0);
  }

  public double getPosition() {
    return motor.getEncoder().getPosition() * Constants.MyFirstSubsystem.MotorPositionRatio * 360;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Motor Position", getPosition());
    SmartDashboard.putNumber("Motor Power", motor.getAppliedOutput());
  }

  

}