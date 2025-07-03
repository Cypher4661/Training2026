package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FirstSubsystems extends SubsystemBase{
    private final SparkMax motor;

    public FirstSubsystems(){
        super();
        motor = new SparkMax(Constants.FirstSubsystemsConstants.MotorId, MotorType.kBrushless);
        motor.setInverted(Constants.FirstSubsystemsConstants.MotorInverted);
    }
    public void setPower(double power){
        motor.set(power);
    }
    public void stop(){
        setPower(0);
    }
}
