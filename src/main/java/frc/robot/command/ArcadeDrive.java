package frc.robot.command;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.Drive;

/**
 * Drives the robot from joystick input. "Arcade" drive means one stick axis
 * controls forward/backward and another controls turning.
 *
 * A command is an action the robot does. The scheduler calls initialize() once
 * when it starts, execute() every 20 ms while it runs, and end() when it stops.
 */
public class ArcadeDrive extends Command {

  private final Drive driveSubsystem;
  // Java note: a Supplier<Double> is something we can call .get() on to get a
  // fresh number. Here it reads the joystick each time.
  private final Supplier<Double> velocityAcquirer, rotationStation;

  public ArcadeDrive(Drive driveSubsystem,
      Supplier<Double> velocityAcquirer, Supplier<Double> rotationStation) {
    // "this.x = x" saves the value passed in into this object's field of the same name
    this.velocityAcquirer = velocityAcquirer;
    this.rotationStation = rotationStation;
    this.driveSubsystem = driveSubsystem;
    // Tell the scheduler this command uses the drivetrain, so two commands can't
    // drive it at the same time
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

  // Never finishes on its own; it runs until something else interrupts it
  @Override
  public boolean isFinished() {
    return false;
  }

}
