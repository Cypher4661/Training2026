// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;

import frc.robot.utils.SparkMotor;
import frc.robot.utils.TalonMotor;

/** Add your docs here. */
public class swerveModule {
    public final SparkMotor  steerMotor;
    public final TalonMotor  driverMotor;
    public final CANcoder CANcoder;

    public swerveModule(moduleConfig config) {
        this.steerMotor = new SparkMotor(config.steerMotorConfig);
        this.driverMotor = new TalonMotor(config.driverMotorConfig);
        this.CANcoder = new CANcoder(config.CANcoderId);
    }
    public void setSteerDuty(double duty) {
        steerMotor.setDuty(duty);
    }
    public void setSteerVelocity(double velocity) {
        steerMotor.setVelocity(velocity);
      }
    public void setSteerPosition(double position) {
        steerMotor.setPositionVoltage(position);
    }
    public void setdriverDuty(double duty) {
        driverMotor.setDuty(duty);
    }
    public void setdriverVelocity(double velocity) {
        driverMotor.setVelocity(velocity);
    }
    public void setdriverPosition(double position) {
        driverMotor.setPositionVoltage(position);
    }
}
