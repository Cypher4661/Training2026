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

  public static final class ModuleConfiger extends SubsystemBase {
    
    public static final ModuleConfig FL = new ModuleConfig("FL", 2, 1, 9, 6.75, 150.0/7.0, true, true, 0.1016, 8.0); // modela1
    public static final ModuleConfig FR = new ModuleConfig("FR",2, 1, 9, 6.75, 150.0/7.0, true, true, 0.1016, 8.0); // modela2
    public static final ModuleConfig BL = new ModuleConfig("BL",2, 1, 9, 6.75, 150.0/7.0, true, true, 0.1016, 8.0); // modela3
    public static final ModuleConfig BR = new ModuleConfig("BR",2, 1, 9, 6.75, 150.0/7.0, true, true, 0.1016, 8.0);  // modela4
};

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
