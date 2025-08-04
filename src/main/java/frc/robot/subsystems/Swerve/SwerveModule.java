package frc.robot.subsystems.Swerve;


import com.ctre.phoenix6.hardware.CANcoder;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import frc.robot.utils.SparkMotor;
import frc.robot.utils.TalonMotor;

public class SwerveModule {
    private SparkMotor steerMotor;
    private TalonMotor driveMotor;
    private CANcoder cancoder;
    public String name;

    public SwerveModule(ModuleConfig configs) {
        steerMotor = new SparkMotor(configs.steerConfig);
        driveMotor = new TalonMotor(configs.driveConfig);
        cancoder = new CANcoder(configs.CANcoderID);
        name = configs.name;
    }
    public void setDutySteer(double duty) {
        steerMotor.setDuty(duty);
    }
    public void setDutyDrive(double duty) {
        driveMotor.setDuty(duty);
    }

    public double getDutySteer() {
        return steerMotor.getCurrentVoltage();
    }

    public double getDutyDrive() {
        return driveMotor.getCurrentVoltage();
    }

    public void setNeutralMode(boolean isBrake) {
        driveMotor.setNeutralMode(isBrake);
        steerMotor.setNeutralMode(isBrake);
    }

    public void setSteerPower(double power) {
        steerMotor.set(power);
    }

    public double getAbsoluteAngle() {
        return cancoder.getAbsolutePosition().getValueAsDouble()*360;
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

    
    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(driveMotor.getCurrentPosition(),
        Rotation2d.fromRadians(steerMotor.getCurrentPosition()));
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVel(), getSteerRotation());
    }

    public void(SwerveModuleState state) {
            setSteerPosition(state.angle.getDegrees());
            setDriveVelocity(state.speedMetersPerSecond);
    }
 
    public void stop() {
        steerMotor.stopMotor();
        driveMotor.stopMotor();
    }
    private SwerveModuleState optimize(SwerveModuleState state, double angle) {
        var delta = state.angle.getDegrees() - angle;
        if (Math.abs(delta) > 90) {
            return new SwerveModuleState(
                -state.speedMetersPerSecond, state.angle.rotateBy(Rotation2d.kPi));
        }
        else{
            return new SwerveModuleState()(state.speedMetersPerSecond, state.angle);
        }
    }
}