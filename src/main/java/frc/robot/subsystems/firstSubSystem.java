


// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.ed


package frc.robot.subsystems;


import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class firstSubSystem extends SubsystemBase {
 private final SparkMax steerMotor;
 private final SparkMax driveMotor;


 private final CANcoder concoderrr;




 private final SimpleMotorFeedforward feedforwardSteer = new SimpleMotorFeedforward(0.1,0.12,0.02);
 private final SimpleMotorFeedforward feedforwardDrive = new SimpleMotorFeedforward(0.1,0.12,0.02);


 private PIDController pidSteer = new PIDController(0.005, 0.001, 0.0005);
 private PIDController pidDrive = new PIDController(0.005, 0.001, 0.0005);


 private double kPSteer = 3;
 private double kPDrive = 3;


 public firstSubSystem() {
   super();
   steerMotor = new SparkMax(Constants.firstSubSystem.MotorIDSteer, MotorType.kBrushless);
   driveMotor = new SparkMax(Constants.firstSubSystem.MotorIDDrive, MotorType.kBrushless);


   concoderrr = new CANcoder(Constants.firstSubSystem.CancoderID);


   var confige = new SparkMaxConfig();
   confige.inverted(Constants.firstSubSystem.MotorInverted);


   steerMotor.configure(confige,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  


   pidSteer.setTolerance(2.0);
   pidDrive.setTolerance(2.0);


   pidSteer.setIntegratorRange(-5, 5);
 }


  public double getPosSteer(){
   double steerAngle = steerMotor.getEncoder().getPosition() / Constants.firstSubSystem.GearRatioSteer * 360;
   return MathUtil.inputModulus(steerAngle, -180, 180);


 }


 public double getPosDrive(){
   return driveMotor.getEncoder().getPosition() / Constants.firstSubSystem.GearRatioDrive * 360;
 }


 public double getSpeedSteer(){
   return steerMotor.getEncoder().getVelocity() / Constants.firstSubSystem.GearRatioSteer * 360 / 60; // Convert to RPM
 }


 public double getSpeedDrive(){
   return driveMotor.getEncoder().getVelocity() / Constants.firstSubSystem.GearRatioDrive * 360 / 60; // Convert to RPM
 }


 public void setPowerSteer(double power){
   steerMotor.set(power);
 }


 public void setPowerDrive(double power){
   driveMotor.set(power);
 }


 public void getToSpeedSteer(double targetSpeed){
   double currentSpeed = getSpeedSteer();
   double feedforwardOut = feedforwardSteer.calculateWithVelocities(currentSpeed, targetSpeed);
   double power = pidSteer.calculate(currentSpeed, targetSpeed) + feedforwardOut;
   setPowerSteer(power);
 }


 public void getToSpeedDrive(double targetSpeed){
   double currentSpeed = getSpeedDrive();
   double feedforwardOut = feedforwardDrive.calculateWithVelocities(currentSpeed, targetSpeed);
   double power = pidDrive.calculate(currentSpeed, targetSpeed) + feedforwardOut;
   setPowerDrive(power);
 }


 public void turnToAngleSteer(double angleSteer) {
   double currentAngle = getPosSteer();
   //double turnSpeed = turnController.calculate(currentAngle, angle);
   double turnSpeedSteer = (angleSteer - currentAngle) * kPSteer;
   setPowerSteer(turnSpeedSteer);
 }


 public void turnToAngleDrive(double angleDrive) {
   double currentAngle = getPosDrive();
   double turnSpeedDrive = (angleDrive - currentAngle) * kPDrive;
   setPowerSteer(turnSpeedDrive);
 }


 public void stopSteer(){
   setPowerSteer(0);
 }


 public void stopDrive(){
   setPowerDrive(0);
 }


 public double getCancoderPosition() {
   return concoderrr.getAbsolutePosition().getValueAsDouble()*360;
 }


 @Override
 public void periodic() {
   SmartDashboard.putNumber("mSpeed steer", getSpeedSteer());
   SmartDashboard.putNumber("mSpeed drive", getSpeedDrive());


   SmartDashboard.putNumber("mAngle steer", getPosSteer());
   SmartDashboard.putNumber("mAngle drive", getPosDrive());
    SmartDashboard.putNumber("steer power", steerMotor.getAppliedOutput());
   SmartDashboard.putNumber("drive power", driveMotor.getAppliedOutput());


   SmartDashboard.putNumber("cancoder angle", getCancoderPosition());
 }
}













