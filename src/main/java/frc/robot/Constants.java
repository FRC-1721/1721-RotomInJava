package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * One place for all the numbers the robot uses (ports, CAN IDs, speed limits).
 * If a number might need tuning, put it here instead of burying it in the code.
 *
 * Java note: "static" means the value belongs to the class itself, so you use it
 * as Constants.MotorConstants.ladder without creating an object. "final" means it
 * can't be changed while the robot is running.
 */
public final class Constants {
  /** Which way the code is running. AdvantageKit uses this to pick real or fake hardware. */
  public static enum Mode {
    /** On the real robot. */
    REAL,
    /** In the desktop simulator, with simulated motors. */
    SIM,
    /** Re-running the code against a log file recorded on the robot. */
    REPLAY
  }

  /** What to do when NOT on the real robot. Change to Mode.REPLAY to replay a log. */
  public static final Mode SimMode = Mode.SIM;
  // "condition ? a : b" means: if condition is true use a, otherwise use b
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : SimMode;

  public static final class OperatorConstants {
    /** USB port of the driver's Xbox controller in the Driver Station app. */
    public static final int DriverControllerPort = 0;
  }

  /**
   * "Dampen" values scale motor power down (1 = full power, 0.5 = half power).
   * They keep the robot controllable while we test.
   */
  public static final class DriveConstants {
    /** Top drive speed for both sides. */
    public static final double DampenGlobal = .2;
    /** Right side drive scale. */
    public static final double DampenRight  = 1 * DampenGlobal;
    /** Left side drive scale, lower than the right so the robot drives straight. */
    public static final double DampenLeft   = .6 * DampenGlobal;

    /** Elevator (rope spool) speed scale. */
    public static final double DampenElevator = .7;
    /** Ladder (lead screw) speed scale. */
    public static final double DampenLadder = .5;

    // Drivetrain measurements, used to work out where the robot is on the field.
    // These are the kit-of-parts chassis defaults; measure Rotom and update them.

    /** How many times the motor turns for one turn of the wheel. */
    public static final double GearRatio = 10.71;
    public static final double WheelDiameterMeters = Units.inchesToMeters(6);
    /** Distance between the left and right wheels. */
    public static final double TrackWidthMeters = Units.inchesToMeters(26);

    /**
     * Where the robot is when the code starts, in meters from the blue alliance
     * corner of the field, facing away from the blue wall.
     */
    public static final Pose2d StartingPose = new Pose2d(2.0, 4.0, Rotation2d.kZero);
  }

  /**
   * CAN IDs of each SPARK MAX motor controller. Every controller on the CAN bus
   * has a unique ID, set with the REV Hardware Client. These must match the robot.
   */
  public static final class MotorConstants {
    public static final int leftLead    = 2;
    public static final int leftFollow  = 1;
    public static final int rightLead   = 3;
    public static final int rightFollow = 4;
    public static final int elevator    = 11;
    public static final int ladder      = 6;
    public static final int claw        = 7;
  }
}
