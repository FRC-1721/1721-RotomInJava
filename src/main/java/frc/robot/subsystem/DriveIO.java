package frc.robot.subsystem;

import org.littletonrobotics.junction.AutoLog;

/**
 * The hardware layer for the drivetrain (AdvantageKit's "IO" pattern).
 *
 * Java note: an interface is a list of methods a class promises to have.
 * DriveIOSparkMax (real motors) and DriveIOSim (simulated motors) both
 * "implements DriveIO", so Drive can use either one without knowing which.
 *
 * Everything the robot reads from hardware goes in DriveIOInputs. AdvantageKit
 * logs it every loop, which lets us replay a match later in AdvantageScope.
 */
public interface DriveIO {
  // Java note: @AutoLog is an annotation. AdvantageKit sees it when the code is
  // built and generates a DriveIOInputsAutoLogged class that knows how to log these fields.
  @AutoLog
  public static class DriveIOInputs {
    public double leftPositionRotations = 0.0;
    public double leftVelocityRPM = 0.0;
    public double leftAppliedVolts = 0.0;
    // Arrays (double[]) hold one value per motor: lead and follower
    public double[] leftCurrentAmps = new double[] {};
    public double[] leftTempCelsius = new double[] {};

    public double rightPositionRotations = 0.0;
    public double rightVelocityRPM = 0.0;
    public double rightAppliedVolts = 0.0;
    public double[] rightCurrentAmps = new double[] {};
    public double[] rightTempCelsius = new double[] {};
  }

  // "default" methods do nothing unless a class replaces them. That's what
  // replay mode uses: an IO that does nothing, because data comes from the log.

  /** Fill in "inputs" with the latest sensor readings. */
  public default void updateInputs(DriveIOInputs inputs) {}

  /** Left side power, -1 (full reverse) to 1 (full forward). */
  public default void setLeft(double speed) {}

  /** Right side power, -1 (full reverse) to 1 (full forward). */
  public default void setRight(double speed) {}
}
