package frc.robot.subsystem;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants;

/**
 * The real drivetrain hardware: four NEO motors on SPARK MAX controllers.
 * On each side the "lead" motor gets commands and the "follow" motor copies it.
 */
public class DriveIOSparkMax implements DriveIO {
  private final SparkMax leftLead = new SparkMax(Constants.MotorConstants.leftLead, MotorType.kBrushless);
  private final SparkMax leftFollow = new SparkMax(Constants.MotorConstants.leftFollow, MotorType.kBrushless);
  private final SparkMax rightLead = new SparkMax(Constants.MotorConstants.rightLead, MotorType.kBrushless);
  private final SparkMax rightFollow = new SparkMax(Constants.MotorConstants.rightFollow, MotorType.kBrushless);

  public DriveIOSparkMax() {
    // you can't use .addFollower() bc rev and you can't use
    // MotorControllerGroup bc deprecated and so you have to
    // make a config for each motor
    // Settings for all four: limit current to 50 amps to protect the motors and
    // breakers, and use brake mode so the robot stops instead of coasting.
    // The right side is inverted because its motors face the opposite direction.
    SparkMaxConfig globalConfig = new SparkMaxConfig();
    SparkMaxConfig leftLeadConfig = new SparkMaxConfig();
    SparkMaxConfig rightLeadConfig = new SparkMaxConfig();
    SparkMaxConfig leftFollowConfig = new SparkMaxConfig();
    SparkMaxConfig rightFollowConfig = new SparkMaxConfig();
    globalConfig.smartCurrentLimit(50).idleMode(IdleMode.kBrake);
    leftLeadConfig.apply(globalConfig).inverted(false);
    rightLeadConfig.apply(globalConfig).inverted(true);
    leftFollowConfig.apply(globalConfig).follow(leftLead);
    rightFollowConfig.apply(globalConfig).follow(rightLead);
    // Send the settings to each controller and save them so they survive a power cycle
    leftLead.configure(leftLeadConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftFollow.configure(leftFollowConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightLead.configure(rightLeadConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFollow.configure(rightFollowConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    // getAppliedOutput() is -1 to 1, so multiply by battery voltage to get volts
    inputs.leftPositionRotations = leftLead.getEncoder().getPosition();
    inputs.leftVelocityRPM = leftLead.getEncoder().getVelocity();
    inputs.leftAppliedVolts = leftLead.getAppliedOutput() * leftLead.getBusVoltage();
    inputs.leftCurrentAmps = new double[] {leftLead.getOutputCurrent(), leftFollow.getOutputCurrent()};
    inputs.leftTempCelsius = new double[] {leftLead.getMotorTemperature(), leftFollow.getMotorTemperature()};

    inputs.rightPositionRotations = rightLead.getEncoder().getPosition();
    inputs.rightVelocityRPM = rightLead.getEncoder().getVelocity();
    inputs.rightAppliedVolts = rightLead.getAppliedOutput() * rightLead.getBusVoltage();
    inputs.rightCurrentAmps = new double[] {rightLead.getOutputCurrent(), rightFollow.getOutputCurrent()};
    inputs.rightTempCelsius = new double[] {rightLead.getMotorTemperature(), rightFollow.getMotorTemperature()};
  }

  // Only the lead motors are set; the followers copy them automatically
  @Override
  public void setLeft(double speed) {
    leftLead.set(speed);
  }

  @Override
  public void setRight(double speed) {
    rightLead.set(speed);
  }
}
