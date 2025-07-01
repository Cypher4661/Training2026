package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FirstSubsystem extends SubsystemBase {

    private SparkMax motor;

    public FirstSubsystem() {
        super();
        motor = new SparkMax(Constants.FirstSubsystem.MotorId, MotorType.kBrushless);
//        motor.setInverted(Constants.FirstSubsystem.Inverted);
    }

    public void setPower(double power) {
        motor.set(power);
    }

    /**
     * 
     * @return mechanism position in degrees
     */
    public double getPosition() {
        return motor.getEncoder().getPosition() * 360 / Constants.FirstSubsystem.GearRatio;
    }


    public void stop() {
        setPower(0);
    }

}
