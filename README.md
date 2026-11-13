# 1721-RotomInJava

Rotom rewrite in java

## Logging with AdvantageKit + AdvantageScope

This project uses [AdvantageKit](https://docs.advantagekit.org) to log every sensor input and motor output each loop.
View the data with **AdvantageScope**, which is installed with WPILib 2026 (look for it in the WPILib tools / Start menu).

**Live, on the robot:** connect to the robot's network, open AdvantageScope, then *File → Connect to Robot*.
**Live, in simulation:** run `./gradlew simulateJava`, then *File → Connect to Simulator*.
Each sim run also saves a log to the `logs/` folder in this project (ignored by git).
**After a match:** plug a FAT32 USB stick into the roboRIO before enabling. Logs (`.wpilog`) are written there;
open them in AdvantageScope with *File → Open Log(s)*.

Useful fields: `Drive/*` and `Arm/*` (motor inputs read from hardware), `RealOutputs/Drive/*` and
`RealOutputs/Arm/*` (what the code commanded), and `DriverStation/Joystick0` (driver controller).

### Seeing the robot on the field

1. Start the sim (`./gradlew simulateJava`) and set it to **Teleoperated**.
2. In AdvantageScope, *File → Connect to Simulator*.
3. Click **+** to add a **3D Field** tab and pick the 2026 field.
4. Drag `RealOutputs/Odometry/Robot` from the sidebar onto the **Poses** area, and choose a robot model (e.g. KitBot).
5. Drive. A **2D Field** tab gives a top-down view the same way.

The robot's position is worked out from the wheel encoders, using the gear ratio, wheel size and track width in
`Constants.DriveConstants`. These are kit-of-parts defaults, so measure Rotom and update them. On the real robot
the heading comes from the navX gyro (`Drive/Gyro/*` in AdvantageScope; `Connected` should be true). The simulator
has no gyro, so there the heading comes from the wheels too. Set `NavXConnection` in Constants to match how the
navX is plugged in (default: MXP port, SPI).
In sim the robot curves right when driving "straight" because `DampenLeft` slows the left side to correct a
drift on the real robot.

**Replay:** set `SimMode` in `Constants.java` to `Mode.REPLAY` and run `./gradlew simulateJava`. AdvantageKit
re-runs the code against a recorded log and writes a new `_sim` log you can compare with the original.
Set it back to `Mode.SIM` when done.
