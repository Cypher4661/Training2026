package frc.robot.subsystems;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.SparkMotor;
import frc.robot.utils.TalonMotor;

public class MotorExampleSubsytem extends SubsystemBase {
    // Define the motor 
    TalonMotor talonMotor;
    SparkMotor sparkMotor;
    
    // Constructor
    public MotorExampleSubsytem() {
        super();
        talonMotor = new TalonMotor(Constants.Example.TALON_CONFIG);
        sparkMotor = new SparkMotor(Constants.Example.SPARKMOTOR_CONFIG);
        SmartDashboard.putNumber("Set Power to", 0);
        SmartDashboard.putData("set Talon", new RunCommand(()->setTalonPower(SmartDashboard.getNumber("Set Power to",0)),this));
        SmartDashboard.putData("set Spark", new RunCommand(()->setSparkPower(SmartDashboard.getNumber("Set Power to",0)),this));
        SmartDashboard.putData("stop", new InstantCommand(()->{setSparkPower(0); setTalonPower(0);},this));
    }

    // Method to set the motor speed
    public void setTalonPower(double power) {
        talonMotor.setDuty(power);
    }
    // Method to stop the motor
    public void stopTalon() {
        setTalonPower(0);
    }
    public void setTalonVelocity(double velocity) {
        talonMotor.setVelocity(velocity);
    }
    public void setTalonPosition(double position) {
        talonMotor.setPositionVoltage(position);
    }

    public void setSparkVelocity(double velocity) {
        sparkMotor.setVelocity(velocity);
    }
    public void setSparkPosition(double position) {
        sparkMotor.setPositionVoltage(position);
    }

    public void setSparkPower(double power) {
        System.out.println(" power = " + power);
        sparkMotor.setDuty(power);
    }
    // Method to stop the motor
    public void stopSpark() {
        setSparkPower(0);
    }


    public double getTalonPosition() {
        return talonMotor.getCurrentPosition();
    }
    public double getTalonVelocity() {
        return talonMotor.getCurrentVelocity();
    }
    public double getTalonVolts() {
        return talonMotor.getCurrentVoltage();
    }

    public double getSparkVelocity() {
        return sparkMotor.getCurrentVelocity();
    }
    public double getSparkPosition() {
        return sparkMotor.getCurrentPosition();
    }
    public double getSparkVolts() {
        return sparkMotor.getCurrentVoltage();
    }


}   