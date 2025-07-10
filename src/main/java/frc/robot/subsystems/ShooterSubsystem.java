package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private double currentPosition = 0;
    private double currentVelocity = 0;
    public void setMotorOutput(double output) {
        // סימולציה – עדכון מהירות ומיקום
        currentVelocity = output * 100; // 100 = מקס מהירות תיאורטית
        currentPosition += currentVelocity * 0.02; // הנחה של 20ms per frame
    }

    public double getPosition() {
        return currentPosition;
    }

    public double getVelocity() {
        return currentVelocity;
    }

    public void reset() {
        currentPosition = 0;
        currentVelocity = 0;
    }

}
