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

  public IntakeIOTalonFX() {

    TalonFXConfiguration config = new TalonFXConfiguration();
    pivot.setControl(new NeutralOut()); // <-- important
    CANcoderConfiguration ccConfig = new CANcoderConfiguration();

    cancoder.getConfigurator().apply(ccConfig);

    config.MotorOutput.Inverted =
        PIVOT_INVERTED ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;

    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    config.CurrentLimits.StatorCurrentLimit = STATOR_LIMIT_AMPS;
    config.CurrentLimits.StatorCurrentLimitEnable = true;

    config.Slot0.kP = 11; // start low
    config.Slot0.kI = 0;
    config.Slot0.kD = 0.3; // small damping
    config.Slot0.kG = -0.2; // gravity feedforward

    config.MotionMagic.MotionMagicCruiseVelocity = CRUISE_VELOCITY;
    config.MotionMagic.MotionMagicAcceleration = ACCELERATION;

    config.Feedback.FeedbackRemoteSensorID = CANCODER_ID;
    config.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;

    pivot.getConfigurator().apply(config);
    roller.getConfigurator().apply(config);
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    inputs.pivotPositionDeg = cancoder.getAbsolutePosition().getValueAsDouble() * 360.0;
    inputs.pivotVelocity = pivot.getVelocity().getValueAsDouble();
    inputs.pivotAppliedVolts = pivot.getMotorVoltage().getValueAsDouble();
    inputs.pivotCurrent = pivot.getStatorCurrent().getValueAsDouble();
    double rotations = cancoder.getAbsolutePosition().getValueAsDouble();
    rotations = rotations % 1.0; // keeps it between 0 and 1
  }

  @Override
  public void setPivotPositionDeg(double degrees) {
    double rotations = degrees / 360.0; // multiply by gear ratio
    pivot.setControl(motionMagic.withPosition(rotations));
  }

  @Override
  public void setRollerVoltage(double volts) {
    roller.setControl(new VoltageOut(volts));
  }

  @Override
  public void stopPivot() {

    pivot.stopMotor();
  }
}