// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class MyFirstSubsystem extends SubsystemBase{

    public static final int MotorID = 11 ;
    public static final boolean MotorInverted = false;
    public static final double MotorPositionRatio = 1.0/9; // Ratio to convert encoder ticks to desired units (e.g., degrees, meters)
    public static final int DriveMoterId = 2;
    public static final int SteerMOterId = 1;
    public static final int CANcoderId = 9; // ID for the CANcoder used for steering angle feedback
    public static final double DriveMoterRatio = 6.75;
    public static final double SteerMoterRatio = 150.0/7.0; // Ratio for steering motor, e.g., 150:7 for a 21:1 gearbox
    public static final boolean DriveMoterInverted = false; // Invert the drive motor if necessary
    public static final boolean SteerMoterInverted = true
  ; // Invert the steering motor if necessary
    
  }
  public static final class DriverConstants {

    public static final int DriverControllerPort = 0;
    public static final int OperatorControllerPort = 1;

    public static final double DriverDeadband = 0.1;
  }


}
