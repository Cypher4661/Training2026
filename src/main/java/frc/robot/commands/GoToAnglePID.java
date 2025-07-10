package frc.robot.commands;

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
    PIDController pid = new PIDController(0.005,0.0001, 0.0005);


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
        System.out.println(" Command started at " + subsystem.getPosition() + " going to " + targetAngle + " degrees");
        pid.reset();
        pid.setSetpoint(targetAngle);
    }

    @Override
    public void execute() {
        subsystem.setPower(pid.calculate(subsystem.getPosition()));
    }

    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
        System.out.println(" Command ended at " + subsystem.getPosition() + " degrees, error = " + (targetAngle - subsystem.getPosition()));
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
        builder.addDoubleProperty("error", pid::getError,null);
        builder.addDoubleProperty("sum error", pid::getAccumulatedError,null);
    }


}
