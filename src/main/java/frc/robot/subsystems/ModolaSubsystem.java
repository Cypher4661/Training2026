package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;

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
        return motorsteer.getEncoder().getPosition() / Constants.FirstSubsystemsConstants.GearRatiosteer * 360;
    }

    public double getPositionDrive(){
        return motordrive.getEncoder().getPosition() / Constants.FirstSubsystemsConstants.GearRatiodrive * 360;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("PositionSteer", getPositionSteer());
        SmartDashboard.putNumber("PositionDrive", getPositionDrive());
    }
    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("PositionSteer", this::getPositionSteer, null);
        builder.addDoubleProperty("PositinDrive", this::getPositionDrive, null);
        builder.addDoubleProperty("powerSteer", this.motorsteer::getAppliedOutput, null);
        builder.addDoubleProperty("powerDrive", this.motordrive::getAppliedOutput, null);
    }
}
