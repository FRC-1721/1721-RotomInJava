package frc.robot.subsystem;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase{
  private final ArmIO io;
  private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();

  public Arm(ArmIO io) {
    this.io = io;
  }

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

  public void grab(double speed) {
    Logger.recordOutput("Arm/ClawOutput", speed);
    io.setClaw(speed);
  }
}
