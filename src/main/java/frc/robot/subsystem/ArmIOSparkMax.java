package frc.robot.subsystem;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants;

/** The real arm hardware: one SPARK MAX each for the ladder, elevator, and claw. */
public class ArmIOSparkMax implements ArmIO {
  private final SparkMax ladder = new SparkMax(Constants.MotorConstants.ladder, MotorType.kBrushless);
  private final SparkMax elevator = new SparkMax(Constants.MotorConstants.elevator, MotorType.kBrushless);
  private final SparkMax claw = new SparkMax(Constants.MotorConstants.claw, MotorType.kBrushless);

  public ArmIOSparkMax() {
    // Brake mode makes the ladder hold its position when the stick is let go.
    // The elevator and claw keep whatever settings are saved on their controllers.
    SparkMaxConfig ladderConfig = new SparkMaxConfig();
    ladderConfig.idleMode(IdleMode.kBrake);
    ladder.configure(ladderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void updateInputs(ArmIOInputs inputs) {
    inputs.ladderPositionRotations = ladder.getEncoder().getPosition();
    inputs.ladderVelocityRPM = ladder.getEncoder().getVelocity();
    inputs.ladderAppliedVolts = ladder.getAppliedOutput() * ladder.getBusVoltage();
    inputs.ladderCurrentAmps = ladder.getOutputCurrent();
    inputs.ladderTempCelsius = ladder.getMotorTemperature();

    inputs.elevatorPositionRotations = elevator.getEncoder().getPosition();
    inputs.elevatorVelocityRPM = elevator.getEncoder().getVelocity();
    inputs.elevatorAppliedVolts = elevator.getAppliedOutput() * elevator.getBusVoltage();
    inputs.elevatorCurrentAmps = elevator.getOutputCurrent();
    inputs.elevatorTempCelsius = elevator.getMotorTemperature();

    inputs.clawPositionRotations = claw.getEncoder().getPosition();
    inputs.clawVelocityRPM = claw.getEncoder().getVelocity();
    inputs.clawAppliedVolts = claw.getAppliedOutput() * claw.getBusVoltage();
    inputs.clawCurrentAmps = claw.getOutputCurrent();
    inputs.clawTempCelsius = claw.getMotorTemperature();
  }

  @Override
  public void setLadder(double speed) {
    ladder.set(speed);
  }

  @Override
  public void setElevator(double speed) {
    elevator.set(speed);
  }

  @Override
  public void setClaw(double speed) {
    claw.set(speed);
  }
}
