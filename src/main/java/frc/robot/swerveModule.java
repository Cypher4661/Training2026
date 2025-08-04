// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
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
    public double getSteerPosition() {
        return steerMotor.getCurrentPosition();
    }
    public double getSteerVelocity() {
        return steerMotor.getCurrentVelocity();
    }
    public double getSteerDuty() {
        return steerMotor.getCurrentVoltage();
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
    public double getdriverPosition() {
        return driverMotor.getCurrentPosition();
    }
    public double getdriverVelocity() {
        return driverMotor.getCurrentVelocity();
    }
    public double getdriverDuty() {
        return driverMotor.getCurrentVoltage();
    }
    public double getAbsolutePosition() {
        return CANcoder.getAbsolutePosition().getValueAsDouble() * 360;
    }
    public void calibrateSteer(moduleConfig config) {
        double absoluteAngle = getAbsolutePosition() - config.offSet;
        steerMotor.getEncoder().setPosition(absoluteAngle * config.steerMotorGearRatio / 360);
    }
    public void setSwerveModuleState(SwerveModuleState state) {
        state = optimize(state, getSteerPosition());
        setSteerPosition(state.angle.getDegrees());
        setdriverVelocity(state.speedMetersPerSecond);
    }
    private SwerveModuleState optimize(SwerveModuleState state, double angle) {
        var delta = state.angle.getDegrees() - angle;
        if(Math.abs(delta) > 90) {
            return new SwerveModuleState(
                -state.speedMetersPerSecond, state.angle.rotateBy(Rotation2d.kPi));
        } else {
            return new SwerveModuleState(state.speedMetersPerSecond, state.angle);
        }
    }

}
