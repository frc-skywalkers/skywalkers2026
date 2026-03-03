package frc.robot;

public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = Mode.REAL;

  public static enum Mode {
    REAL,
    SIM,
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

    public static final double INTAKE_VOLTAGE = 10.0;
    public static final double OUTTAKE_VOLTAGE = -8.0;
    public static final double HANDOFF_VOLTAGE = 4.0;

    public static final double JAM_REVERSE_VOLTAGE = -6.0;
    public static final double JAM_REVERSE_TIME = 0.2;
  }

  public static final class TransferConstants {
    // CAN IDs for the transfer motors
    public static final int MOTOR1_ID = 20;
    public static final int MOTOR2_ID = 21;

    // Inversions (set true if motors are mounted opposite directions)
    public static final boolean MOTOR1_INVERTED = false;
    public static final boolean MOTOR2_INVERTED = true;

    // Current limit for both motors
    public static final double STATOR_LIMIT_AMPS = 40.0;

    // Optional: voltage constants for forward/backward if needed
    public static final double FORWARD_VOLTAGE = 12.0;
    public static final double BACKWARD_VOLTAGE = -12.0;
  }
}