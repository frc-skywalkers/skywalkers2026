package frc.robot.subsystems.intake;

import static frc.robot.Constants.IntakeConstants.*;

import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.controls.*;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.*;

public class IntakeIOTalonFX implements IntakeIO {

  private final TalonFX pivot = new TalonFX(PIVOT_ID);
  private final TalonFX roller = new TalonFX(ROLLER_ID);
  private final CANcoder cancoder = new CANcoder(CANCODER_ID);

  private final MotionMagicVoltage motionMagic = new MotionMagicVoltage(0).withSlot(0);

  private final VoltageOut rollerVoltage = new VoltageOut(0);

  public IntakeIOTalonFX() {

    // === Pivot Config ===
    TalonFXConfiguration pivotConfig = new TalonFXConfiguration();

    pivotConfig.MotorOutput.Inverted =
        PIVOT_INVERTED ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;

    pivotConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    pivotConfig.CurrentLimits.StatorCurrentLimit = STATOR_LIMIT_AMPS;
    pivotConfig.CurrentLimits.StatorCurrentLimitEnable = true;

    pivotConfig.Slot0.kP = PIVOT_kP;
    pivotConfig.Slot0.kI = PIVOT_kI;
    pivotConfig.Slot0.kD = PIVOT_kD;
    pivotConfig.Slot0.kG = PIVOT_kG;

    pivotConfig.MotionMagic.MotionMagicCruiseVelocity = CRUISE_VELOCITY;
    pivotConfig.MotionMagic.MotionMagicAcceleration = ACCELERATION;

    // Use CANcoder as feedback
    pivotConfig.Feedback.FeedbackRemoteSensorID = CANCODER_ID;
    pivotConfig.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;

    pivot.getConfigurator().apply(pivotConfig);

    // === Roller Config ===
    TalonFXConfiguration rollerConfig = new TalonFXConfiguration();

    rollerConfig.MotorOutput.Inverted =
        ROLLER_INVERTED
            ? InvertedValue.Clockwise_Positive
            : InvertedValue.CounterClockwise_Positive;

    rollerConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

    rollerConfig.CurrentLimits.StatorCurrentLimit = STATOR_LIMIT_AMPS;
    rollerConfig.CurrentLimits.StatorCurrentLimitEnable = true;

    roller.getConfigurator().apply(rollerConfig);
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    inputs.pivotPositionDeg = cancoder.getAbsolutePosition().getValueAsDouble() * 360.0;

    inputs.pivotVelocity = pivot.getVelocity().getValueAsDouble();
    inputs.pivotAppliedVolts = pivot.getMotorVoltage().getValueAsDouble();
    inputs.pivotCurrent = pivot.getStatorCurrent().getValueAsDouble();

    inputs.rollerVelocity = roller.getVelocity().getValueAsDouble();
    inputs.rollerAppliedVolts = roller.getMotorVoltage().getValueAsDouble();
    inputs.rollerCurrent = roller.getStatorCurrent().getValueAsDouble();
  }

  @Override
  public void setPivotPositionDeg(double degrees) {
    double rotations = degrees / 360.0;
    pivot.setControl(motionMagic.withPosition(rotations));
  }

  @Override
  public void setRollerVoltage(double volts) {
    roller.setControl(rollerVoltage.withOutput(volts));
  }

  @Override
  public void stopRoller() {
    roller.stopMotor();
  }
}
