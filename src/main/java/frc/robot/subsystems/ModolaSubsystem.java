package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ModolaSubsystem extends SubsystemBase{
    private final SparkMax motorsteer;
    private final SparkMax motordrive;
    private final CANcoder eNcoder;

    public ModolaSubsystem(){
        motorsteer = new SparkMax(Constants.FirstSubsystemsConstants.MotorIdsteer, MotorType.kBrushless);
        motordrive = new SparkMax(Constants.FirstSubsystemsConstants.MotorIddrive, MotorType.kBrushless);
        eNcoder = new CANcoder(Constants.FirstSubsystemsConstants.CANcoderID);
        var cfgSteer = new SparkMaxConfig();
        cfgSteer.inverted(true);
        motorsteer.configure(cfgSteer,ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        var cfgDrive = new SparkMaxConfig();
        cfgDrive.inverted(true);
        motordrive.configure(cfgDrive,ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        calibrateSteer();
        SmartDashboard.putData("Modil1", this);

    }

    private void calibrateSteer() {
        double angle = CANcoder() - Constants.FirstSubsystemsConstants.CancoderOffset;
        motorsteer.getEncoder().setPosition(angle/360*Constants.FirstSubsystemsConstants.GearRatiosteer);
    }
    
    public void setPower(double powersteer, double powerdrive){
        motorsteer.set(powersteer);
        motordrive.set(powerdrive);
    }

    public double getPowerSteer(){
        return motorsteer.getAppliedOutput();
    }
    
    public double getPowerDrive(){
        return motordrive.getAppliedOutput();
    }
    
    public void stop(){
        setPower(0,0);
    }
    
    public double getPositionSteer(){
        double angle =  motorsteer.getEncoder().getPosition() / Constants.FirstSubsystemsConstants.GearRatiosteer * 360;
        return MathUtil.inputModulus(angle, -180 , 180);
    }

    public double getPositionDrive(){
        return motordrive.getEncoder().getPosition() / Constants.FirstSubsystemsConstants.GearRatiodrive * 360;
    }

    public double CANcoder(){
        return eNcoder.getAbsolutePosition().getValueAsDouble()*360;
    }

    @Override
    public void periodic() {
       // SmartDashboard.putNumber("PositionSteer", getPositionSteer());
       // SmartDashboard.putNumber("PositionDrive", getPositionDrive());
       // SmartDashboard.putNumber("SteerPower",getPowerSteer());
       // SmartDashboard.putNumber("SteerPower",getPowerDrive());
    }
    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("PositionSteer", this::getPositionSteer, null);
        builder.addDoubleProperty("PositinDrive", this::getPositionDrive, null);
        builder.addDoubleProperty("powerSteer", this.motorsteer::getAppliedOutput, null);
        builder.addDoubleProperty("powerDrive", this.motordrive::getAppliedOutput, null);
        builder.addDoubleProperty("Position CAN", this::CANcoder, null);
    }
}
