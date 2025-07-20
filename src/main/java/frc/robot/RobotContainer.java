package frc.robot;

import frc.robot.command.ArcadeDrive;
import frc.robot.subsystem.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final Drive driveSubsystem = new Drive();

  private final CommandXboxController driverController =
      new CommandXboxController(Constants.OperatorConstants.DriverControllerPort);

  public RobotContainer() {
    configureBindings();

    driveSubsystem.setDefaultCommand(
      new ArcadeDrive(driveSubsystem, () -> driverController.getRawAxis(1), () -> driverController.getRawAxis(0))
    );
  }

  private void configureBindings() {
    //driverController.b().whileTrue(new Explode());
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
