package frc.robot;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.CANBus;
import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public final class Constants {
  
  public static class OperatorConstants {
    public static final int OperatorControllerPort = 1;
  }

  public static final class ModuleConfiger extends SubsystemBase {
    
    public static final ModuleConfig FL = new ModuleConfig("FL", 2, 1, 9, 6.75, 150.0/7.0, true, true, 0.1016, 8.0, 0); // modela1
    public static final ModuleConfig FR = new ModuleConfig("FR",2, 1, 9, 6.75, 150.0/7.0, true, true, 0.1016, 8.0,0); // modela2
    public static final ModuleConfig BL = new ModuleConfig("BL",2, 1, 9, 6.75, 150.0/7.0, true, true, 0.1016, 8.0,0); // modela3
    public static final ModuleConfig BR = new ModuleConfig("BR",2, 1, 9, 6.75, 150.0/7.0, true, true, 0.1016, 8.0,0);  // modela4
};

  public static final class DriverConstants {
    public static final int DriverControllerPort = 0;
    public static final double MaxVelocity = 3.0; // m/s
    public static final double MaxAngularVelocity = 2 * Math.PI; // rad/s

  }




}
