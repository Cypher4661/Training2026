package frc.robot;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Swerve.ModuleConfig;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class DriveConstants {
    public static final int DriverControllerPort = 0;
    public static final int operatorControllerPort = 1;
  }
  public static final class ModuleConfiger extends SubsystemBase {
    public static final ModuleConfig FL = new ModuleConfig("FL", 2, 1, 9, 150.0/7.0, 0, true, true, 0.1016, 8.0, 0.0);
    public static final ModuleConfig FR = new ModuleConfig("FR", 2, 1, 9, 150.0/7.0, 0, true, true, 0.1016, 8.0, 0.0);
    public static final ModuleConfig BR = new ModuleConfig("BR", 2, 1, 9, 150.0/7.0, 0, true, true, 0.1016, 8.0, 0.0);
    public static final ModuleConfig BL = new ModuleConfig("BL", 2, 1, 9, 150.0/7.0, 0, true, true, 0.1016, 8.0, 0.0);
  }
  
}