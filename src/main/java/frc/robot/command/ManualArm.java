package frc.robot.command;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.Arm;

/**
 * Lets the driver move the ladder and elevator directly with the controller.
 * This is the Arm's default command, so it runs whenever nothing else uses the Arm.
 */
public class ManualArm extends Command {

  private final Arm armSubsystem;
  private final Supplier<Double> ladderProvider, elevatorDealer;

  public ManualArm(Arm armSubsystem, Supplier<Double> ladderProvider, Supplier<Double> elevatorDealer) {
    this.armSubsystem = armSubsystem;
    this.ladderProvider = ladderProvider;
    this.elevatorDealer = elevatorDealer;
    addRequirements(armSubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    armSubsystem.ascent(deadZone(ladderProvider.get(), 0.1));
    armSubsystem.extension(deadZone(elevatorDealer.get(), 0.1));
  }

  @Override
  public void end(boolean interrupted) {
    armSubsystem.extension(0);
    armSubsystem.ascent(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  /**
   * Joysticks rarely rest at exactly 0, which would make motors creep.
   * Anything smaller than "zone" is treated as 0.
   */
  private double deadZone(double input, double zone) {
    if (Math.abs(input) > zone) {
      return input;
    } else {
      return 0;
    }
  }

}
