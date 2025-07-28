package frc.robot;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.CANBus;
import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public final class Constants {
  
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class modela1 extends SubsystemBase{

    public static final int DriveMoterId = 2;
    public static final int SteerMOterId = 1;
    public static final int CANcoderId = 9; // ID for the CANcoder used for steering angle feedback
    public static final double DriveMoterRatio = 6.75;
    public static final double SteerMoterRatio = 150.0/7.0; // Ratio for steering motor, e.g., 150:7 for a 21:1 gearbox
    public static final boolean DriveMoterInverted = true; // Invert the drive motor if necessary
    public static final boolean SteerMoterInverted = true;
    public static final double diameter = 0.1016; // Diameter of the wheel in meters (e.g., 4 inches converted to meters)
    public static final double CANcoderOffset = 8.0; // Offset for the CANcoder, adjust based on your setup
  ; 
  
    
  }
  public static final class DriverConstants {

    public static final int DriverControllerPort = 0;
    public static final int OperatorControllerPort = 1;

    public static final double DriverDeadband = 0.1;
  }
  public static class Example {
    public static final TalonConfig TALON_CONFIG = new TalonConfig(7,new CANBus("rio"), "talon example motor")
          .withBrake(true)
          .withCurrent(20)
          .withInvert(true)
          .withMeterMotor(12.7, 4*0.0254)
          .withVelocities(3, 6, 10)
          .withPID(1, 0, 0, 0.12, 3.7, 1.2, 0)
          .withRampTime(0.3)
          .withVolts(6);
    public static final SparkConfig SPARKMOTOR_CONFIG = new SparkConfig(8, "spark example motor")
        .withBrake(true)
        .withCurrent(20)
        .withInvert(false)
        .withPID(2,   0.2,   0, 0.1)
        .withDegreesMotor(8.4)
        .withVelocities(720, 1000, 3000)
        .withRampTime(0.2)
        .withVolts(8);
  }



}
