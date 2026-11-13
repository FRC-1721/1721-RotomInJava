package frc.robot.subsystem;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * The arm: a lead screw "ladder" that changes the lift angle, a rope spool
 * "elevator" that extends it, and a claw that grabs game pieces.
 *
 * Like Drive, this class decides WHAT to do, and the ArmIO it's given (real
 * or simulated) talks to the motors.
 */
public class Arm extends SubsystemBase{
  private final ArmIO io;
  private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();

  public Arm(ArmIO io) {
    this.io = io;
  }

  /** Runs every 20 ms: read the motors' sensors and log them. */
  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Arm", inputs);
  }

  /**
   * This is the spool motor, it controls
   * how much rope we let out.
   * @param speed Motor speed
   */
  public void extension(double speed) {
    double output = speed * Constants.DriveConstants.DampenElevator;
    Logger.recordOutput("Arm/ElevatorOutput", output);
    io.setElevator(output);
  }

  /**
   * This is the lead screw motor, it controls
   * where on the screw the fulcrum of the lift
   * is
   * @param speed Motor speed
   */
  public void ascent(double speed) {
    double output = speed * Constants.DriveConstants.DampenLadder;
    Logger.recordOutput("Arm/LadderOutput", output);
    io.setLadder(output);
  }

  /**
   * Spins the claw motor.
   * @param speed Motor speed, positive and negative grab and release
   */
  public void grab(double speed) {
    Logger.recordOutput("Arm/ClawOutput", speed);
    io.setClaw(speed);
  }
}
