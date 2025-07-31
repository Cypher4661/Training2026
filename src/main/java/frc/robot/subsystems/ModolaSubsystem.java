package frc.robot.subsystems;

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
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ModolaSubsystem extends SubsystemBase{
    private final SparkMax SteerMotor;
    private final SparkMax DriveMotor;
    private final CANcoder eNcoder;
    private final SimpleMotorFeedforward SteerFF = new SimpleMotorFeedforward(0.0075, 0.000625);
    private final PIDController SteerPID = new PIDController(0.001, 0, 0);
    private final SimpleMotorFeedforward DriveFF = new SimpleMotorFeedforward(1.0/150.0, 2.0/9.0);
    private final PIDController DrivePID = new PIDController(0.001, 0, 0);
    SparkMaxConfig cfgSteer;
    SparkMaxConfig cfgDrive;

    public ModolaSubsystem(){
        SteerMotor = new SparkMax(Constants.ModolaConstants.MotorIdsteer, MotorType.kBrushless);
        DriveMotor = new SparkMax(Constants.ModolaConstants.MotorIddrive, MotorType.kBrushless);
        eNcoder = new CANcoder(Constants.ModolaConstants.CANcoderID);
        cfgSteer = new SparkMaxConfig();
        cfgSteer.inverted(true);
        cfgSteer.idleMode(IdleMode.kBrake);
        SteerMotor.configure(cfgSteer,ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        cfgDrive = new SparkMaxConfig();
        cfgDrive.inverted(true);
        cfgDrive.idleMode(IdleMode.kBrake);
        DriveMotor.configure(cfgDrive,ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        calibrateSteer();
        SmartDashboard.putData("Modil1", this);

    }

    public void setNeutralMode(boolean isBrake){
        IdleMode mode = isBrake ? IdleMode.kBrake : IdleMode.kCoast;
        cfgSteer.idleMode(mode);
        SteerMotor.configure(cfgSteer,ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        cfgDrive.idleMode(mode);
        DriveMotor.configure(cfgDrive,ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    private void calibrateSteer() {
        double angle = getCANcoderAbseloteAngle() - Constants.ModolaConstants.CancoderOffset;
        SteerMotor.getEncoder().setPosition(angle/360*Constants.ModolaConstants.GearRatiosteer);
    }
    
    public void setSteerPower(double powersteer){
        SteerMotor.set(powersteer);
    }

    public void setDrivePower(double powerdrive){
        DriveMotor.set(powerdrive);
    }

    public double getPowerSteer(){
        return SteerMotor.getAppliedOutput();
    }
    
    public double getPowerDrive(){
        return DriveMotor.getAppliedOutput();
    }
    
    public void stop(){
       SteerMotor.stopMotor();
       DriveMotor.stopMotor();
    }
    
    public double getSteerPosition(){
        double angle =  SteerMotor.getEncoder().getPosition() / Constants.ModolaConstants.GearRatiosteer * 360;
        return MathUtil.inputModulus(angle, -180 , 180);
    }

    public double getDrivePosition(){
        double position = DriveMotor.getEncoder().getPosition() / Constants.ModolaConstants.GearRatiodrive * 360;
        return MathUtil.inputModulus(position, -180, 180);
    }

    public double getSteerVelocity(){
        return SteerMotor.getEncoder().getVelocity() / Constants.ModolaConstants.GearRatiosteer *360/60;
    }

    public double getDriveVelocity(){
        return DriveMotor.getEncoder().getVelocity() / Constants.ModolaConstants.GearRatiodrive / 60 * Math.PI*Constants.ModolaConstants.Diameter;
    }

    public double getCANcoderAbseloteAngle(){
        return eNcoder.getAbsolutePosition().getValueAsDouble()*360;
    }

    public void setSteerVelocity(double velocity){
        setSteerPower(SteerFF.calculate(velocity) + SteerPID.calculate(velocity));
    }

    public void setDriveVelocity(double velocity){
        setDrivePower(DriveFF.calculate(velocity) + DrivePID.calculate(velocity));
    }

    public void setSteerPosition(double positionDegrees){
        //SteerMotor.setPositionVoltage(positionDegrees);
    }

    public Rotation2d getSteerRotation() {
        return new Rotation2d(getSteerPosition());
    }


    public void setState(SwerveModuleState state){
        double wantedAngle = state.angle.getDegrees();
        double diff = wantedAngle - getSteerPosition();
        double vel = state.speedMetersPerSecond;
        diff = MathUtil.angleModulus(diff);
        if(diff>0.5*Math.PI){
            vel = -vel;
            diff = diff-Math.PI;}
        else if(diff< -0.5*Math.PI){
            vel = -vel;
            diff = diff + Math.PI;
        }
        setSteerPosition(getSteerPosition()+diff);
        setDriveVelocity(vel);
    }

    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(getDrivePosition(),Rotation2d.fromRadians(getSteerPosition()));
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), getSteerRotation());
    }


    @Override
    public void periodic() {
       
    }
    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Steer Position", this::getSteerPosition, null);
        builder.addDoubleProperty("Drive Position", this::getDrivePosition, null);
        builder.addDoubleProperty("Steer Velocity", this::getSteerVelocity, null);
        builder.addDoubleProperty("Drive Velocity", this::getDriveVelocity, null);
        builder.addDoubleProperty("Steer Power", this::getPowerSteer, null);
        builder.addDoubleProperty("Drive Drive", this::getPowerDrive, null);
        builder.addDoubleProperty("Position CAN", this::getCANcoderAbseloteAngle, null);
    }
}
