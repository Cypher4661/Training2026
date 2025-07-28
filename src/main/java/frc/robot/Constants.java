package frc.robot;

public final class Constants {

  public static class DriveMotor {
    public static final int ID = 2;
    public static final boolean INVERTED = false;
    public static final double kS = 0.2;
    public static final double kV = 2.3;
    public static final double kA = 0.1;
    public static final double kP = 0.1;
    public static final double kI = 0;
    public static final double kD = 0.01;

  }

  public static final int cancoderId = 9;

  public static class steerMotor {
    public static final int ID = 1;
    public static final boolean INVERTED = true;
    public static final double kS = 0.15;
    public static final double kV = 1.9;
    public static final double kA = 0.08;
    public static final double kG = 0.3;
    public static final double kP = 0.2;
    public static final double kI = 0;
    public static final double kD = 0.02;

  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class Arm {
    public static final int MOTOR_PORT = 4;
    public static final boolean INVERTED = false;
    public static final boolean BRAKE_MODE = true;
    public static final double RAMP_TIME = 0.5;
    public static final double GEAR_RATIO = 100.0;

    // PID
    public static final double kP = 0.1;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    // FeedForward (לעדכון אחרי SysId)
    public static final double kS = 0.2;
    public static final double kV = 1.2;
    public static final double kA = 0.0;
    public static final double kG = 0.0;
  }
}
