package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants.OuttakeConstants;

public class OuttakeIOTalonFX implements OuttakeIO {

  private final TalonFX motor = new TalonFX(OuttakeConstants.MOTOR_ID);

  private final VoltageOut voltageRequest = new VoltageOut(0);
  private final VelocityVoltage velocityRequest = new VelocityVoltage(0);

  private double setpointRPM = 0.0;

  public OuttakeIOTalonFX() {
    TalonFXConfiguration config = new TalonFXConfiguration();

    config.MotorOutput.Inverted = OuttakeConstants.MOTOR_INVERTED;
    config.MotorOutput.NeutralMode = OuttakeConstants.kNeutralMode;

    config.CurrentLimits.StatorCurrentLimit = OuttakeConstants.kStatorCurrentLimit;
    config.CurrentLimits.StatorCurrentLimitEnable = true;

    config.CurrentLimits.SupplyCurrentLimit = OuttakeConstants.kSupplyCurrentLimit;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;

    // Basic velocity PID (starting values — will tune later)
    config.Slot0.kP = 0.16;
    config.Slot0.kI = 0.0; // 0
    config.Slot0.kD = 0.000005; // 0
    config.Slot0.kV = 0.115;

    motor.getConfigurator().apply(config);
  }

  @Override
  public void updateInputs(OuttakeIOInputs inputs) {
    inputs.velocityRPM = motor.getVelocity().getValueAsDouble() * 60.0;
    inputs.appliedVolts = motor.getMotorVoltage().getValueAsDouble();
    inputs.supplyCurrentAmps = motor.getSupplyCurrent().getValueAsDouble();
    inputs.statorCurrentAmps = motor.getStatorCurrent().getValueAsDouble();
    inputs.temperatureCelsius = motor.getDeviceTemp().getValueAsDouble();
    inputs.setpointRPM = setpointRPM;
  }

  @Override
  public void setVelocityRPM(double rpm) {
    setpointRPM = rpm;
    motor.setControl(velocityRequest.withVelocity(rpm / 60.0));
  }

  @Override
  public void setVoltage(double volts) {
    setpointRPM = 0.0;
    motor.setControl(voltageRequest.withOutput(volts));
  }

  @Override
  public void stop() {
    setpointRPM = 0.0;
    motor.stopMotor();
  }
}
