package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MotorSubsystem;

public class goToAngle extends Command{
    private double target;
    private MotorSubsystem subsystem;
    private PIDController controller;

    public goToAngle(MotorSubsystem subsystem, double targetAngle) {

    }


    public goToAngle(double target, MotorSubsystem subsystem) {
        this.target = target;
        this.subsystem = subsystem;
        controller = new PIDController(0.005, 0.001, 0.0001);
        controller.setTolerance(2.0, 10.0);
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        controller.reset();
        controller.setSetpoint(target);
    }

    @Override
    public void execute() {
        double current = subsystem.GetAngle();
        double error = target - current;
        if(error > 0) {
            subsystem.setPower(0.015);
        } else {
            subsystem.setPower(-0.015);
        }
        double turnSpeed = controller.calculate(subsystem.GetAngle());
        subsystem.setPower(turnSpeed);
    }

    @Override
    public boolean isFinished( ) {
        //double current = subsystem.GetAngle();
        //double error = target - current;
        //return Math.abs(error) < tolerance;
        return controller.atSetpoint();

    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
        System.out.println("PID Turn ended, final error : " + controller.getPositionError() + " degrees");
        
    }
}
