package frc.robot;

import frc.robot.command.ArcadeDrive;
import frc.robot.command.ManualArm;
import frc.robot.command.ManualGripper;
import frc.robot.subsystem.Drive;
import frc.robot.subsystem.DriveIO;
import frc.robot.subsystem.DriveIOSim;
import frc.robot.subsystem.DriveIOSparkMax;
import frc.robot.subsystem.GyroIO;
import frc.robot.subsystem.GyroIONavX;
import frc.robot.subsystem.Arm;
import frc.robot.subsystem.ArmIO;
import frc.robot.subsystem.ArmIOSim;
import frc.robot.subsystem.ArmIOSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * Where the robot is put together: creates the subsystems, the controller, and
 * connects controller sticks and buttons to commands.
 */
public class RobotContainer {
  private final Drive driveSubsystem;
  private final Arm armSubsystem;

  private final CommandXboxController driverController =
      new CommandXboxController(Constants.OperatorConstants.DriverControllerPort);

  public RobotContainer() {
    // Each subsystem gets an "IO" object that talks to motors. On the robot that's
    // real SPARK MAXes, in the simulator it's fake motors, and in replay it's nothing
    // (the data comes from the log). The subsystem code is the same in all three cases.
    // The simulator has no gyro, so Drive works out its heading from the wheels.
    switch (Constants.currentMode) {
      case REAL:
        driveSubsystem = new Drive(new DriveIOSparkMax(), new GyroIONavX());
        armSubsystem = new Arm(new ArmIOSparkMax());
        break;

      case SIM:
        driveSubsystem = new Drive(new DriveIOSim(), new GyroIO() {});
        armSubsystem = new Arm(new ArmIOSim());
        break;

      default:
        // Replay: no hardware, inputs come from the log file
        driveSubsystem = new Drive(new DriveIO() {}, new GyroIO() {});
        armSubsystem = new Arm(new ArmIO() {});
        break;
    }

    configureBindings();

    // A default command runs on a subsystem whenever no other command is using it.
    // Java note: "() -> ..." is a lambda, a tiny function. The command calls it
    // every loop to read the current stick value.
    // Xbox raw axes: 0 = left X, 1 = left Y, 2 = left trigger, 3 = right trigger, 5 = right Y.
    // Pushing a stick forward gives a negative Y, so we flip it with a minus sign.
    driveSubsystem.setDefaultCommand(
      new ArcadeDrive(driveSubsystem, () -> -driverController.getRawAxis(1), () -> driverController.getRawAxis(0))
    );
    // Right stick Y moves the ladder; right trigger minus left trigger moves the elevator
    armSubsystem.setDefaultCommand(
      new ManualArm(armSubsystem, () -> -driverController.getRawAxis(5), () -> driverController.getRawAxis(3) - driverController.getRawAxis(2))
    );
  }

  /** Connect buttons to commands. */
  private void configureBindings() {
    //driverController.b().whileTrue(new Explode());
    // whileTrue: run the command while the button is held, stop it when released.
    // Buttons 5 and 6 are the left and right bumpers.
    driverController.button(5).whileTrue(new ManualGripper(armSubsystem, 0.5));
    driverController.button(6).whileTrue(new ManualGripper(armSubsystem, -0.5));
  }

  /** The command to run in autonomous. null means do nothing. */
  public Command getAutonomousCommand() {
    return null;
  }
}
