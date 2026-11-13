package frc.robot.subsystem;

import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {
  @AutoLog
  public static class ArmIOInputs {
    public double ladderPositionRotations = 0.0;
    public double ladderVelocityRPM = 0.0;
    public double ladderAppliedVolts = 0.0;
    public double ladderCurrentAmps = 0.0;
    public double ladderTempCelsius = 0.0;

    public double elevatorPositionRotations = 0.0;
    public double elevatorVelocityRPM = 0.0;
    public double elevatorAppliedVolts = 0.0;
    public double elevatorCurrentAmps = 0.0;
    public double elevatorTempCelsius = 0.0;

    public double clawPositionRotations = 0.0;
    public double clawVelocityRPM = 0.0;
    public double clawAppliedVolts = 0.0;
    public double clawCurrentAmps = 0.0;
    public double clawTempCelsius = 0.0;
  }

  public default void updateInputs(ArmIOInputs inputs) {}

  public default void setLadder(double speed) {}

  public default void setElevator(double speed) {}

  public default void setClaw(double speed) {}
}
