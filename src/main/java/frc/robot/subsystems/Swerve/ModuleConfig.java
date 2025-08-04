package frc.robot.subsystems.Swerve;

import com.ctre.phoenix6.CANBus;

import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public class ModuleConfig {
    public final int driveMotorID;
    public final int steerMotorID;
    public final int CANcoderID;
    public final double driveMotorRatio;
    public final double steerMotorRatio;
    public final boolean driveMotorInvrted;
    public final boolean steerMotorInvrted;
    public final double dimeter;
    public final double CANcoderOffset;
    public final String name ;
    public final SparkConfig steerConfig;
    public final TalonConfig driveConfig;
    public final double Offset; // Offset for the steer motor, if needed

    public ModuleConfig(String name, int driveMotorID, int steerMotorID, int CANcoderID, double driveMotorRatio, double steerMotorRatio, 
    boolean driveMotorInvrted, boolean steerMotorInvrted, double dimeter, double CANcoderOffset, double offset) {

        this.name = name;
        this.driveMotorID = driveMotorID;
        this.steerMotorID = steerMotorID;
        this.CANcoderID = CANcoderID;
        this.driveMotorRatio = driveMotorRatio;
        this.steerMotorRatio = steerMotorRatio;
        this.dimeter = dimeter;
        this.CANcoderOffset = CANcoderOffset;
        this.driveMotorInvrted = driveMotorInvrted;
        this.steerMotorInvrted = steerMotorInvrted;
        this.Offset = offset;
        // Initialize the configurations for the motors

        steerConfig = new SparkConfig(steerMotorID, name + "/ steer motor")
            .withVolts(8,-8)
            .withCurrent(20)
            .withBrake(true)
            .withInvert(steerMotorInvrted)
            .withRampTime(0.1)
            .withMotorRatio(steerMotorRatio)
            .withMeterMotor(dimeter * Math.PI);

        driveConfig = new TalonConfig(driveMotorID, new CANBus("rio"), name + "/ drive motor")
            .withVolts(12, -12)
            .withCurrent(20, 0,0)
            .withBrake(true)
            .withInvert(driveMotorInvrted)
            .withRampTime(0.1)
            .withMotorRatio(driveMotorRatio)
            .withMeterMotor(dimeter * Math.PI);
    }
}
