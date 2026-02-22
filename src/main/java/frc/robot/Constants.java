// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static final class OuttakeConstants {

    // can
    public static final int MOTOR_ID = 40; // change
    public static final String kCanBus = "rio";

    // motor settings
    public static final InvertedValue MOTOR_INVERTED = InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue kNeutralMode = NeutralModeValue.Coast; // test value

    // current limits
    public static final double kStatorCurrentLimit = 100.0; // 60.0, 80
    public static final double kSupplyCurrentLimit = 80.0; // 40.0, 43.0, 60

    // velocity setpoints WILL TUNE FS
    public static final double kAmpScoreRPM = 6000.0; // 5000.0, 6000.0, 8000,0, 10000
    public static final double kFeedRPM = 2400.0; // 1200
    public static final double kReverseRPM = -2000.0;
    public static final double kIdleHoldRPM = 300.0;

    // jam detection
    public static final double kJamCurrentThreshold = 60.0; // 50.0

    // gEAR-ratio:
    public static final double kGearRatio = 0.25;

    // logging pth
    public static final String kLoggingPath = "outtake/";
  }
}
