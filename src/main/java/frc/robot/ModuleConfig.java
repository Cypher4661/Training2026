package frc.robot;

import com.ctre.phoenix6.CANBus;

import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public class ModuleConfig {
    public final String name;
    public final int DriveMoterId;
    public final int SteerMoterId;
    public final int CANcoderId;
    public final double DriveMoterRatio;
    public final double SteerMoterRatio;
    public final boolean DriveMoterInverted;
    public final boolean SteerMoterInverted;
    public final double diameter;
    public final double CANcoderOffset;
    public final SparkConfig steerConfig;
    public final TalonConfig driveConfig;

    public ModuleConfig(String name,
        int driveMoterId, int steerMoterId, int canCoderId,
        double driveMoterRatio, double steerMoterRatio,
        boolean driveMoterInverted, boolean steerMoterInverted,
        double diameter, double canCoderOffset
    ) {
        this.name = name;
        this.DriveMoterId = driveMoterId;
        this.SteerMoterId = steerMoterId;
        this.CANcoderId = canCoderId;
        this.DriveMoterRatio = driveMoterRatio;
        this.SteerMoterRatio = steerMoterRatio;
        this.DriveMoterInverted = driveMoterInverted;
        this.SteerMoterInverted = steerMoterInverted;
        this.diameter = diameter;
        this.CANcoderOffset = canCoderOffset;
        steerConfig = new SparkConfig(steerMoterId, name + "/   steer motor")
            .withBrake(true)
            .withInvert(steerMoterInverted)
            .withDegreesMotor(steerMoterRatio)
            .withVelocities(720, 1000, 3000)
            .withRampTime(0.2)
            .withCurrent(20)
            .withVolts(8)
            .withPID(0.001, 0, 0, 0.0075, 0.000625, 0, 0);
        driveConfig = new TalonConfig(driveMoterId, new CANBus("rio"),  name + "/   drive motor")
            .withBrake(true)
            .withInvert(driveMoterInverted)
            .withMeterMotor(driveMoterRatio, diameter*Math.PI) // diameter in meters, wheel radius in meters
            .withVelocities(3, 6, 10) // m/s
            .withPID(0.001, 0, 0, 1.0/150, 2.0/9, 0, 0)
            .withRampTime(0.3)
            .withVolts(6)
            .withCurrent(20);
    }
}
