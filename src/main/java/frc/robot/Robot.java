package frc.robot;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


/**
 * The top-level robot class. WPILib calls the methods below automatically:
 * the "Init" methods once when the robot enters a mode (disabled, autonomous,
 * teleop, test), and the "Periodic" methods every 20 ms (50 times a second).
 *
 * In a command-based robot this class stays small. The real setup happens in
 * RobotContainer, and the work happens in subsystems and commands.
 *
 * Java note: "extends LoggedRobot" means Robot is a LoggedRobot with our own
 * changes added. LoggedRobot is AdvantageKit's version of WPILib's TimedRobot
 * that also records everything to a log.
 */
public class Robot extends LoggedRobot {
  private Command autonomousCommand;
  private final RobotContainer robotContainer;

  /** Runs once when the robot code starts. */
  public Robot() {
    // Set up AdvantageKit logging. This must happen before anything else is created.
    Logger.recordMetadata("ProjectName", "1721-RotomInJava");

    switch (Constants.currentMode) {
      case REAL:
        // Save logs to a USB stick on the roboRIO, and send live data to AdvantageScope
        Logger.addDataReceiver(new WPILOGWriter());
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case SIM:
        // Send live data to AdvantageScope only
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case REPLAY:
        // Run as fast as possible, read inputs from an old log, and save a new "_sim" log
        setUseTiming(false);
        String logPath = LogFileUtil.findReplayLog();
        Logger.setReplaySource(new WPILOGReader(logPath));
        Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        break;
    }

    Logger.start();

    robotContainer = new RobotContainer();
  }

  /** Runs every 20 ms in every mode. */
  @Override
  public void robotPeriodic() {
    // The scheduler runs the active commands and each subsystem's periodic().
    // Without this line, no commands would ever run.
    CommandScheduler.getInstance().run();
  }

  // Java note: @Override marks a method that replaces one from the parent class
  // (LoggedRobot). Empty ones are here so it's easy to add code later.

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    // "null" means there's no command, so check before using it
    if (autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(autonomousCommand);
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // Stop autonomous when the driver takes over
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
