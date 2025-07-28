package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MotorSubsystem;
import com.demacia.elastilog.ElastiLog;
import java.util.function.DoubleSupplier;

public class HoldVelocityCommand extends Command {
    private final MotorSubsystem armSubsystem;
    private final DoubleSupplier velocitySupplier;

    public HoldVelocityCommand(MotorSubsystem subsystem, DoubleSupplier velocitySupplier) {
        this.armSubsystem = subsystem;
        this.velocitySupplier = velocitySupplier;
        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
        double velocity = velocitySupplier.getAsDouble();
        armSubsystem.setVelocity(velocity);

        double actual = armSubsystem.getVelocity();
        double error = velocity - actual;

        ElastiLog.info("TargetVelocity", velocity);
        ElastiLog.info("ActualVelocity", actual);
        ElastiLog.info("VelocityError", error);
    }

    @Override
    public void end(boolean interrupted) {
        armSubsystem.setVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
