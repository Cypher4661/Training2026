package frc.robot.subsystems.Swerve;

import com.ctre.phoenix6.CANBus;

import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public class ModuleConfig {
    public int ModuleID;
    public SparkConfig SteerConfig;
    public TalonConfig DriveConfig;
    public int CancoderID;
    public String Name;
    public int SteerID;
    public int DriveID;
    public double GearRatiosteer = 150.0/7;
    public double GearRatiodrive = 6.75;
    public CANBus canbus = new CANBus("rio");

    public ModuleConfig(String name, int steerId, int driveId, int cancoderId, int moduleId) {
        this.ModuleID = moduleId;
        this.Name = name;
        this.SteerID = steerId;
        this.DriveID = driveId;
        this.CancoderID = cancoderId;
        
        SteerConfig = new SparkConfig(steerId, name + "/Steer")
            .withBrake(true)
            .withInvert(false)
            .withDegreesMotor(GearRatiosteer)
            .withCurrent(20)
            .withVolts(8)
            .withPID(0, 0, 0, 0, 0, 0, 0)
            .withRampTime(0.2)
            .withVelocities(720, 1200, 2000);
        
        DriveConfig = new TalonConfig(driveId,canbus, name + "/Drive")
            .withBrake(true)
            .withInvert(false)
            .withDegreesMotor(GearRatiodrive)
            .withCurrent(20)
            .withVolts(8)
            .withPID(0, 0, 0, 0, 0, 0, 0) 
            .withRampTime(0.2)    
            .withVelocities(720, 1200, 2000); 
 }
}
