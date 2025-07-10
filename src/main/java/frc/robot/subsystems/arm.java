// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.GoToAngle;

public class Arm extends SubsystemBase {
   private final SparkMax motor;
  /** Creates a new arm. */
  public Arm() { 
    super();
    motor = new SparkMax(Constants.armConstants.MotorID, MotorType.kBrushless);
    motor.setInverted(Constants.armConstants.MotorInvereted);
    SmartDashboard.putData("arm",this);
   }
public Command getCommand(){
  return new GoToAngle(this, 90).andThen(new WaitCommand(5),new GoToAngle(this, 135), new WaitCommand(2),new GoToAngle(this, 0));

}   
public void setPower(double power){
  motor.set(power);
}
public void stop(){
  setPower(0 );
}
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Position", getPosition());
    
    
    // This method will be called once per scheduler run
  }
  public double getPosition(){
    return motor.getEncoder().getPosition()/Constants.armConstants.GearRatio*360;
  }
}
