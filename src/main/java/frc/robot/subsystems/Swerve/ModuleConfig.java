package frc.robot.subsystems.Swerve;

import com.ctre.phoenix6.CANBus;

import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public class ModuleConfig {
    public  SparkConfig SteerConfig;
    public TalonConfig DriveConfig;
    public int CancoderId;
    public String Name;
    public int SteerId;
    public int DriveId;
    public CANBus canbus = new CANBus("rio");
}
