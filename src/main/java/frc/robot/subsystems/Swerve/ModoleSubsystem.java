// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems.Swerve;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.Arrays;

public class ModoleSubsystem extends SubsystemBase {
    private final SparkMax driveMoter1, SteerMoter1;
    private final SparkMax DriveMoter2, SteerMoter2;
    private final SparkMax DriveMoter3, SteerMoter3;
    private final SparkMax DriveMoter4, SteerMoter4;
    private final CANcoder canCoder1, canCoder2, canCoder3, canCoder4;

    private final SimpleMotorFeedforward steerFF = new SimpleMotorFeedforward(0.0075, 0.000625);
    private final PIDController steerPID = new PIDController(0.001, 0, 0);
    private final SimpleMotorFeedforward driverFF = new SimpleMotorFeedforward(1.0/150.0, 2.0/9.0);
    private final PIDController driverPID = new PIDController(0.001, 0, 0);

    public modoleSubsystem() {

    }
}
