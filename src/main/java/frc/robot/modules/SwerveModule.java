// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.modules;

public class SwerveModule {
    private final int driveMotorId;
    private final int steerMotorId;
    private final int canCoderId;

    public SwerveModule(int driveMotorId, int steerMotorId, int canCoderId) {
        this.driveMotorId = driveMotorId;
        this.steerMotorId = steerMotorId;
        this.canCoderId = canCoderId;
    }

    public int getDriveMotorId() {
        return driveMotorId;
    }

    public int getSteerMotorId() {
        return steerMotorId;
    }

    public int getCanCoderId() {
        return canCoderId;
    }
}