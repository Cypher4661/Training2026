// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ChassisConstants;
import frc.robot.subsystems.Swerve.SwerveModule;

import static edu.wpi.first.units.Units.Rotation;
import static frc.robot.Constants.*;

import com.ctre.phoenix6.hardware.Pigeon2;

public class Chassis extends SubsystemBase {
  public SwerveDrivePoseEstimator poseEstimator;
  public final SwerveModule FL;
  public final SwerveModule FR;
  public final SwerveModule BR;
  public final SwerveModule BL;
  public final SwerveModule[] Modules = {
      FL = new SwerveModule(ChassisConstants.FL_CONFIG),
      FR = new SwerveModule(ChassisConstants.FR_CONFIG),
      BR = new SwerveModule(ChassisConstants.BR_CONFIG),
      BL = new SwerveModule(ChassisConstants.BL_CONFIG)
  };
  public final Pigeon2 gyro;
  public final SwerveDriveKinematics kinematicsFix;

  public Chassis() {
    super();
    gyro = new Pigeon2(ChassisConstants.GYRO_ID, ChassisConstants.GYRO_CAN_BUS);

        kinematicsFix = new SwerveDriveKinematics(new Translation2d[]{
          new Translation2d(0,0),
          new Translation2d(0,-0),
          new Translation2d(-0,0),
          new Translation2d(-0,-0)
        });
    
    SmartDashboard.putData("Gyro", gyro);
    Field2d field = new Field2d();
    SmartDashboard.putData("Field", field);
    SmartDashboard.putData("rsset gyro", new InstantCommand(() -> setYaw(Rotation2d.kZero)).ignoringDisable(true));
  }

  public SwerveModuleState[] getModuleStates() {
    return kinematicsFix.toSwerveModuleStates(getChassisSpeedsFieldRel());
  } 
public SwerveModuleState[] getModulePosition() {
    return new SwerveModuleState[]{
      FL.getState(),
      FR.getState(),
      BR.getState(),
      BL.getState()
    };
  }

  public ChassisSpeeds getChassisSpeedsFieldRel() {
    return kinematicsFix.toChassisSpeeds(getModuleStates());
  }



  public Rotation2d getGyroAngle() {
    return Rotation2d.fromDegrees(0);
  }

  public void setYaw(Rotation2d yaw) {
    gyro.setYaw(yaw.getDegrees());
  }

  public void StopChassis(){
    FL.stop();
    FR.stop();
    BL.stop();
    BR.stop();
  }

  public void setIdleMode(boolean isBrake) {
    for (SwerveModule module : Modules) {
      module.setIdleMode(isBrake);
    }
  }

  public void resetPose(Pose2d pose){
    poseEstimator.resetPose(pose);
  }

  public Pose2d getPose(){
    return poseEstimator.getEstimatedPosition();
  }

  // public Translation2d calculateVelocity(double wantedVX, double wantedVY, double currentVX, double currentVY){

    
  //   double deltaVX = wantedVX - currentVX;
  //   double deltaVY = wantedVY - currentVY;

  //   double distance = Math.sqrt(deltaVX * deltaVX + deltaVY * deltaVY);
  //   double maxSpeed = 3.0; // Maximum speed in m/s

  //   if (distance > maxSpeed) {
  //     double scaleFactor = maxSpeed / distance;
  //     deltaVX *= scaleFactor;
  //     deltaVY *= scaleFactor;
  //   }

  //   return new Translation2d(currentVX + deltaVX, currentVY + deltaVY);
  // }


  public Translation2d calculateVelocityWithAccel(double wantedVx, double wantedVy){
    ChassisSpeeds currentSpeeds = getChassisSpeedsFieldRel();
    


  }
  public void setVelocityWithAccel(ChassisSpeeds wantedSpeeds){
    ChassisSpeeds currentSpeeds = getChassisSpeedsFieldRel();
    Translation2d limitedVelocitiesVector = calculateVelocity(wantedSpeeds.vxMetersPerSecond, wantedSpeeds.vyMetersPerSecond, currentSpeeds.vxMetersPerSecond, currentSpeeds.vyMetersPerSecond);
    ChassisSpeeds limitedVelocities = new ChassisSpeeds(limitedVelocitiesVector.getX(), limitedVelocitiesVector.getY(), wantedSpeeds.omegaRadiansPerSecond);
    Translation2d lastWantedSpeeds = limitedVelocitiesVector;
    setVelocities(limitedVelocities);
  }

  

  public void setVelocities(ChassisSpeeds speeds) {
    speeds = ChassisSpeeds.fromFieldRelativeSpeeds(speeds, getGyroAngle());
    SwerveModuleState[] states = kinematicsFix.toSwerveModuleStates(speeds);
    setModuleStates(states);
  }


  @Override
  public void periodic() {
    Rotation2d gyroAngle = getGyroAngle();
    poseEstimator = new SwerveDrivePoseEstimator(kinematicsFix, getGyroAngle(), getModulePosition(), new Pose2d(null, null, null));
  }
}
