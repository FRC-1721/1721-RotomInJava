package frc.robot.subsystem;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;

public class DriveIOSim implements DriveIO {
  private static final double Gearing = KitbotGearing.k10p71.value;
  private static final double WheelCircumferenceMeters = Math.PI * Units.inchesToMeters(6);

  private final DifferentialDrivetrainSim sim = DifferentialDrivetrainSim.createKitbotSim(
    KitbotMotor.kDoubleNEOPerSide, KitbotGearing.k10p71, KitbotWheelSize.kSixInch, null
  );

  private double leftAppliedVolts = 0.0;
  private double rightAppliedVolts = 0.0;

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    sim.setInputs(leftAppliedVolts, rightAppliedVolts);
    sim.update(0.02);

    inputs.leftPositionRotations = sim.getLeftPositionMeters() / WheelCircumferenceMeters * Gearing;
    inputs.leftVelocityRPM = sim.getLeftVelocityMetersPerSecond() / WheelCircumferenceMeters * Gearing * 60;
    inputs.leftAppliedVolts = leftAppliedVolts;
    inputs.leftCurrentAmps = new double[] {sim.getLeftCurrentDrawAmps()};

    inputs.rightPositionRotations = sim.getRightPositionMeters() / WheelCircumferenceMeters * Gearing;
    inputs.rightVelocityRPM = sim.getRightVelocityMetersPerSecond() / WheelCircumferenceMeters * Gearing * 60;
    inputs.rightAppliedVolts = rightAppliedVolts;
    inputs.rightCurrentAmps = new double[] {sim.getRightCurrentDrawAmps()};
  }

  @Override
  public void setLeft(double speed) {
    leftAppliedVolts = MathUtil.clamp(speed, -1.0, 1.0) * 12.0;
  }

  @Override
  public void setRight(double speed) {
    rightAppliedVolts = MathUtil.clamp(speed, -1.0, 1.0) * 12.0;
  }
}
