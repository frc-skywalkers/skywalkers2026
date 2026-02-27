package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {

  @AutoLog
  public static class IntakeIOInputs {
    public double pivotPositionDeg = 0.0;
    public double pivotVelocity = 0.0;
    public double pivotAppliedVolts = 0.0;
    public double pivotCurrent = 0.0;

    public double rollerVelocity = 0.0;
    public double rollerAppliedVolts = 0.0;
    public double rollerCurrent = 0.0;

    public boolean jamDetected = false;
  }

  default void updateInputs(IntakeIOInputs inputs) {}

  default void setPivotPositionDeg(double degrees) {}

  default void setRollerVoltage(double volts) {}

  default void stopRoller() {}
}
