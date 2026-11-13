package frc.robot.subsystem;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
  private final DriveIO io;
  private final DriveIOInputsAutoLogged inputs = new DriveIOInputsAutoLogged();

  private final DifferentialDrive robotDrive;

  public Drive(DriveIO io) {
    this.io = io;

    // DifferentialDrive constructor takes DoubleConsumer x2,
    // DoubleConsumer is just a function that takes in a double
    robotDrive = new DifferentialDrive(
      (double x) -> {
        double output = x * Constants.DriveConstants.DampenLeft;
        Logger.recordOutput("Drive/LeftOutput", output);
        io.setLeft(output);
      },
      (double x) -> {
        double output = x * Constants.DriveConstants.DampenRight;
        Logger.recordOutput("Drive/RightOutput", output);
        io.setRight(output);
      }
    );
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Drive", inputs);
  }

  public void setMotors(double leftSpeed, double rightSpeed) {
    Logger.recordOutput("Drive/LeftOutput", leftSpeed);
    Logger.recordOutput("Drive/RightOutput", rightSpeed);
    io.setLeft(leftSpeed);
    io.setRight(rightSpeed);
  }

  public void arcadeDrive(double velocity, double rotation) {
    Logger.recordOutput("Drive/RequestedVelocity", velocity);
    Logger.recordOutput("Drive/RequestedRotation", rotation);
    robotDrive.arcadeDrive(velocity, rotation);
  }

  @Override
  public void simulationPeriodic() {}
}
