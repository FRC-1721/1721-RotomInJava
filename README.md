# 1721-RotomInJava

Rotom rewrite in java

## Logging with AdvantageKit + AdvantageScope

This project uses [AdvantageKit](https://docs.advantagekit.org) to log every sensor input and motor output each loop.
View the data with **AdvantageScope**, which is installed with WPILib 2026 (look for it in the WPILib tools / Start menu).

**Live, on the robot:** connect to the robot's network, open AdvantageScope, then *File → Connect to Robot*.
**Live, in simulation:** run `./gradlew simulateJava`, then *File → Connect to Simulator*.
**After a match:** plug a FAT32 USB stick into the roboRIO before enabling. Logs (`.wpilog`) are written there;
open them in AdvantageScope with *File → Open Log(s)*.

Useful fields: `Drive/*` and `Arm/*` (motor inputs read from hardware), `RealOutputs/Drive/*` and
`RealOutputs/Arm/*` (what the code commanded), and `DriverStation/Joystick0` (driver controller).

**Replay:** set `SimMode` in `Constants.java` to `Mode.REPLAY` and run `./gradlew simulateJava`. AdvantageKit
re-runs the code against a recorded log and writes a new `_sim` log you can compare with the original.
Set it back to `Mode.SIM` when done.
