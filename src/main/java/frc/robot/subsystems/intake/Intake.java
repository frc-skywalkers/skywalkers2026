import static frc.robot.Constants.IntakeConstants.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.*;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {

  public enum IntakeState {
    STOWED,
    DEPLOYED,
    INTAKING,
    OUTTAKING,
    HANDOFF,
    STOPPED
  }

  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

  private IntakeState state = IntakeState.STOWED;

  private final Timer jamTimer = new Timer();
  private boolean reversingForJam = false;

  public Intake(IntakeIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Intake", inputs);

    handleState();
    handleJamDetection();
  }

  private void handleState() {
    switch (state) {
      case STOWED -> {
        io.setPivotPositionDeg(STOWED_DEG);
        io.stopRoller();
      }
      case DEPLOYED -> {
        io.setPivotPositionDeg(DEPLOYED_DEG);
        io.stopRoller();
      }
      case INTAKING -> {
        io.setPivotPositionDeg(DEPLOYED_DEG);
        io.setRollerVoltage(INTAKE_VOLTAGE);
      }
      case OUTTAKING -> {
        io.setPivotPositionDeg(DEPLOYED_DEG);
        io.setRollerVoltage(OUTTAKE_VOLTAGE);
      }
      case HANDOFF -> {
        io.setPivotPositionDeg(HANDOFF_DEG);
        io.setRollerVoltage(HANDOFF_VOLTAGE);
      }
      case STOPPED -> io.stopRoller();
    }
  }

  private void handleJamDetection() {

    if (state == IntakeState.INTAKING && !reversingForJam) {
      if (inputs.rollerCurrent > JAM_CURRENT_THRESHOLD && Math.abs(inputs.rollerVelocity) < 5.0) {

        reversingForJam = true;
        jamTimer.restart();
        io.setRollerVoltage(JAM_REVERSE_VOLTAGE);
      }
    }

    if (reversingForJam) {
      if (jamTimer.hasElapsed(JAM_REVERSE_TIME)) {
        reversingForJam = false;
        io.setRollerVoltage(INTAKE_VOLTAGE);
      }
    }

    inputs.jamDetected = reversingForJam;
  }

  public void setState(IntakeState newState) {
    state = newState;
  }

  // Command factories

  public Command intakeCommand() {
    return run(() -> setState(IntakeState.INTAKING));
  }

  public Command outtakeCommand() {
    return run(() -> setState(IntakeState.OUTTAKING));
  }

  public Command deployCommand() {
    return runOnce(() -> setState(IntakeState.DEPLOYED));
  }

  public Command stowCommand() {
    return runOnce(() -> setState(IntakeState.STOWED));
  }

  public Command handoffCommand() {
    return runOnce(() -> setState(IntakeState.HANDOFF));
  }
}
