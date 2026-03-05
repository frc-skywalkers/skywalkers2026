package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OuttakeConstants;
import frc.robot.util.Limelight;
import org.littletonrobotics.junction.Logger;

public class Outtake extends SubsystemBase {

  private final OuttakeIO io;
  private final OuttakeIOInputsAutoLogged inputs = new OuttakeIOInputsAutoLogged();

  public Outtake(OuttakeIO io) {
    this.io = io;
  }

  private static final int DISTANCE_BUFFER_SIZE = 5;
  private final double[] distanceBuffer = new double[DISTANCE_BUFFER_SIZE];
  private int bufferIndex = 0;
  private boolean bufferFilled = false;

  private double getSmoothedDistance() {
    if (Limelight.hasTarget()) {
      distanceBuffer[bufferIndex] = Limelight.getDistanceMeters();
      bufferIndex = (bufferIndex + 1) % DISTANCE_BUFFER_SIZE;
      if (bufferIndex == 0) bufferFilled = true;
    }

    // calculate average of valid entries
    int size = bufferFilled ? DISTANCE_BUFFER_SIZE : bufferIndex;
    double sum = 0;
    for (int i = 0; i < size; i++) sum += distanceBuffer[i];
    return (size > 0) ? sum / size : 2.0; // default fallback distance if no data
  }

  private double calculateVoltage(double distance) {

    // example tuning curve
    double voltage;

    if (distance < 3.1242) {
      voltage = 5.88;
    } else if (distance < 3.8) {
      voltage = 9;
    } else if (distance < 4.4) {
      voltage = 12;
    } else {
      voltage = 2;
    }

    return voltage;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs(OuttakeConstants.kLoggingPath, inputs);
  }

  // presets
  public void ampScore() {
    io.setVoltage(5.8);
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

  public void scoreWithVision() {
    // Always use smoothed distance
    double distance = getSmoothedDistance();

    double voltage = calculateVoltage(distance);

    io.setVoltage(voltage);
  }

  public void runPercent(double percent) {
    io.setVoltage(percent * 40.0); // 12.0, 12.5, 13.5, 16, 40
  }

  public void stop() {
    io.stop();
  }
}
