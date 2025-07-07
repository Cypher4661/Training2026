package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class arm extends SubsystemBase {

    private final SparkMax motor;

    // Constructor
    public arm() {
        motor = new SparkMax(Constants.armConstants.MotorID, MotorType.kBrushless);
        motor.setInverted(Constants.armConstants.MotorInverted);
        SmartDashboard.putData(this);
    }

    public double getPosition() {
        return motor.getEncoder().getPosition()*Constants.armConstants.gearRatio*360;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("position", this::getPosition, null);
    }


    // Simple power -1 to 1
    public void setPower(double power) {
        motor.set(power);
    }

    // stop
    public void stop() {
        setPower(0);
    }

    public void periodic() {
        SmartDashboard.putNumber("position",getPosition());

    }
}
