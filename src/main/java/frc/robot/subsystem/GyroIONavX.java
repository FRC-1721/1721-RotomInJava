package frc.robot.subsystem;

import com.studica.frc.AHRS;

import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

/** The real gyro: a navX board from Studica. */
public class GyroIONavX implements GyroIO {
  // AHRS is the navX's class name ("Attitude and Heading Reference System")
  private final AHRS navx = new AHRS(Constants.DriveConstants.NavXConnection);

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    inputs.connected = navx.isConnected();
    inputs.yawPosition = navx.getRotation2d();
    // getRate() is degrees per second with clockwise positive, so flip it
    inputs.yawVelocityRadPerSec = Units.degreesToRadians(-navx.getRate());
  }
}
