package frc.robot.subsystem;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import frc.robot.Constants;

/**
 * A pretend drivetrain for the desktop simulator, so we can test code without
 * the robot. It uses WPILib's physics model of a tank drive, set up with the
 * measurements in Constants, which is close enough to see whether driving works.
 */
public class DriveIOSim implements DriveIO {
  // How hard the robot is to spin, and its weight. Same estimates WPILib uses
  // for the kit-of-parts chassis.
  private static final double MomentOfInertia = 0.86;
  private static final double MassKg = Units.lbsToKilograms(60);

  private static final double WheelCircumferenceMeters = Math.PI * Constants.DriveConstants.WheelDiameterMeters;

  private final DifferentialDrivetrainSim sim = new DifferentialDrivetrainSim(
    DCMotor.getNEO(2),
    Constants.DriveConstants.GearRatio,
    MomentOfInertia,
    MassKg,
    Constants.DriveConstants.WheelDiameterMeters / 2,
    Constants.DriveConstants.TrackWidthMeters,
    null
  );

  private double leftAppliedVolts = 0.0;
  private double rightAppliedVolts = 0.0;

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    // Apply the voltages and step the physics forward one loop (20 ms)
    sim.setInputs(leftAppliedVolts, rightAppliedVolts);
    sim.update(0.02);

    // The sim reports wheel distance in meters; convert to motor rotations,
    // the same units the real SPARK MAX encoders report
    double gearRatio = Constants.DriveConstants.GearRatio;
    inputs.leftPositionRotations = sim.getLeftPositionMeters() / WheelCircumferenceMeters * gearRatio;
    inputs.leftVelocityRPM = sim.getLeftVelocityMetersPerSecond() / WheelCircumferenceMeters * gearRatio * 60;
    inputs.leftAppliedVolts = leftAppliedVolts;
    inputs.leftCurrentAmps = new double[] {sim.getLeftCurrentDrawAmps()};

    inputs.rightPositionRotations = sim.getRightPositionMeters() / WheelCircumferenceMeters * gearRatio;
    inputs.rightVelocityRPM = sim.getRightVelocityMetersPerSecond() / WheelCircumferenceMeters * gearRatio * 60;
    inputs.rightAppliedVolts = rightAppliedVolts;
    inputs.rightCurrentAmps = new double[] {sim.getRightCurrentDrawAmps()};
  }

  // Turn power (-1 to 1) into volts, assuming a 12 V battery
  @Override
  public void setLeft(double speed) {
    leftAppliedVolts = MathUtil.clamp(speed, -1.0, 1.0) * 12.0;
  }

  @Override
  public void setRight(double speed) {
    rightAppliedVolts = MathUtil.clamp(speed, -1.0, 1.0) * 12.0;
  }
}
