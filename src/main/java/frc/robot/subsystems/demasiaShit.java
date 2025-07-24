// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.SparkConfig;
import frc.robot.utils.SparkMotor;

public class demasiaShit extends SubsystemBase {
  SparkMotor baseMotor;
  /** Creates a new demasiaShit. */
  public demasiaShit() {
    super();
     SparkConfig baseDriveConfig = new SparkConfig(0, "Base Drive")
        .withBrake(true)
        .withInvert(true)
        .withRampTime(0.1)
        .withPID(1, 0, 0, 1)
        .withDegreesMotor(0);

     baseMotor = new SparkMotor(baseDriveConfig);

  }

  public void setPower(double power) {
    baseMotor.setDuty(power);
  }

  @Override
  public void periodic() {
    setPower(SmartDashboard.getNumber("motor power", 0));
    // This method will be called once per scheduler run
  }
}
