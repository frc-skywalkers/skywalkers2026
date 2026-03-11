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

  public static final class IntakeConstants {
    // motor settings (PLS CHANGE B4 TESTING)
    public static final int PIVOT_ID = 42;
    public static final int ROLLER_ID = 55;
    public static final int CANCODER_ID = 52;

    // inversion settings (IDK IM JS GUESSING PLS TUNE)
    public static final boolean PIVOT_INVERTED = false;
    public static final boolean ROLLER_INVERTED = false;

    // current lims
    public static final double STATOR_LIMIT_AMPS = 40.0;
    public static final double JAM_CURRENT_THRESHOLD = 35.0;

    // pivot (js guessing, pls check w design or sumn to see what they want)
    public static final double STOWED_DEG = 0.0;
    public static final double HANDOFF_DEG = 35.0;
    public static final double DEPLOYED_DEG = 75.0;

    // jam detection nums
    public static final double PIVOT_kP = 60.0;
    public static final double PIVOT_kI = 0.0;
    public static final double PIVOT_kD = 2.0;
    public static final double PIVOT_kG = 0.3; // gravity feedforward
    public static final double CRUISE_VELOCITY = 60.0; // rotations/sec
    public static final double ACCELERATION = 120.0;

    // roller volltages
    public static final double INTAKE_VOLTAGE = 1.0;
    public static final double OUTTAKE_VOLTAGE = -1.0;
    public static final double HANDOFF_VOLTAGE = 4.0;

    // more jam handling constants
    public static final double JAM_REVERSE_VOLTAGE = -6.0;
    public static final double JAM_REVERSE_TIME = 0.2; // seconds
  }
  
  public static final class OuttakeConstants {

    // can
    public static final int MOTOR_ID = 40; // change
    public static final String kCanBus = "rio";

    // motor settings
    public static final InvertedValue MOTOR_INVERTED = InvertedValue.CounterClockwise_Positive;

    public static final NeutralModeValue kNeutralMode = NeutralModeValue.Coast; // test value

    // current limits
    public static final double kStatorCurrentLimit = 100.0; // 60.0, 80
    public static final double kSupplyCurrentLimit = 80.0; // 40.0, 43.0, 60

    // velocity setpoints WILL TUNE FS
    public static final double kAmpScoreRPM = 000.0; // 5000.0, 6000.0, 8000,0, 10000 //6000
    public static final double kFeedRPM = 2400.0; // 1200
    public static final double kReverseRPM = -2000.0;
    public static final double kIdleHoldRPM = 300.0;

    // jam detection
    public static final double kJamCurrentThreshold = 60.0; // 50.0

    // gEAR-ratio:
    public static final double kGearRatio = 0.25;

    // Limelight settings
    public static final String LIMELIGHT_NAME = "limelight";

    // geometry (meters)
    public static final double LIMELIGHT_HEIGHT = 0.4318; // height of camera from floor
    public static final double TARGET_HEIGHT = 1.8288; // april tag height
    public static final double LIMELIGHT_ANGLE_DEG = 10.0; // mounting angle

    // voltage limits
    public static final double kMinVoltage = 4.5;
    public static final double kMaxVoltage = 8.0;

    // logging pth
    public static final String kLoggingPath = "outtake/";
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
