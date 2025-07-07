package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class shiraSubSystem extends SubsystemBase{
    private SparkMax motor;

    public shiraSubSystem(){
        motor = new SparkMax(Constants.shiraConstants.motorID, MotorType.kBrushless);
    }
    public void setPower(double power){
        motor.set(power);
    }
    
    public double getPosition(){
        return motor.getEncoder().getPosition() / Constants.shiraConstants.ratio * 360;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("position", getPosition());
    }
}
