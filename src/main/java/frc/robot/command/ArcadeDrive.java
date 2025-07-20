package frc.robot.command;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.Drive;

public class ArcadeDrive extends Command {

  private final Drive driveSubsystem;
  private final Supplier<Double> velocityAcquirer, rotationStation;

  public ArcadeDrive(Drive driveSubsystem,
      Supplier<Double> velocityAcquirer, Supplier<Double> rotationStation) {
    this.velocityAcquirer = velocityAcquirer;
    this.rotationStation = rotationStation;
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double velocity = velocityAcquirer.get();
    double rotation = rotationStation.get();
    driveSubsystem.arcadeDrive(velocity, rotation);
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.setMotors(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
    
}
