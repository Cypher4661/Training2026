public class AngleSubsystem extends SubsystemBase {

    private final CANSparkMax angleMotor = new CANSparkMax(Constants.AngleMotor.ID, MotorType.kBrushless);
    private final RelativeEncoder angleEncoder = angleMotor.getEncoder();
    private final PIDController pid = new PIDController(Constants.AngleMotor.kP, Constants.AngleMotor.kI, Constants.AngleMotor.kD);
    private final ArmFeedforward ff = new ArmFeedforward(Constants.AngleMotor.kS, Constants.AngleMotor.kG, Constants.AngleMotor.kV, Constants.AngleMotor.kA);

    public AngleSubsystem() {
        angleMotor.restoreFactoryDefaults();
        angleMotor.setInverted(Constants.AngleMotor.INVERTED);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Angle/Position", getPosition());
        SmartDashboard.putNumber("Angle/PID Error", pid.getPositionError());
        SmartDashboard.putNumber("Angle/Motor Output (%)", angleMotor.getAppliedOutput());
    }

    public void setAngle(double targetAngle) {
        double ffOutput = ff.calculate(Math.toRadians(targetAngle), 0);
        double pidOutput = pid.calculate(getPosition(), targetAngle);
        angleMotor.setVoltage(ffOutput + pidOutput);
    }

    public double getPosition() {
        return angleEncoder.getPosition(); // ודא תרגום נכון לזווית
    }

    public void stop() {
        angleMotor.stopMotor();
    }
}
