package frc.robot.subsystem;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

/** A pretend arm for the desktop simulator: three simulated motors. */
public class ArmIOSim implements ArmIO {
  private final DCMotorSim ladderSim = makeSim(DCMotor.getNEO(1));
  private final DCMotorSim elevatorSim = makeSim(DCMotor.getNEO(1));
  private final DCMotorSim clawSim = makeSim(DCMotor.getNeo550(1));

  private double ladderAppliedVolts = 0.0;
  private double elevatorAppliedVolts = 0.0;
  private double clawAppliedVolts = 0.0;

  // Real gearing and load are unknown, so this just simulates a lightly
  // loaded motor; positions come out in motor rotations like the real encoders.
  private static DCMotorSim makeSim(DCMotor motor) {
    return new DCMotorSim(LinearSystemId.createDCMotorSystem(motor, 0.001, 1.0), motor);
  }

  @Override
  public void updateInputs(ArmIOInputs inputs) {
    ladderSim.setInputVoltage(ladderAppliedVolts);
    elevatorSim.setInputVoltage(elevatorAppliedVolts);
    clawSim.setInputVoltage(clawAppliedVolts);
    ladderSim.update(0.02);
    elevatorSim.update(0.02);
    clawSim.update(0.02);

    inputs.ladderPositionRotations = ladderSim.getAngularPositionRotations();
    inputs.ladderVelocityRPM = ladderSim.getAngularVelocityRPM();
    inputs.ladderAppliedVolts = ladderAppliedVolts;
    inputs.ladderCurrentAmps = ladderSim.getCurrentDrawAmps();

    inputs.elevatorPositionRotations = elevatorSim.getAngularPositionRotations();
    inputs.elevatorVelocityRPM = elevatorSim.getAngularVelocityRPM();
    inputs.elevatorAppliedVolts = elevatorAppliedVolts;
    inputs.elevatorCurrentAmps = elevatorSim.getCurrentDrawAmps();

    inputs.clawPositionRotations = clawSim.getAngularPositionRotations();
    inputs.clawVelocityRPM = clawSim.getAngularVelocityRPM();
    inputs.clawAppliedVolts = clawAppliedVolts;
    inputs.clawCurrentAmps = clawSim.getCurrentDrawAmps();
  }

  @Override
  public void setLadder(double speed) {
    ladderAppliedVolts = MathUtil.clamp(speed, -1.0, 1.0) * 12.0;
  }

  @Override
  public void setElevator(double speed) {
    elevatorAppliedVolts = MathUtil.clamp(speed, -1.0, 1.0) * 12.0;
  }

  @Override
  public void setClaw(double speed) {
    clawAppliedVolts = MathUtil.clamp(speed, -1.0, 1.0) * 12.0;
  }
}
