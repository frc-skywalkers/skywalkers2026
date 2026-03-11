package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {

  @AutoLog
  public static class IntakeIOInputs {
    public double pivotPositionDeg = 0.0;
    public double pivotVelocity = 0.0;
    public double pivotAppliedVolts = 0.0;
    public double pivotCurrent = 0.0;
  }

  default void updateInputs(IntakeIOInputs inputs) {}

  default void setPivotPositionDeg(double degrees) {}

  default void stopPivot() {}
}
