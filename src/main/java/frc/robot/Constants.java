// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

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

  public static final class IntakeConstants {
    public static final int PIVOT_ID = 10;
    public static final int ROLLER_ID = 11;
    public static final int CANCODER_ID = 12;

    public static final boolean PIVOT_INVERTED = false;
    public static final boolean ROLLER_INVERTED = false;

    public static final double STATOR_LIMIT_AMPS = 40.0;
    public static final double JAM_CURRENT_THRESHOLD = 35.0;

    public static final double STOWED_DEG = 0.0;
    public static final double HANDOFF_DEG = 35.0;
    public static final double DEPLOYED_DEG = 75.0;

    public static final double PIVOT_kP = 60.0;
    public static final double PIVOT_kI = 0.0;
    public static final double PIVOT_kD = 2.0;
    public static final double PIVOT_kG = 0.3;

    public static final double CRUISE_VELOCITY = 60.0;
    public static final double ACCELERATION = 120.0;

    public static final double INTAKE_VOLTAGE = 2.0;
    public static final double OUTTAKE_VOLTAGE = -2.0;
    public static final double HANDOFF_VOLTAGE = 2.0;

    public static final double JAM_REVERSE_VOLTAGE = -2.0;
    public static final double JAM_REVERSE_TIME = 0.2;
  }

  public static final class TransferConstants {
    // CAN IDs for the transfer motors
    public static final int MOTOR1_ID = 60;
    public static final int MOTOR2_ID = 61;

    // Inversions (set true if motors are mounted opposite directions)
    public static final boolean MOTOR1_INVERTED = true;
    public static final boolean MOTOR2_INVERTED = false;

    // Current limit for both motors
    public static final double STATOR_LIMIT_AMPS = 40.0;

    // Optional: voltage constants for forward/backward if needed
    public static final double FORWARD_VOLTAGE = 2.0;
    public static final double BACKWARD_VOLTAGE = -2.0;
  }
}
