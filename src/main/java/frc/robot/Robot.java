package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class Robot extends TimedRobot {
  private final PWMSparkMax m_leftMotor = new PWMSparkMax(1);
  private final PWMSparkMax m_leftMotor1 = new PWMSparkMax(2);
  private final PWMSparkMax m_rightMotor = new PWMSparkMax(3);
  private final PWMSparkMax m_rightMotor1 = new PWMSparkMax(4);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);
  private final Joystick m_stick = new Joystick(0);

  public Robot() {
    m_leftMotor.addFollower(m_leftMotor1);
    m_rightMotor.addFollower(m_rightMotor1);

    m_rightMotor.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(-m_stick.getY(), -m_stick.getX());
  }
}
