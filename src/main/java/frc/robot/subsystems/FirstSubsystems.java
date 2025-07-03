package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FirstSubsystems extends SubsystemBase{
    private final TalonFX motor;

    public FirstSubsystems(){
        super();
        motor = new TalonFX(Constants.FirstSubsystems.MotorId,"one");
        motor.setInverted(Constants.FirstSubsystems.MotorInverted);
    }
    public void setPower(double power){
        motor.set(power);
    }
    public void stop(){
        setPower(0);
    }
}
