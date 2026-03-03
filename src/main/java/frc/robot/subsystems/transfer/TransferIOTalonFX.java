package frc.robot.subsystems.transfer;

import static frc.robot.Constants.TransferConstants.*;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class TransferIOTalonFX implements TransferIO {

    private final TalonFX motor1 = new TalonFX(MOTOR1_ID);
    private final TalonFX motor2 = new TalonFX(MOTOR2_ID);

    private final VoltageOut voltageControl = new VoltageOut(0);

    public TransferIOTalonFX() {
        // === Motor 1 Config ===
        TalonFXConfiguration motor1Config = new TalonFXConfiguration();
        motor1Config.MotorOutput.Inverted = MOTOR1_INVERTED ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        motor1Config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        motor1Config.CurrentLimits.StatorCurrentLimit = STATOR_LIMIT_AMPS;
        motor1Config.CurrentLimits.StatorCurrentLimitEnable = true;
        motor1.getConfigurator().apply(motor1Config);

        // === Motor 2 Config ===
        TalonFXConfiguration motor2Config = new TalonFXConfiguration();
        motor2Config.MotorOutput.Inverted = MOTOR2_INVERTED ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        motor2Config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        motor2Config.CurrentLimits.StatorCurrentLimit = STATOR_LIMIT_AMPS;
        motor2Config.CurrentLimits.StatorCurrentLimitEnable = true;
        motor2.getConfigurator().apply(motor2Config);
    }

    @Override
    public void setMotors(double speed) {
        motor1.setControl(voltageControl.withOutput(speed * 12.0)); // convert -1.0..1.0 to volts
        motor2.setControl(voltageControl.withOutput(speed * 12.0));
    }

    @Override
    public void stopMotors() {
        motor1.stopMotor();
        motor2.stopMotor();
    }
}