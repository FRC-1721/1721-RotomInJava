package frc.robot.command;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.Arm;

public class ManualGripper extends Command {
  private final Arm armSubsystem;
  private final double force;

  public ManualGripper(Arm armSubsystem, double force) {
    this.armSubsystem = armSubsystem;
    this.force = force;
  }

  @Override
  public void initialize() {
    armSubsystem.grab(force);
  }

  @Override
  public void end(boolean interrupted) {
    armSubsystem.grab(0);
  }
}
