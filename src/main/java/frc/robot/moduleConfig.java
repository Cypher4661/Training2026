package frc.robot;

import com.ctre.phoenix6.CANBus;

import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public class moduleConfig {
    public final String name;
    public final int steerMotorId;
    public final int driverMotorId;
    public final int CANcoderId;
    public final double steerMotorGearRatio;
    public final double driverMotorGearRatio;
    public final boolean steerMotorIsInverted;
    public final boolean driverMotorIsInverted;
    public final double CANcoderOffSet;
    public final double diameter;
    public final SparkConfig steerMotorConfig;
    public final TalonConfig driverMotorConfig;


    public moduleConfig(String name, int steerMotorId, int driverMotorId, int CANcoderId, 
                        double steerMotorGearRatio, double driverMotorGearRatio, 
                        boolean steerMotorIsInverted, boolean driverMotorIsInverted, 
                        double CANcoderOffSet, double diameter) {
        this.name = name;
        this.steerMotorId = steerMotorId;
        this.driverMotorId = driverMotorId;
        this.CANcoderId = CANcoderId;
        this.steerMotorGearRatio = steerMotorGearRatio;
        this.driverMotorGearRatio = driverMotorGearRatio;
        this.steerMotorIsInverted = steerMotorIsInverted;
        this.driverMotorIsInverted = driverMotorIsInverted;
        this.CANcoderOffSet = CANcoderOffSet;
        this.diameter = diameter;
        steerMotorConfig = new SparkConfig(steerMotorId, name + " steer motor")
            .withBrake(true)
            .withInvert(steerMotorIsInverted)
            .withVelocities(720, 1000, 3000)
            .withRampTime(0.2)
            .withVolts(8)
            .withDegreesMotor(steerMotorGearRatio)
            .withCurrent(20)
            .withPID(0.001, 0, 0, 0.0075, 0.000625, 0, 0);
        driverMotorConfig = new TalonConfig(driverMotorId, new CANBus("rio"),name + " driver motor")
            .withBrake(true)
            .withInvert(driverMotorIsInverted)
            .withVelocities(720, 1000, 3000)
            .withRampTime(0.2)
            .withVolts(8)
            .withDegreesMotor(driverMotorGearRatio)
            .withCurrent(20)
            .withPID(0.001, 0, 0, 1.0/150, 2.0/9, 0, 0);

    }
}
