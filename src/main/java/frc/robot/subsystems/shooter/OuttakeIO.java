package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface OuttakeIO {

  @AutoLog
  class OuttakeIOInputs {
    public double velocityRPM = 0.0;
    public double appliedVolts = 0.0;
    public double supplyCurrentAmps = 0.0;
    public double statorCurrentAmps = 0.0;
    public double temperatureCelsius = 0.0;

    // control for jam detection / tuning
    public double setpointRPM = 0.0;
  }

  // update logging inputs
  default void updateInputs(OuttakeIOInputs inputs) {}

  // set the shooter wheel velocity in RPM
  default void setVelocityRPM(double velocityRPM) {}

  // run the shooter at specified voltage
  default void setVoltage(double volts) {}

  // stop the shooter
  default void stop() {}
}
