package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.firstSubSystem;

public class pidToAngle extends Command{
    firstSubSystem subSystem;

    private PIDController controller = new PIDController(0.005, 0.001, 0.0005);

    public pidToAngle(firstSubSystem subSystem) {
        this.subSystem = subSystem;
        controller.setTolerance(2, 50);
        addRequirements(subSystem);
        SmartDashboard.putNumber("wantedAngle", 90);
        SmartDashboard.putData("pid", controller);
        SmartDashboard.putNumber("error", controller.getError());
        
    }

    @Override
    public void initialize() {
        controller.reset();

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double wantedAngle = SmartDashboard.getNumber("wantedAngle", 90);
        subSystem.setPower(controller.calculate (subSystem.getPos(), wantedAngle));
    }
    
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return controller.atSetpoint();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        subSystem.stop();
    }

}