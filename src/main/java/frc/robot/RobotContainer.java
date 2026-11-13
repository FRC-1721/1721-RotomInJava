package frc.robot;

import frc.robot.command.ArcadeDrive;
import frc.robot.command.ManualArm;
import frc.robot.command.ManualGripper;
import frc.robot.subsystem.Drive;
import frc.robot.subsystem.DriveIO;
import frc.robot.subsystem.DriveIOSim;
import frc.robot.subsystem.DriveIOSparkMax;
import frc.robot.subsystem.Arm;
import frc.robot.subsystem.ArmIO;
import frc.robot.subsystem.ArmIOSim;
import frc.robot.subsystem.ArmIOSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final Drive driveSubsystem;
  private final Arm armSubsystem;

  private final CommandXboxController driverController =
      new CommandXboxController(Constants.OperatorConstants.DriverControllerPort);

  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        driveSubsystem = new Drive(new DriveIOSparkMax());
        armSubsystem = new Arm(new ArmIOSparkMax());
        break;

      case SIM:
        driveSubsystem = new Drive(new DriveIOSim());
        armSubsystem = new Arm(new ArmIOSim());
        break;

      default:
        // Replay: no hardware, inputs come from the log file
        driveSubsystem = new Drive(new DriveIO() {});
        armSubsystem = new Arm(new ArmIO() {});
        break;
    }

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
    driverController.button(5).whileTrue(new ManualGripper(armSubsystem, 0.5));
    driverController.button(6).whileTrue(new ManualGripper(armSubsystem, -0.5));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
