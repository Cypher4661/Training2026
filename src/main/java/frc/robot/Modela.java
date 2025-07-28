package frc.robot;

public class Modela {
    public final int DriveMoterId;
    public final int SteerMoterId;
    public final int CANcoderId;
    public final double DriveMoterRatio;
    public final double SteerMoterRatio;
    public final boolean DriveMoterInverted;
    public final boolean SteerMoterInverted;
    public final double diameter;
    public final double CANcoderOffset;

    public Modela(
        int driveMoterId, int steerMoterId, int canCoderId,
        double driveMoterRatio, double steerMoterRatio,
        boolean driveMoterInverted, boolean steerMoterInverted,
        double diameter, double canCoderOffset
    ) {
        this.DriveMoterId = driveMoterId;
        this.SteerMoterId = steerMoterId;
        this.CANcoderId = canCoderId;
        this.DriveMoterRatio = driveMoterRatio;
        this.SteerMoterRatio = steerMoterRatio;
        this.DriveMoterInverted = driveMoterInverted;
        this.SteerMoterInverted = steerMoterInverted;
        this.diameter = diameter;
        this.CANcoderOffset = canCoderOffset;
    }
}
