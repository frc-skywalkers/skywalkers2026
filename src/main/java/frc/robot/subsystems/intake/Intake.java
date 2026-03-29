package frc.robot.subsystems.intake;

import static frc.robot.Constants.IntakeConstants.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

  // Track hold position
  private double holdPositionDeg = 0.0; // -162
  private boolean isHolding = true;

  public Intake(IntakeIO io) {
    this.io = io;
    io.updateInputs(inputs);
    holdPositionDeg = inputs.pivotPositionDeg; // start holding at current
    io.setPivotPositionDeg(holdPositionDeg);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);

    // If no button is commanding, hold last position
    if (isHolding) {
      io.setPivotPositionDeg(holdPositionDeg);
    }

    Logger.processInputs("Intake", inputs);
    System.out.println("Pivot Degrees: " + inputs.pivotPositionDeg);
    System.out.println("Hold Position: " + holdPositionDeg);
  }
  // Spins rollers at intake voltage
  public void intakeRoller() {
    io.setRollerVoltage(IntakeConstants.INTAKE_VOLTAGE); // 4 volts
  }

  // Stops rollers
  public void stopRoller() {
    io.setRollerVoltage(0.0);
  }

  // Combined: move pivot + intake roller
  public void movePivotAndIntake(double degrees) {
    setTargetPosition(degrees);
    intakeRoller();
  }

  public void setTargetPosition(double degrees) {
    // holdPositionDeg = 360 + degrees;
    holdPositionDeg = degrees;
    // io.setPivotPositionDeg(Math.abs(360 + degrees));
    io.setPivotPositionDeg(degrees);
    isHolding = true; // start holding after motion
  }

  public void holdCurrentPosition() {
    io.updateInputs(inputs);
    holdPositionDeg = inputs.pivotPositionDeg;
    // io.setPivotPositionDeg(360 + holdPositionDeg);
    io.setPivotPositionDeg(holdPositionDeg);
  }

  public void stop() {
    holdPositionDeg = 35;
    io.setPivotPositionDeg(holdPositionDeg);
    holdCurrentPosition(); // replace stop with active hold
  }
}
