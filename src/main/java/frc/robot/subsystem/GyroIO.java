package frc.robot.subsystem;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;

/**
 * The hardware layer for the gyro, the sensor that measures which way the
 * robot is facing. Works the same way as DriveIO: GyroIONavX is the real
 * sensor, and an empty GyroIO (which reports "not connected") is used in the
 * simulator and in replay.
 */
public interface GyroIO {
  @AutoLog
  public static class GyroIOInputs {
    public boolean connected = false;
    /** Which way the robot faces. Counter-clockwise (turning left) is positive. */
    public Rotation2d yawPosition = Rotation2d.kZero;
    /** How fast the robot is turning, counter-clockwise positive. */
    public double yawVelocityRadPerSec = 0.0;
  }

  /** Fill in "inputs" with the latest sensor readings. */
  public default void updateInputs(GyroIOInputs inputs) {}
}
