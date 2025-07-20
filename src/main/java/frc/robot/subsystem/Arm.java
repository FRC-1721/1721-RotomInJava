package frc.robot.subsystem;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase{
  private final SparkMax claw = new SparkMax(Constants.MotorConstants.claw, MotorType.kBrushless);
  private final SparkMax ladder = new SparkMax(Constants.MotorConstants.ladder, MotorType.kBrushless);
  private final SparkMax elevator = new SparkMax(Constants.MotorConstants.elevator, MotorType.kBrushless);

  public Arm() {}

  @Override
  public void periodic() {}

  /**
   * This is the spool motor, it controls
   * how much rope we let out.
   * @param speed Motor speed
   */
  public void extension(double speed) {
    elevator.set(speed);
  }

  /**
   * This is the lead screw motor, it controls
   * where on the screw the fulcrum of the lift
   * is
   * @param speed Motor speed
   */
  public void ascent(double speed) {
    ladder.set(speed);
  }

  public void grab(double speed) {
    claw.set(speed);
  }
}
