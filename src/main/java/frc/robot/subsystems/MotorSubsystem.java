package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.goToAngle;

public class MotorSubsystem extends SubsystemBase {
  private final SparkMax motor;
  
  /** Creates a new subsystems. */
  public MotorSubsystem() {
    super();
    motor = new SparkMax(Constants.MyFirstSubsystemConstants.MotorID,MotorType.kBrushless);
    SmartDashboard.putData("cmd", new goToAngle(this));
  }
 public void setPower(double power) {
  motor.set(power);
  }
  public double getPosition() {
    return motor.getEncoder().getPosition()  * Constants.MyFirstSubsystemConstants.GearRatio *360;
  }
 

  public void stop() {
    setPower(0);
  }
}