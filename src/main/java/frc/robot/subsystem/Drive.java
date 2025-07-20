package frc.robot.subsystem;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
  private final SparkMax leftLead = new SparkMax(Constants.MotorConstants.leftLead, MotorType.kBrushless);
  private final SparkMax leftFollow = new SparkMax(Constants.MotorConstants.leftFollow, MotorType.kBrushless);
  private final SparkMax rightLead = new SparkMax(Constants.MotorConstants.rightLead, MotorType.kBrushless);
  private final SparkMax rightFollow = new SparkMax(Constants.MotorConstants.rightFollow, MotorType.kBrushless);

  // DifferentialDrive constructor takes DoubleConsumer x2,
  // DoubleConsumer is just a function that takes in a double
  private final DifferentialDrive robotDrive = new DifferentialDrive(
    (double x) -> {leftLead.set(x * Constants.DriveConstants.DampenLeft);},
    (double x) -> {rightLead.set(x * Constants.DriveConstants.DampenRight);}
  );

  public Drive() {
    // you can't use .addFollower() bc rev and you can't use
    // MotorControllerGroup bc deprecated and so you have to
    // make a config for each motor
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
    leftLead.configure(leftLeadConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftFollow.configure(leftFollowConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightLead.configure(rightLeadConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFollow.configure(rightFollowConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {}

  public void setMotors(double leftSpeed, double rightSpeed) {
    leftLead.set(leftSpeed);
    rightLead.set(rightSpeed);
  }

  public void arcadeDrive(double velocity, double rotation) {
    robotDrive.arcadeDrive(velocity, rotation);
  }

  @Override
  public void simulationPeriodic() {}
}
