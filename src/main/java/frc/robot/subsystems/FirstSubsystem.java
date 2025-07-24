package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.Utils;
import frc.robot.commands.GoToAnglePID;

import static edu.wpi.first.units.Units.Degrees;
import static frc.robot.Constants.FirstSubsystem.*;

public class FirstSubsystem extends SubsystemBase {

    private SparkMax motor;
    private TalonFX talonMotor;
    SimpleMotorFeedforward ff = new SimpleMotorFeedforward(0.3, 0.05,4 );
    

    public FirstSubsystem() {
        super();
        configureMotor();
        configureTalonMotor();
        SmartDashboard.putData("FirstSubsystem", this);
        SmartDashboard.putData("follow", new GoToAnglePID(this));
        SmartDashboard.putNumber("TargetPower", 0);
        SmartDashboard.putData("Manual Power", new RunCommand(()->setPower(SmartDashboard.getNumber("TargetPower", 0)), this));
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

    private void configureTalonMotor() {
        talonMotor = new TalonFX(MotorId, "rio");
        TalonFXConfiguration configuration = new TalonFXConfiguration();
        configuration.CurrentLimits.SupplyCurrentLimit = MaxAmper;
        configuration.CurrentLimits.withStatorCurrentLimit(MaxAmper).withSupplyCurrentLimitEnable(true);
        configuration.MotorOutput.withInverted(Inverted ? InvertedValue.CounterClockwise_Positive : InvertedValue.Clockwise_Positive).withNeutralMode(NeutralModeValue.Brake).withPeakForwardDutyCycle(MaxVolt/12).withPeakReverseDutyCycle(-MaxVolt/12);
        talonMotor.getConfigurator().apply(configuration);
    }

    public void setPower(double power) {
        motor.set(MathUtil.clamp(power, -MaxDuty, MaxDuty));
        SmartDashboard.putNumber("Motor Power", power);
    }

    /**
     * 
     * @return mechanism position in degrees
     */
    public double getPosition() {
        return motor.getEncoder().getPosition() / GearRatio * 360;
    }

    public double getAngleDegrees() {
        return MathUtil.inputModulus(getPosition(), -180, 180);
    }

    public double getTalonPosition() {
        return talonMotor.getPosition().getValue().in(Degrees) / GearRatio;
    }


    public void stop() {
        setPower(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Angle", getPosition());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Position", this::getPosition, null);
        builder.addDoubleProperty("power", this.motor::getAppliedOutput, null);
    }

}
