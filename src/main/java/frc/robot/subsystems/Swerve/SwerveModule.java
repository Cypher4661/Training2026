package frc.robot;

import java.lang.ProcessBuilder.Redirect.Type;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.subsystems.Swerve.ModuleConfig;
import frc.robot.utils.TalonMotor;

public class SwerveModule {
    private SparkMax steerMotor;
    private TalonMotor driveMotor;
    private CANcoder cancoder;
    public String name;

    public SwerveModule(ModuleConfig configs) {
        steerMotor = new SparkMax(ModuleConfig.steerconfig, MotorType.kBrushless);
        driveMotor = new TalonMotor(ModuleConfig.driveconfig, MotorType.kBrushless);
        cancoder = new CANcoder(configs.CANCODER_CONFIG);
        name = configs.NAME;

        steerMotor.setPosition(getAbsoluteAngle() - configs.STEER_OFFSET);        
    }

    public void setNeutralMode(boolean isBrake) {
        driveMotor.setNeutralMode(isBrake);
        steerMotor.setNeutralMode(isBrake);
    }

    public void setSteerPower(double power) {
        steerMotor.set(power);
    }

    public double getAbsoluteAngle() {
        return cancoder.getCurrentAbsPosition();
    }

    public void setDrivePower(double power) {
        driveMotor.set(power);
    }
    
    public void setSteerVelocity(double velocityRadsPerSecond) {
        steerMotor.setVelocity(velocityRadsPerSecond);
    }

    public void setDriveVelocity(double velocityMetersPerSecond) {
        driveMotor.setVelocity(velocityMetersPerSecond);
    }
        public void setSteerPosition(double positionRadians) {
        steerMotor.setPositionVoltage(positionRadians);
    }
    public double getSteerAngle() {
        return steerMotor.getCurrentPosition();
    }
    public Rotation2d getSteerRotation() {
        return new Rotation2d(getSteerAngle());
    }
    public double getSteerVel() {
        return steerMotor.getCurrentVelocity();
    }
    public double getDriveVel() {
        return driveMotor.getCurrentVelocity();
    }

    public void setState(SwerveModuleState state) {
        double wantedAngle = state.angle.getRadians();
        double diff = wantedAngle - steerMotor.getCurrentPosition();
        double vel = state.speedMetersPerSecond;
        diff = MathUtil.angleModulus(diff);
        if(diff > 0.5 * Math.PI) {
            vel = -vel;
            diff = diff-Math.PI;
        } else if(diff < -0.5 * Math.PI) {
            vel = -vel;
            diff = diff + Math.PI;
        }
        setSteerPosition(steerMotor.getCurrentPosition() + diff);
        setDriveVelocity(vel);
    }
    
    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(driveMotor.getCurrentPosition(),
Rotation2d.fromRadians(steerMotor.getCurrentPosition()));
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVel(), getSteerRotation());
    }

    public void stop() {
        steerMotor.stopMotor();
        driveMotor.stopMotor();
    }
}