package frc.robot;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.utils.SparkMotor;
import frc.robot.utils.TalonMotor;


public class SwerveModule {
    public final SparkMotor steerMotor;
    public final TalonMotor driveMotor;
    public final CANcoder canCoder;
    private void calibrateSteer() {
        double absolotAngel = getAbseloteAngele() - Constants.MyFirstSubsystem.CANcoderOffset;
        SteerMoter.getEncoder().setPosition(absolotAngel * Constants.MyFirstSubsystem.SteerMoterRatio / 360);
    }
    

    public SwerveModule(ModuleConfig config) {
        this.steerMotor = new SparkMotor(config.steerConfig);
        this.driveMotor = new TalonMotor(config.driveConfig);
        this.canCoder = new CANcoder(config.CANcoderId);

    }
    //set
    public void setSteerDuty(double dutyCycle) {
        steerMotor.setDuty(dutyCycle);
        
    }
    public void setSteerVelocity(double velocity) {
        steerMotor.setVelocity(velocity);
    }
    public void setSteerPosition(double position) {
        steerMotor.setPositionVoltage(position);
    }
    //get
    public double getSteerPosition() {
        return steerMotor.getCurrentPosition();
    }
    public double getSteerVelocity() {
        return steerMotor.getCurrentVelocity();
    }
    public double getSteerDuty() {
        return steerMotor.getCurrentVelocity();
    }
    //set
    public void setDriveDuty(double dutyCycle) {
        driveMotor.setDuty(dutyCycle);
    }
    public void setDriveVelocity(double velocity) {
        driveMotor.setVelocity(velocity);
    }
    public void setDrivePosition(double position) {
        driveMotor.setPositionVoltage(position);
    }
    //get
    public double getDrivePosition() {
        return driveMotor.getCurrentPosition();
    }
    public double getDriveVelocity() {
        return driveMotor.getCurrentVelocity();
    }
    public double getDriveDuty() {
        return driveMotor.getCurrentVelocity();
    }
    //cancoder
    public double getCanCoderPosition() {
        return canCoder.getAbsolutePosition().getValueAsDouble() * 360;
    }
    //state
    public void setState(SwerveModuleState state) {
        setSteerPosition(state.angle.getDegrees());
        setDriveVelocity(state.speedMetersPerSecond);
    }











}
