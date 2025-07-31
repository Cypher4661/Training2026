// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

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
import frc.robot.modules.SwerveModule;

import java.util.Arrays;

public class modoleSubsystem extends SubsystemBase {
    private final SparkMax DriveMoter1, SteerMoter1;
    private final SparkMax DriveMoter2, SteerMoter2;
    private final SparkMax DriveMoter3, SteerMoter3;
    private final SparkMax DriveMoter4, SteerMoter4;
    private final CANcoder canCoder1, canCoder2, canCoder3, canCoder4;

    private final SimpleMotorFeedforward steerFF = new SimpleMotorFeedforward(0.0075, 0.000625);
    private final PIDController steerPID = new PIDController(0.001, 0, 0);
    private final SimpleMotorFeedforward driverFF = new SimpleMotorFeedforward(1.0/150.0, 2.0/9.0);
    private final PIDController driverPID = new PIDController(0.001, 0, 0);

    public modoleSubsystem() {
        // יצירת המודולים
        DriveMoter1 = new SparkMax(Constants.DriveConstants.DriveMoter1);
        SteerMoter1 = new SparkMax(Constants.DriveConstants.SteerMoter1);
        DriveMoter2 = new SparkMax(Constants.DriveConstants.DriveMoter2);
        SteerMoter2 = new SparkMax(Constants.DriveConstants.SteerMoter2);                                                       
        DriveMoter3 = new SparkMax(Constants.DriveConstants.DriveMoter3);
        SteerMoter3 = new SparkMax(Constants.DriveConstants.SteerMoter3);
        DriveMoter4 = new SparkMax(Constants.DriveConstants.DriveMoter4);
        SteerMoter4 = new SparkMax(Constants.DriveConstants.SteerMoter4);
        canCoder1 = new CANcoder(Constants.DriveConstants.CANcoder1);
        canCoder2 = new CANcoder(Constants.DriveConstants.CANcoder2);
        canCoder3 = new CANcoder(Constants.DriveConstants.CANcoder3);
        canCoder4 = new CANcoder(Constants.DriveConstants.CANcoder4);
        // הגדרת PID למנועי הסטיר
    }
}
