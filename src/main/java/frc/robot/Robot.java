package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class Robot extends TimedRobot {
  private final PWMSparkMax leftMotor = new PWMSparkMax(1);
  private final PWMSparkMax leftMotor1 = new PWMSparkMax(2);
  private final PWMSparkMax rightMotor = new PWMSparkMax(3);
  private final PWMSparkMax rightMotor1 = new PWMSparkMax(4);

  private final DifferentialDrive robotDrive = new DifferentialDrive(leftMotor::set, rightMotor::set);
  private final Joystick stick = new Joystick(0);

  public Robot() {
    leftMotor.addFollower(leftMotor1);
    rightMotor.addFollower(rightMotor1);

    rightMotor.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    robotDrive.arcadeDrive(-stick.getY(), -stick.getX());
  }
}
