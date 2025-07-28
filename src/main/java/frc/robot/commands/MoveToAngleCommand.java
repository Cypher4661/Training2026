package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MotorSubsystem;
import frc.demacia.elastilog.ElastiLog;
import java.util.function.DoubleSupplier;

public class MoveToAngleCommand extends Command{
    private final MotorSubsystem armSubsystem;
    private final DoubleSupplier angleSupplier;

    public MoveToAngleCommand(MotorSubsystem subsystem, DoubleSupplier angleSupplier) {
        this.armSubsystem = subsystem;
        this.angleSupplier = angleSupplier;
        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
        double target = angleSupplier.getAsDouble();
        armSubsystem.setPosition(target);

        double actual = armSubsystem.getPosition();
        double error = target - actual;

        ElastiLog.info("TargetAngle", target);
        ElastiLog.info("ActualAngle", actual);
        ElastiLog.info("AngleError", error);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
