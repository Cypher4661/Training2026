package frc.robot.subsystems.Swerve;

import com.ctre.phoenix6.hardware.CANcoder;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.utils.SparkMotor;
import frc.robot.utils.TalonMotor;

public class SwerveModule {
    private final SparkMotor steerMotor;
    private final TalonMotor driveMotor;
    private final CANcoder cancoder;
    private final double absoluteOffset;
    public String name;

    public SwerveModule(ModuleConfig configs) {
        steerMotor = new SparkMotor(configs.steerConfig);
        driveMotor = new TalonMotor(configs.driveConfig);
        cancoder = new CANcoder(configs.CANcoderID);
        absoluteOffset = configs.CANcoderOffset; 
        name = configs.name;
    }


    public void setSteerDuty(double duty) {
        steerMotor.setDuty(duty);
    }

    public void setDriveDuty(double duty) {
        driveMotor.setDuty(duty);
    }

    public void setSteerPower(double power) {
        steerMotor.set(power);
    }

    public void setDrivePower(double power) {
        driveMotor.set(power);
    }

    public void setSteerVelocity(double velocity) {
        steerMotor.setVelocity(velocity);
    }

    public void setDriveVelocity(double velocity) {
        driveMotor.setVelocity(velocity);
    }

    public void setSteerPosition(double positionDegrees) {
        steerMotor.setPositionVoltage(positionDegrees);
    }

    public void setDriverPosition(double positionMeters) {
        driveMotor.setPositionVoltage(positionMeters);
    }

    public double getAbsoluteAngle() {
        return (cancoder.getAbsolutePosition().getValueAsDouble() * 360.0) - absoluteOffset;
    }

    public double getSteerPosition() {
        return steerMotor.getCurrentPosition();
    }

    public double getDriverPosition() {
        return driveMotor.getCurrentPosition();
    }

    public Rotation2d getSteerRotation() {
        return Rotation2d.fromDegrees(getSteerPosition());
    }

    public double getSteerVelocity() {
        return steerMotor.getCurrentVelocity();
    }

    public double getDriveVelocity() {
        return driveMotor.getCurrentVelocity();
    }

    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(
            getDriverPosition(),
            getSteerRotation()
        );
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), getSteerRotation());
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        SwerveModuleState optimized = optimize(desiredState, getSteerPosition());
        setSteerPosition(optimized.angle.getDegrees());
        setDriveVelocity(optimized.speedMetersPerSecond);
    }

    public void stop() {
        steerMotor.stopMotor();
        driveMotor.stopMotor();
    }

    public void resetToAbsolute() {
        double absAngle = getAbsoluteAngle();
        steerMotor.setPositionVoltage(absAngle);
    }

    private SwerveModuleState optimize(SwerveModuleState state, double currentAngleDegrees) {
        double delta = state.angle.getDegrees() - currentAngleDegrees;
        if (Math.abs(delta) > 90) {
            return new SwerveModuleState(
                -state.speedMetersPerSecond,
                state.angle.rotateBy(Rotation2d.fromDegrees(180))
            );
        }
        return state;
    }
}
