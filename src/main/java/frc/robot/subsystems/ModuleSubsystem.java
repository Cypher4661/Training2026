// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Modela;

public class ModuleSubsystem extends SubsystemBase {
  private final SparkMax DriveMoter1, SteerMoter1;
  private final SparkMax DriveMoter2, SteerMoter2;
  private final SparkMax DriveMoter3, SteerMoter3;
  private final SparkMax DriveMoter4, SteerMoter4;
  private final CANcoder canCoder1, canCoder2, canCoder3, canCoder4;

  private final SimpleMotorFeedforward steerFF = new SimpleMotorFeedforward(0.0075, 0.000625);
  private final PIDController steerPID = new PIDController(0.001, 0, 0);
  private final SimpleMotorFeedforward driverFF = new SimpleMotorFeedforward(1.0/150.0, 2.0/9.0);
  private final PIDController driverPID = new PIDController(0.001, 0, 0);

  public ModuleSubsystem() {
    super();

    // Configuration
    SparkMaxConfig[] driveConfigs = new SparkMaxConfig[4];
    SparkMaxConfig[] steerConfigs = new SparkMaxConfig[4];

    for (int i = 0; i < 4; i++) {
      driveConfigs[i] = new SparkMaxConfig();
      driveConfigs[i].inverted(Constants.modelas[i].DriveMoterInverted);
      steerConfigs[i] = new SparkMaxConfig();
      steerConfigs[i].inverted(Constants.modelas[i].SteerMoterInverted);
    }

    // Motors and encoders
    DriveMoter1 = new SparkMax(Constants.modelas[0].DriveMoterId, MotorType.kBrushless);
    SteerMoter1 = new SparkMax(Constants.modelas[0].SteerMoterId, MotorType.kBrushless);
    canCoder1 = new CANcoder(Constants.modelas[0].CANcoderId);
    DriveMoter1.configure(driveConfigs[0], ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    SteerMoter1.configure(steerConfigs[0], ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    DriveMoter2 = new SparkMax(Constants.modelas[1].DriveMoterId, MotorType.kBrushless);
    SteerMoter2 = new SparkMax(Constants.modelas[1].SteerMoterId, MotorType.kBrushless);
    canCoder2 = new CANcoder(Constants.modelas[1].CANcoderId);
    DriveMoter2.configure(driveConfigs[1], ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    SteerMoter2.configure(steerConfigs[1], ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    DriveMoter3 = new SparkMax(Constants.modelas[2].DriveMoterId, MotorType.kBrushless);
    SteerMoter3 = new SparkMax(Constants.modelas[2].SteerMoterId, MotorType.kBrushless);
    canCoder3 = new CANcoder(Constants.modelas[2].CANcoderId);
    DriveMoter3.configure(driveConfigs[2], ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    SteerMoter3.configure(steerConfigs[2], ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    DriveMoter4 = new SparkMax(Constants.modelas[3].DriveMoterId, MotorType.kBrushless);
    SteerMoter4 = new SparkMax(Constants.modelas[3].SteerMoterId, MotorType.kBrushless);
    canCoder4 = new CANcoder(Constants.modelas[3].CANcoderId);
    DriveMoter4.configure(driveConfigs[3], ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    SteerMoter4.configure(steerConfigs[3], ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    for (int i = 1; i <= 4; i++) {
      calibrateSteer(i);
    }
  }

  private void calibrateSteer(int i) {
    Modela modela = Constants.modelas[i - 1];
    double absAngle = getAbseloteAngele(i) - modela.CANcoderOffset;
    getSteerMoter(i).getEncoder().setPosition(absAngle * modela.SteerMoterRatio / 360);
  }

  private SparkMax getSteerMoter(int i) {
    return switch (i) {
      case 1 -> SteerMoter1;
      case 2 -> SteerMoter2;
      case 3 -> SteerMoter3;
      case 4 -> SteerMoter4;
      default -> null;
    };
  }

  private SparkMax getDriveMoter(int i) {
    return switch (i) {
      case 1 -> DriveMoter1;
      case 2 -> DriveMoter2;
      case 3 -> DriveMoter3;
      case 4 -> DriveMoter4;
      default -> null;
    };
  }

  private CANcoder getCANcoder(int i) {
    return switch (i) {
      case 1 -> canCoder1;
      case 2 -> canCoder2;
      case 3 -> canCoder3;
      case 4 -> canCoder4;
      default -> null;
    };
  }

  public void setSteerAngle(double angle, int modelaNumber) {
    double error = MathUtil.inputModulus(angle, -180, 180) - getSteerPosition(modelaNumber);
    error = MathUtil.inputModulus(error, -180, 180);
    double velocity = Math.abs(error) < 4.0 ? 0.0 : error * 1.1;
    setSteerVelocity(velocity, modelaNumber);
  }

  public void setSteerPower(double power, int modelaNumber) {
    getSteerMoter(modelaNumber).set(power);
  }

  public void setDriverPower(double power, int modelaNumber) {
    getDriveMoter(modelaNumber).set(power);
  }

  public double getSteerPosition(int modelaNumber) {
    double raw = getSteerMoter(modelaNumber).getEncoder().getPosition();
    return MathUtil.inputModulus(raw / Constants.modelas[modelaNumber - 1].SteerMoterRatio * 360, -180, 180);
  }

  public double getDriverPosition(int modelaNumber) {
    return getDriveMoter(modelaNumber).getEncoder().getPosition() / Constants.modelas[modelaNumber - 1].DriveMoterRatio * 360;
  }

  public double getSteerVelocity(int modelaNumber) {
    return getSteerMoter(modelaNumber).getEncoder().getVelocity() / Constants.modelas[modelaNumber - 1].SteerMoterRatio * 360 / 60;
  }

  public double getdriverVelocity(int modelaNumber) {
    return getDriveMoter(modelaNumber).getEncoder().getVelocity() / Constants.modelas[modelaNumber - 1].DriveMoterRatio / 60 * Math.PI * Constants.modelas[modelaNumber - 1].diameter;
  }

  public double getSteerPower(int modelaNumber) {
    return getSteerMoter(modelaNumber).getAppliedOutput();
  }

  public double getdriverPower(int modelaNumber) {
    return getDriveMoter(modelaNumber).getAppliedOutput();
  }

  public double getAbseloteAngele(int modelaNumber) {
    return getCANcoder(modelaNumber).getAbsolutePosition().getValueAsDouble() * 360;
  }

  public void setSteerVelocity(double velocity, int modelaNumber) {
    double ff = steerFF.calculate(velocity);
    double pid = steerPID.calculate(getSteerVelocity(modelaNumber), velocity);
    getSteerMoter(modelaNumber).setVoltage(ff + pid);
  }

  public void setDriverVelocity(double velocity, int modelaNumber) {
    double ff = driverFF.calculate(velocity);
    double pid = driverPID.calculate(getdriverVelocity(modelaNumber), velocity);
    getDriveMoter(modelaNumber).setVoltage(ff + pid);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);
    for (int i = 1; i <= 4; i++) {
      final int idx = i;
      builder.addDoubleProperty("Steer Position " + i, () -> getSteerPosition(idx), null);
      builder.addDoubleProperty("Driver Position " + i, () -> getDriverPosition(idx), null);
      builder.addDoubleProperty("Steer Velocity " + i, () -> getSteerVelocity(idx), null);
      builder.addDoubleProperty("Driver Velocity " + i, () -> getdriverVelocity(idx), null);
      builder.addDoubleProperty("Steer Power " + i, () -> getSteerPower(idx), null);
      builder.addDoubleProperty("Driver Power " + i, () -> getdriverPower(idx), null);
      builder.addDoubleProperty("Abselote Angle " + i, () -> getAbseloteAngele(idx), null);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
