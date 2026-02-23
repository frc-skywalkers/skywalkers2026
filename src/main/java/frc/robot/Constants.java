// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  // public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;
  public static final Mode currentMode = Mode.REAL;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static final class IntakeConstants {
    // CAN IDs (placeholder — tune later)
    public static final int PIVOT_ID = 10;
    public static final int ROLLER_ID = 11;
    public static final int CANCODER_ID = 12;

    // Inversions (tune later)
    public static final boolean PIVOT_INVERTED = false;
    public static final boolean ROLLER_INVERTED = false;

    // Current Limits
    public static final double STATOR_LIMIT_AMPS = 40.0;
    public static final double JAM_CURRENT_THRESHOLD = 35.0;

    // Pivot Angles (degrees)
    public static final double STOWED_DEG = 0.0;
    public static final double HANDOFF_DEG = 35.0;
    public static final double DEPLOYED_DEG = 75.0;

    // Motion Magic Config (placeholder values — tune)
    public static final double PIVOT_kP = 60.0;
    public static final double PIVOT_kI = 0.0;
    public static final double PIVOT_kD = 2.0;
    public static final double PIVOT_kG = 0.3; // gravity feedforward
    public static final double CRUISE_VELOCITY = 60.0; // rotations/sec
    public static final double ACCELERATION = 120.0;

    // Roller Voltages
    public static final double INTAKE_VOLTAGE = 10.0;
    public static final double OUTTAKE_VOLTAGE = -8.0;
    public static final double HANDOFF_VOLTAGE = 4.0;

    // Jam handling
    public static final double JAM_REVERSE_VOLTAGE = -6.0;
    public static final double JAM_REVERSE_TIME = 0.2; // seconds
  }
}
