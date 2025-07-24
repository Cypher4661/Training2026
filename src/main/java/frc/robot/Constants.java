package frc.robot;

import com.ctre.phoenix6.CANBus;
import frc.robot.utils.SparkConfig;
import frc.robot.utils.TalonConfig;

public final class Constants {
  public static class Example {
    public static final TalonConfig TALON_CONFIG = new TalonConfig(7,new CANBus("rio"), "talon example motor")
          .withBrake(true)
          .withCurrent(20,20,0)
          .withInvert(true)
          .withMeterMotor(12.7, 4*0.0254)
          .withMotionMagic(3, 6, 10)
          .withPID(1, 0, 0, 0.12, 3.7, 1.2, 0)
          .withRampTime(0.3)
          .withVolts(6);
    public static final SparkConfig SPARKMOTOR_CONFIG = new SparkConfig(8, "spark example motor")
        .withBrake(true)
        .withCurrent(20)
        .withInvert(false)
        .withPID(2,   0.2,   0, 0.1)
        .withRadiansMotor(8.4)
        .withVelocity(20, 0, 30)
        .withRampTime(0.2)
        .withVolts(8);
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
