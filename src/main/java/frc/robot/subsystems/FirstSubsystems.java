package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FirstSubsystems extends SubsystemBase{
    private final SparkMax motor;

    public FirstSubsystems(){
        super();
        motor = new SparkMax(Constants.FirstSubsystemsConstants.MotorId, MotorType.kBrushless);
        motor.setInverted(Constants.FirstSubsystemsConstants.MotorInverted);
        SmartDashboard.putData("SubSystem", this);
    }
    public void setPower(double power){
        motor.set(power);
    }
    public void stop(){
        setPower(0);
    }
    public double getPosition(){
        return motor.getEncoder().getPosition() / Constants.FirstSubsystemsConstants.GearRatio * 360;
    }

    @Override
    public void periodic() {
        
        SmartDashboard.putNumber("Position", getPosition());
    }
    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("position", this::getPosition, null);
    }

}
