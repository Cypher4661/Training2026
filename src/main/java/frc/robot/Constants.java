// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
  public static class MyFirstSubsystem {

    public static final int MotorID = 11 ;
    public static final boolean MotorInverted = false;
    public static final double GearRatio = 1.0/3.0; // Example gear ratio, adjust as needed
    
  }
  public static final class DriverConstants {

    public static final int DriverControllerPort = 0;
    public static final int OperatorControllerPort = 1;

    public static final double DriverDeadband = 0.1;
  }


}
