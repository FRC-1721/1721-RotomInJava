package frc.robot.command;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.Arm;

/**
 * Spins the claw at a fixed power while a button is held (see RobotContainer).
 * Positive and negative "force" spin it in opposite directions.
 *
 * This command doesn't call addRequirements(), so it can run at the same time
 * as ManualArm instead of interrupting it.
 */
public class ManualGripper extends Command {
  private final Arm armSubsystem;
  private final double force;

  public ManualGripper(Arm armSubsystem, double force) {
    this.armSubsystem = armSubsystem;
    this.force = force;
  }

  // Start the claw once; the motor keeps that power until told otherwise
  @Override
  public void initialize() {
    armSubsystem.grab(force);
  }

  // Button released: stop the claw
  @Override
  public void end(boolean interrupted) {
    armSubsystem.grab(0);
  }
}
