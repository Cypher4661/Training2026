package frc.robot;

import com.ctre.phoenix6.CANBus;
import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
public static final class MyFirstSubsystemConstants {
    public static final int MotorID = 11;
    public static final double GearRatio = 1.0/9;
    public static final Boolean MotorInverted = false;
    public static final int CANcoderId = 9;
    public static final double CANofset = 8.0;
    public static final double diameter = 4 * 0.0254;
    }

public static final class DriverConstants {
    public static final int DriverControllerPort = 0;
    public static final int OperatorControllerPort = 1;
    public static final double DriverDeadband = 0.1;


    }
    public static final class driveMotorConstants {
        public static final int driveMotorID = 2; // Example drive motor ID
        public static final double driveMotorGearRatio = 6.75; // Example gear ratio for drive motor
        public static final boolean driveMotorInverted = false; // Example inversion for drive motor
    }

    public static final class steerMotorConstants {
        public static final int steerMotorID = 1; // Example steer motor ID
        public static final double steerMotorGearRatio = 150/7.0; // Example gear ratio for steer motor
        public static final boolean steerMotorInverted = false; // Example inversion for steer motor
    }
}

