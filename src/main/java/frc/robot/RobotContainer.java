package frc.robot;

import frc.robot.command.ArcadeDrive;
import frc.robot.command.ManualArm;
import frc.robot.subsystem.Drive;
import frc.robot.subsystem.Arm;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final Drive driveSubsystem = new Drive();
  private final Arm armSubsystem = new Arm();

  private final CommandXboxController driverController =
      new CommandXboxController(Constants.OperatorConstants.DriverControllerPort);

  public RobotContainer() {
    configureBindings();

    driveSubsystem.setDefaultCommand(
      new ArcadeDrive(driveSubsystem, () -> -driverController.getRawAxis(1), () -> driverController.getRawAxis(0))
    );
    armSubsystem.setDefaultCommand(
      new ManualArm(armSubsystem, () -> -driverController.getRawAxis(5), () -> driverController.getRawAxis(3) - driverController.getRawAxis(2))
    );
  }

  private void configureBindings() {
    //driverController.b().whileTrue(new Explode());
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
