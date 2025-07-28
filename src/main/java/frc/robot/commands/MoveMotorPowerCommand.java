package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MotorSubsystem;
import frc.Demacia.elastilog.ElastiLog;
import java.util.function.DoubleSupplier;

public class MoveMotorPowerCommand extends Command {
    private final MotorSubsystem motorSubsystem;
    private final DoubleSupplier powerInput;

    public MoveMotorPowerCommand(MotorSubsystem subsystem, DoubleSupplier powerInput) {
        this.motorSubsystem = subsystem;
        this.powerInput = powerInput;
        addRequirements(motorSubsystem);
    }

    @Override
    public void execute() {
        double power = powerInput.getAsDouble();
        motorSubsystem.setPower(power);
        ElastiLog.info("motorPower", power);
    }

    @Override
    public void end(boolean interrupted) {
        motorSubsystem.setPower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
