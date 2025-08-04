package frc.robot;

import com.ctre.phoenix6.CANBus;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
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

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class moduleConfiger extends SubsystemBase{

    public static final int MotorID = 11 ;
    public static final boolean MotorInverted = false;
    public static final double MotorPositionRatio = 1.0/3; // Ratio to convert encoder ticks to desired units (e.g., degrees, meters)
    public static final moduleConfig FL = new moduleConfig("FL", 2,1,9,6.75,150.0/7,true,true,0.1016,8.0,0);
    public static final moduleConfig FR = new moduleConfig("FR",2,1,9,6.75,150.0/7,true,true,0.1016,8.0,0);
    public static final moduleConfig BL = new moduleConfig("BL",2,1,9,6.75,150.0/7,true,true,0.1016,8.0,0);
    public static final moduleConfig BR = new moduleConfig("BR",2,1,9,6.75,150.0/7,true,true,0.1016,8.0,0);
  }
  public static final class DriverConstants {

    public static final int DriverControllerPort = 0;
    public static final int OperatorControllerPort = 1;

    public static final double DriverDeadband = 0.1;
  }


}
