public final class Constants {

  public static class DriveMotor {
      public static final int ID = 1;
      public static final boolean INVERTED = false;
      public static final double kS = 0.2;
      public static final double kV = 2.3;
      public static final double kA = 0.1;
      public static final double kP = 0.1;
      public static final double kI = 0;
      public static final double kD = 0.01;
  }

  public static class AngleMotor {
      public static final int ID = 2;
      public static final boolean INVERTED = true;
      public static final double kS = 0.15;
      public static final double kV = 1.9;
      public static final double kA = 0.08;
      public static final double kG = 0.3;
      public static final double kP = 0.2;
      public static final double kI = 0;
      public static final double kD = 0.02;
  }
}
