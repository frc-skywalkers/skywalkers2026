package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OuttakeConstants;
import org.littletonrobotics.junction.Logger;

public class Outtake extends SubsystemBase {

  private final OuttakeIO io;
  private final OuttakeIOInputsAutoLogged inputs = new OuttakeIOInputsAutoLogged();

  public Outtake(OuttakeIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs(OuttakeConstants.kLoggingPath, inputs);
  }

  // presets
  public void ampScore() {
    io.setVelocityRPM(OuttakeConstants.kAmpScoreRPM);
  }

  public void feed() {
    io.setVelocityRPM(OuttakeConstants.kFeedRPM);
  }

  public void reverse() {
    io.setVelocityRPM(OuttakeConstants.kReverseRPM);
  }

  public void idleHold() {
    io.setVelocityRPM(OuttakeConstants.kIdleHoldRPM);
  }

  public void runPercent(double percent) {
    io.setVoltage(percent * 40.0); // 12.0, 12.5, 13.5, 16, 40
  }

  public void stop() {
    io.stop();
  }
}
