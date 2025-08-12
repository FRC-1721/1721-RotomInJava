package frc.robot;

public final class Constants {
  public static final class OperatorConstants {
    public static final int DriverControllerPort = 0;
  }

  public static final class DriveConstants {
    public static final double DampenGlobal = .2;
    public static final double DampenRight  = 1 * DampenGlobal;
    public static final double DampenLeft   = .6 * DampenGlobal;

    public static final double DampenElevator = .7;
    public static final double DampenLadder = .5;
  }

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
