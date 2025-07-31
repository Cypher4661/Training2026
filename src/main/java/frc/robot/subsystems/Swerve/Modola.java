package frc.robot.subsystems.Swerve;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.SparkConfig;
import frc.robot.utils.SparkMotor;
import frc.robot.utils.TalonMotor;

public class Modola implements Sendable {
    private final SparkMotor SteerMotor;
    private final TalonMotor DriveMotor;
    private final CANcoder eNcoder;
    private final SimpleMotorFeedforward SteerFF = new SimpleMotorFeedforward(0.0075, 0.000625);
    private final PIDController SteerPID = new PIDController(0.001, 0, 0);
    private final SimpleMotorFeedforward DriveFF = new SimpleMotorFeedforward(1.0 / 150.0, 2.0 / 9.0);
    private final PIDController DrivePID = new PIDController(0.001, 0, 0);
    SparkMaxConfig cfgSteer;
    SparkMaxConfig cfgDrive;

    public Modola(ModuleConfig config) {
        SteerMotor = new SparkMotor(config.SteerConfig);
        DriveMotor = new TalonMotor(config.DriveConfig);
        eNcoder = new CANcoder(Constants.ModolaConstants.CANcoderID);
        calibrateSteer();
        SmartDashboard.putData("Modil1", this);
    }

    public void setIdleMode(boolean isBrake) {
        SteerMotor.setNeutralMode(isBrake);
        DriveMotor.setNeutralMode(isBrake);
    }

    private void calibrateSteer() {
        double angle = getCANcoderAbseloteAngle() - Constants.ModolaConstants.CancoderOffset;
        SteerMotor.getEncoder().setPosition(angle / 360 * Constants.ModolaConstants.GearRatiosteer);
    }

    public void setSteerPower(double powersteer) {
        SteerMotor.set(powersteer);
    }

    public void setDrivePower(double powerdrive) {
        DriveMotor.set(powerdrive);
    }

    public double getPowerSteer() {
        return SteerMotor.getAppliedOutput();
    }

    public double getPowerDrive() {
        return DriveMotor.get();
    }

    public void stop() {
        SteerMotor.stopMotor();
        DriveMotor.stopMotor();
    }

    public double getSteerPosition() {
        double angle = SteerMotor.getEncoder().getPosition() / Constants.ModolaConstants.GearRatiosteer * 360;
        return MathUtil.inputModulus(angle, -180, 180);
    }

    public double getDrivePosition() {
        double position = DriveMotor.getCurrentPosition();
        return MathUtil.inputModulus(position, -180, 180);
    }

    public double getSteerVelocity() {
        return SteerMotor.getCurrentVelocity();
    }

    public double getDriveVelocity() {
        return DriveMotor.getCurrentVelocity();

    }

    public double getCANcoderAbseloteAngle() {
        return eNcoder.getAbsolutePosition().getValueAsDouble() * 360;
    }

    public void setSteerVelocity(double velocity) {
        setSteerPower(SteerFF.calculate(velocity) + SteerPID.calculate(velocity));
    }

    public void setDriveVelocity(double velocity) {
        setDrivePower(DriveFF.calculate(velocity) + DrivePID.calculate(velocity));
    }

    public void setSteerPosition(double positionDegrees) {
        SteerMotor.setPositionVoltage(positionDegrees);
    }

    public Rotation2d getSteerRotation() {
        return new Rotation2d(getSteerPosition());
    }

    public void setState(SwerveModuleState state) {
        double wantedAngle = state.angle.getDegrees();
        double diff = wantedAngle - getSteerPosition();
        double vel = state.speedMetersPerSecond;
        diff = MathUtil.angleModulus(diff);
        if (diff > 0.5 * Math.PI) {
            vel = -vel;
            diff = diff - Math.PI;
        } else if (diff < -0.5 * Math.PI) {
            vel = -vel;
            diff = diff + Math.PI;
        }
        setSteerPosition(getSteerPosition() + diff);
        setDriveVelocity(vel);
    }

    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(getDrivePosition(), Rotation2d.fromRadians(getSteerPosition()));
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), getSteerRotation());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Steer Position", this::getSteerPosition, null);
        builder.addDoubleProperty("Drive Position", this::getDrivePosition, null);
        builder.addDoubleProperty("Steer Velocity", this::getSteerVelocity, null);
        builder.addDoubleProperty("Drive Velocity", this::getDriveVelocity, null);
        builder.addDoubleProperty("Steer Power", this::getPowerSteer, null);
        builder.addDoubleProperty("Drive Drive", this::getPowerDrive, null);
        builder.addDoubleProperty("Position CAN", this::getCANcoderAbseloteAngle, null);

    }
}
