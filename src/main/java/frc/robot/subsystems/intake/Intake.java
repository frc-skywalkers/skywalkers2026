package frc.robot.subsystems.intake;

import static frc.robot.Constants.IntakeConstants.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {

  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

  public Intake(IntakeIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Intake", inputs);
  }

  public void intakePosition() {
    io.setPivotPositionDeg(73);
  }

  public void drivePosition() {
    io.setPivotPositionDeg(35);
    System.out.println(inputs.pivotPositionDeg);
  }

  public void stowPosition() {
    io.setPivotPositionDeg(108);
    System.out.println(inputs.pivotPositionDeg);
  }

  public void stop() {
    io.stopPivot();
    System.out.println(inputs.pivotPositionDeg);
  }
}
