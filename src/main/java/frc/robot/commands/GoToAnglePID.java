package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FirstSubsystem;

public class GoToAnglePID extends Command {
    FirstSubsystem subsystem;
    double targetAngle = 0;

    public static final double maxPower = 0.3;
    public static final double tolerance = 1;
    public static final double rateTolerance = 5;
    PIDController pid = new PIDController(0.003,0.0000, 0.005);


    public GoToAnglePID(FirstSubsystem subsystem) {
        this.subsystem = subsystem;
        pid.setIZone(20);
        pid.setIntegratorRange(-0.01,0.01);
        pid.setTolerance(tolerance, rateTolerance);
        SmartDashboard.putData("PID", pid);
        SmartDashboard.putData("FollowAngle", this);
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        pid.reset();
    }

    @Override
    public void execute() {
        if(pid.atSetpoint()) {
            subsystem.setPower(0);   
        } else {
            subsystem.setPower(MathUtil.clamp(pid.calculate(subsystem.getAngleDegrees(), targetAngle),-maxPower, maxPower));
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
    }

    private void setTarget(double target) {
        if(target != targetAngle) {
            targetAngle = target;
            pid.setSetpoint(target);
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("target", ()->targetAngle,this::setTarget);
    }


}
