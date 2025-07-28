package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import com.demacia.elastilog.ElastiLog;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.demacia.motor.SparkConfig;
//import com.demacia.motor.SparkMotor;
import frc.robot.Constants;
import frc.robot.utils.SparkMotor; // או מחלקה ספציפית מתוך utils אם נדרש
import frc.robot.utils.SparkConfig; 
import edu.wpi.first.math.MathUtil;

public class MotorSubsystem extends SubsystemBase {
    private final SparkMotor armMotor;

    public MotorSubsystem() {
        SparkConfig config = new SparkConfig(Constants.Arm.MOTOR_PORT, "ArmMotor")
                .withBrake(Constants.Arm.BRAKE_MODE)
                .withRampTime(Constants.Arm.RAMP_TIME)
                .withInvert(Constants.Arm.INVERTED)
                .withMotorRatio(Constants.Arm.GEAR_RATIO)
                .withPID(
                    Constants.Arm.kP, Constants.Arm.kI, Constants.Arm.kD,
                    Constants.Arm.kS, Constants.Arm.kV, Constants.Arm.kA, Constants.Arm.kG
                );

        armMotor = new SparkMotor(config);
       // ElasticLog.register(armMotor); זה לא עובד
        SmartDashboard.putData("armMotor", armMotor);// מנסה את זה במקום

    }

    public void setPower(double power) {
        double limitedPower = MathUtil.clamp(power, -0.5, 0.5); // הגבלה ל ±0.5
        armMotor.setDuty(limitedPower);
    }

    public void setVelocity(double velocity) {
        armMotor.setVelocity(velocity);
    }

    public void setPosition(double position) { // כדי להוסיף פיד פורוורד צריך להוסיף משתנה שני בתוך הסוגריים
        armMotor.setPositionVoltage(position);
    }

    public double getPosition() {
        return armMotor.getCurrentPosition();
    }

    public double getVelocity() {
        return armMotor.getCurrentVelocity();
    }
}
