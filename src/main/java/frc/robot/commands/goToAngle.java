package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MotorSubsystem;

public class goToAngle extends Command{
    private double target;
    MotorSubsystem subsystem;
    PIDController pid = new PIDController(0.005, 0.001, 0.0001);


    public goToAngle(MotorSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
        SmartDashboard.putData("pid", pid);
        SmartDashboard.putNumber("targetPosition", 0);
        pid.setTolerance(5, 30);
        pid.enableContinuousInput(-180, 180);
        pid.setIZone(10);
        pid.setIntegratorRange(-0.0015, 0.0015);
    }

    @Override
    public void initialize() {
        pid.reset();
    }

    @Override
    public void execute() {
        double current = subsystem.getPosition();
        double target = SmartDashboard.getNumber("targetPosition", 0);
        subsystem.setPower(pid.calculate(current, target));
    }

    @Override
    public boolean isFinished( ) {

        return pid.atSetpoint();

    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
    }
}
