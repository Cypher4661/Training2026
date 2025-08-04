package frc.robot;

import com.ctre.phoenix6.CANBus;

import frc.robot.subsystems.Swerve.ModuleConfig;
import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public final class Constants {
  public static final class FirstSubsystemsConstants{
    public static final int MotorId11 = 11;
    public static final boolean Motor11Inverted = false;
    public static final double GearRatio = 3;
    public static final double GearRatiosteer = 150.0/7;
    public static final double GearRatiodrive = 6.75;

    public static final double CancoderOffset = 11;
    
  }

  public static final class  ModuleConstants{
    public static final ModuleConfig FL_CONFIG = new ModuleConfig("FL",1 ,2 ,9 ,1);
    public static final ModuleConfig FR_CONFIG = new ModuleConfig("FR",3 ,4 ,10 ,2);
    public static final ModuleConfig BR_CONFIG = new ModuleConfig("BR",5 ,6 ,11 ,3);   
    public static final ModuleConfig BL_CONFIG = new ModuleConfig("BL",7 ,8 ,12 ,4);
  
    public static final int MotorIdsteer = 1;
    public static final int MotorIddrive = 2;
    public static final int CANcoderID = 9;
    public static final double GearRatiosteer = 150.0/7;
    public static final double GearRatiodrive = 6.75;
    public static final double CancoderOffset = 11;
    public static final double Diameter = 0.1016;
  }
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
