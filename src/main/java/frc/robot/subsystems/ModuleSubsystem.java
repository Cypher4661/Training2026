// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Modela;

public class ModuleSubsystem extends SubsystemBase {
  /** Creates a new FFsubsystem. */
  private final SparkMax DriveMoter1;
  private final SparkMax SteerMoter1;
  private final CANcoder canCoder1;
  private final SparkMax DriveMoter2;
  private final SparkMax SteerMoter2;
  private final CANcoder canCoder2;
  private final SparkMax DriveMoter3;
  private final SparkMax SteerMoter3;
  private final CANcoder canCoder3;
  private final SparkMax DriveMoter4;
  private final SparkMax SteerMoter4;
  private final CANcoder canCoder4;
  

  private final SimpleMotorFeedforward steerFF = new SimpleMotorFeedforward(0.0075,0.000625 );
  private final PIDController steerPID = new PIDController(0.001, 0, 0);
  private final SimpleMotorFeedforward driverFF = new SimpleMotorFeedforward(1.0/150.0,2.0/9.0);
  private final PIDController driverPID = new PIDController(0.001, 0, 0);



  public ModuleSubsystem() {
    super();
    //1
    var cfg_1_drive = new SparkMaxConfig();
    cfg_1_drive.inverted(Constants.modelas[0].DriveMoterInverted);
    var cfg_1_steer = new SparkMaxConfig();
    cfg_1_steer.inverted(Constants.modelas[0].SteerMoterInverted);
    //2
    var cfg_2_drive = new SparkMaxConfig();
    cfg_2_drive.inverted(Constants.modelas[1].DriveMoterInverted);
    var cfg_2_steer = new SparkMaxConfig();
    cfg_2_steer.inverted(Constants.modelas[1].SteerMoterInverted);
    //3
    var cfg_3_drive = new SparkMaxConfig();
    cfg_3_drive.inverted(Constants.modelas[2].DriveMoterInverted);
    var cfg_3_steer = new SparkMaxConfig();
    cfg_3_steer.inverted(Constants.modelas[2].SteerMoterInverted);
    //4
    var cfg_4_drive = new SparkMaxConfig();
    cfg_4_drive.inverted(Constants.modelas[3].DriveMoterInverted);
    var cfg_4_steer = new SparkMaxConfig();
    cfg_4_steer.inverted(Constants.modelas[3].SteerMoterInverted);
    //1
    DriveMoter1 = new SparkMax(Constants.modelas[0].DriveMoterId, MotorType.kBrushless);
    SteerMoter1 = new SparkMax(Constants.modelas[0].SteerMoterId, MotorType.kBrushless);
    canCoder1 = new CANcoder(Constants.modelas[0].CANcoderId);
    SteerMoter1.configure(cfg_1_steer, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    DriveMoter1.configure(cfg_1_drive, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    //2
    DriveMoter2 = new SparkMax(Constants.modelas[1].DriveMoterId, MotorType.kBrushless);
    SteerMoter2 = new SparkMax(Constants.modelas[1].SteerMoterId, MotorType.kBrushless);
    canCoder2 = new CANcoder(Constants.modelas[1].CANcoderId);
    SteerMoter2.configure(cfg_2_steer, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    DriveMoter2.configure(cfg_2_drive, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    //3
    DriveMoter3 = new SparkMax(Constants.modelas[2].DriveMoterId, MotorType.kBrushless);
    SteerMoter3 = new SparkMax(Constants.modelas[2].SteerMoterId, MotorType.kBrushless);
    canCoder3 = new CANcoder(Constants.modelas[2].CANcoderId);
    SteerMoter3.configure(cfg_3_steer, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    DriveMoter3.configure(cfg_3_drive, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    //4
    DriveMoter4 = new SparkMax(Constants.modelas[3].DriveMoterId, MotorType.kBrushless);
    SteerMoter4 = new SparkMax(Constants.modelas[3].SteerMoterId, MotorType.kBrushless);
    canCoder4 = new CANcoder(Constants.modelas[3].CANcoderId);
    SteerMoter4.configure(cfg_4_steer, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    DriveMoter4.configure(cfg_4_drive, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);


    
    
    calibrateSteer(1);
    calibrateSteer(2);
    calibrateSteer(3);
    calibrateSteer(4);
    addCommands();

  }

  private void addCommands() {
    

  }
  public void setSteerAngle(double angle, int modelaNumber) {
    // Set the steer motor to the desired angle
    
    double error = MathUtil.inputModulus(angle,-180, 180) - getSteerPosition(modelaNumber);
    if(error > 180) {
      error -= 360;
    } else if(error < -180) {
      error += 360;
    }
    double velocity = error * 1.1; // Proportional control for angle
    if (Math.abs(velocity) < 4.0) {
      velocity = 0; // Stop if the error is small
    }
    setSteerVelocity(velocity, modelaNumber);


  }
  public void setSteerPower(double power, int modelaNumber) {
    if (modelaNumber == 1) {
      SteerMoter1.set(power);
    } else if (modelaNumber == 2) {
      SteerMoter2.set(power);
    } else if (modelaNumber == 3) {
      SteerMoter3.set(power);
    } else if (modelaNumber == 4) {
      SteerMoter4.set(power);
    }
  }
  public void setDriverPower(double power, int modelaNumber) {
    if (modelaNumber == 1) {
      DriveMoter1.set(power);
    } else if (modelaNumber == 2) {
      DriveMoter2.set(power);
    } else if (modelaNumber == 3) {
      DriveMoter3.set(power);
    } else if (modelaNumber == 4) {
      DriveMoter4.set(power);
    }
  }
  private void calibrateSteer(int modelaNumber) {
    Modela modela = Constants.modelas[modelaNumber];
    double absolotAngel = getAbseloteAngele(modelaNumber) - modela.CANcoderOffset;

    if (modelaNumber == 1) {
      SteerMoter1.getEncoder().setPosition(absolotAngel * modela.SteerMoterRatio / 360);
    } else if (modelaNumber == 2) {
      SteerMoter2.getEncoder().setPosition(absolotAngel * modela.SteerMoterRatio / 360);
    } else if (modelaNumber == 3) {
      SteerMoter3.getEncoder().setPosition(absolotAngel * modela.SteerMoterRatio / 360);
    } else if (modelaNumber == 4) {
      SteerMoter4.getEncoder().setPosition(absolotAngel * modela.SteerMoterRatio / 360);
    }

  }
  public double getSteerPosition(int modelaNumber) {
    double angle = 0;
    if (modelaNumber == 1) {
      angle = SteerMoter1.getEncoder().getPosition() / Constants.modelas[0].SteerMoterRatio * 360;
    } else if (modelaNumber == 2) {
      angle = SteerMoter2.getEncoder().getPosition() / Constants.modelas[1].SteerMoterRatio * 360;
    } else if (modelaNumber == 3) {
      angle = SteerMoter3.getEncoder().getPosition() / Constants.modelas[2].SteerMoterRatio * 360;
    } else if (modelaNumber == 4) {
      angle = SteerMoter4.getEncoder().getPosition() / Constants.modelas[3].SteerMoterRatio * 360;
    }
    return MathUtil.inputModulus(angle, -180, 180);
    
  }
  public double getDriverPosition(int modelaNumber) {
    if (modelaNumber == 1) {
      return DriveMoter1.getEncoder().getPosition() / Constants.modelas[0].DriveMoterRatio * 360;
    } else if (modelaNumber == 2) {
      return DriveMoter2.getEncoder().getPosition() / Constants.modelas[1].DriveMoterRatio * 360;
    } else if (modelaNumber == 3) {
      return DriveMoter3.getEncoder().getPosition() / Constants.modelas[2].DriveMoterRatio * 360;
    } else if (modelaNumber == 4) {
      return DriveMoter4.getEncoder().getPosition() / Constants.modelas[3].DriveMoterRatio * 360;
    }
    else {
    return 0.0; // ערך ברירת מחדל אם מספר לא חוקי
    }
  }

  public double getSteerVelocity(int modelaNumber) {
    if (modelaNumber == 1) {
      return SteerMoter1.getEncoder().getVelocity() / Constants.modelas[0].SteerMoterRatio * 360 / 60;
    } else if (modelaNumber == 2) {
      return SteerMoter2.getEncoder().getVelocity() / Constants.modelas[1].SteerMoterRatio * 360 / 60;
    } else if (modelaNumber == 3) {
      return SteerMoter3.getEncoder().getVelocity() / Constants.modelas[2].SteerMoterRatio * 360 / 60;
    } else if (modelaNumber == 4) {
      return SteerMoter4.getEncoder().getVelocity() / Constants.modelas[3].SteerMoterRatio * 360 / 60;
    }
    else {
      return 0.0; // Default value if invalid number
    }
  }
  public double getdriverVelocity(int modelaNumber) {
   
   // Convert to radians per second
    if (modelaNumber == 1) {
      return DriveMoter1.getEncoder().getVelocity() / Constants.modelas[0].DriveMoterRatio / 60 * Math.PI * Constants.modelas[0].diameter;
    } else if (modelaNumber == 2) {
      return DriveMoter2.getEncoder().getVelocity() / Constants.modelas[1].DriveMoterRatio / 60 * Math.PI * Constants.modelas[1].diameter;
    } else if (modelaNumber == 3) {
      return DriveMoter3.getEncoder().getVelocity() / Constants.modelas[2].DriveMoterRatio / 60 * Math.PI * Constants.modelas[2].diameter;
    } else if (modelaNumber == 4) {
      return DriveMoter4.getEncoder().getVelocity() / Constants.modelas[3].DriveMoterRatio / 60 * Math.PI * Constants.modelas[3].diameter;
    }
    else {
      return 0.0; // Default value if invalid number
    }
  }

  public double getSteerPower(int modelaNumber) {
    if (modelaNumber == 1) {
      return SteerMoter1.getAppliedOutput();
    } else if (modelaNumber == 2) {
      return SteerMoter2.getAppliedOutput();
    } else if (modelaNumber == 3) {
      return SteerMoter3.getAppliedOutput();
    } else if (modelaNumber == 4) {
      return SteerMoter4.getAppliedOutput();
    }
    else {
      return 0.0; // Default value if invalid number
    }
    
  }
  public double getdriverPower(int modelaNumber) {
    if (modelaNumber == 1) {
      return DriveMoter1.getAppliedOutput();
    } else if (modelaNumber == 2) {
      return DriveMoter2.getAppliedOutput();
    } else if (modelaNumber == 3) {
      return DriveMoter3.getAppliedOutput();
    } else if (modelaNumber == 4) {
      return DriveMoter4.getAppliedOutput();
    }
    else {
      return 0.0; // Default value if invalid number
    }
   
  }
  public double getAbseloteAngele(int modelaNumber) {
    if (modelaNumber == 1) {
      return canCoder1.getAbsolutePosition().getValueAsDouble() * 360;
    } else if (modelaNumber == 2) {
      return canCoder2.getAbsolutePosition().getValueAsDouble() * 360;
    } else if (modelaNumber == 3) {
      return canCoder3.getAbsolutePosition().getValueAsDouble() * 360;
    } else if (modelaNumber == 4) {
      return canCoder4.getAbsolutePosition().getValueAsDouble() * 360;
    }
    else {
      return 0.0; // Default value if invalid number
    }
    
  }

  public void setSteerVelocity(double velocity, int modelaNumber) {

    double ff = steerFF.calculate(velocity);
    if (modelaNumber == 1) {
      SteerMoter1.setVoltage(ff);
    } else if (modelaNumber == 2) {
      SteerMoter2.setVoltage(ff);
    } else if (modelaNumber == 3) {
      SteerMoter3.setVoltage(ff);
    } else if (modelaNumber == 4) {
      SteerMoter4.setVoltage(ff);
    }
    double pid = steerPID.calculate(getSteerVelocity(modelaNumber), velocity);
    if (modelaNumber == 1) {
      SteerMoter1.setVoltage(ff + pid);
    } else if (modelaNumber == 2) {
      SteerMoter2.setVoltage(ff + pid);
    } else if (modelaNumber == 3) {
      SteerMoter3.setVoltage(ff + pid);
    } else if (modelaNumber == 4) {
      SteerMoter4.setVoltage(ff + pid);
    }

    
  }
  public void setDriverVelocity(double velocity, int modelaNumber) {
    double ff = driverFF.calculate(velocity);
    if (modelaNumber == 1) {
      DriveMoter1.setVoltage(ff);
    } else if (modelaNumber == 2) {
      DriveMoter2.setVoltage(ff);
    } else if (modelaNumber == 3) {
      DriveMoter3.setVoltage(ff);
    } else if (modelaNumber == 4) {
      DriveMoter4.setVoltage(ff);
    }
    double pid = driverPID.calculate(getdriverVelocity(modelaNumber), velocity);
    if (modelaNumber == 1) {
      DriveMoter1.setVoltage(ff + pid);
    } else if (modelaNumber == 2) {
      DriveMoter2.setVoltage(ff + pid);
    } else if (modelaNumber == 3) {
      DriveMoter3.setVoltage(ff + pid);
    } else if (modelaNumber == 4) {
      DriveMoter4.setVoltage(ff + pid);
    }
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);
    for (int i = 0; i < 4; i++) {
        final int index = i; // נדרש כדי שלא יהיה בעיה בלמדה
        builder.addDoubleProperty("Steer Position " + i, () -> getSteerPosition(index), null);
        builder.addDoubleProperty("Driver Position " + i, () -> getDriverPosition(index), null);
        builder.addDoubleProperty("Steer Velocity " + i, () -> getSteerVelocity(index), null);
        builder.addDoubleProperty("Driver Velocity " + i, () -> getdriverVelocity(index), null);
        builder.addDoubleProperty("Steer Power " + i, () -> getSteerPower(index), null);
        builder.addDoubleProperty("Driver Power " + i, () -> getdriverPower(index), null);
        builder.addDoubleProperty("Abselote Angle " + i, () -> getAbseloteAngele(index), null);
    }
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
