package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FirstSubsystem.*;

public class FirstSubsystem extends SubsystemBase {

    private SparkMax motor;

    public FirstSubsystem() {
        super();
        configureMotor();
    }

    private void configureMotor() {
        motor = new SparkMax(MotorId, MotorType.kBrushless);
        SparkMaxConfig config = new SparkMaxConfig();
        config.inverted(Inverted);
        config.idleMode(IdleMode.kBrake);
        config.openLoopRampRate(RampRate);
        config.smartCurrentLimit(MaxAmper);
        config.voltageCompensation(MaxVolt);
        motor.configure(config,ResetMode.kResetSafeParameters , PersistMode.kNoPersistParameters);
    }

    public void setPower(double power) {
        motor.set(power);
    }

    /**
     * 
     * @return mechanism position in degrees
     */
    public double getPosition() {
        return motor.getEncoder().getPosition() * 360 / GearRatio;
    }


    public void stop() {
        setPower(0);
    }

}
