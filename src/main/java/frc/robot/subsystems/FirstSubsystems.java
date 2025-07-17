package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.goToAngle;
import frc.robot.commands.goToPosition;

public class FirstSubsystems extends SubsystemBase{
    private final SparkMax motor;
    
    public FirstSubsystems(){
        super();
        motor = new SparkMax(Constants.FirstSubsystemsConstants.MotorId11, MotorType.kBrushless);
        motor.setInverted(Constants.FirstSubsystemsConstants.MotorInverted);
        SmartDashboard.putData("SubSystem", this);
        SmartDashboard.putData("cmd", new goToPosition(90, this));
        SmartDashboard.putData("cmd2", getCmd());
        SmartDashboard.putData("Start", new goToAngle(this));
        
    }

    private Command getCmd() {
        return new goToPosition(90,this).
        andThen(new WaitCommand(5),
            new goToPosition(135,this),
            new WaitCommand(2),
            new goToPosition(0, this));
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
        builder.addDoubleProperty("power", this.motor::getAppliedOutput, null);
    }
}


