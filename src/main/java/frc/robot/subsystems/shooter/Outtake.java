package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OuttakeConstants;
import frc.robot.util.Limelight;
import org.littletonrobotics.junction.Logger;

public class Outtake extends SubsystemBase {

  private double lastSeenTime = 0;
  private double lastDistance = 0;
  private static final double VISION_TIMEOUT = 1.0;

  private final OuttakeIO io;
  private final OuttakeIOInputsAutoLogged inputs = new OuttakeIOInputsAutoLogged();

  public Outtake(OuttakeIO io) {
    this.io = io;
  }

  private double calculateVoltage(double distance) {
    double banana = 0;
    // example tuning curve
    double voltage;

    if (Limelight.getDistanceMeters() < 2) {
      voltage = 3520;
      banana = 1;
      System.out.println(Limelight.getDistanceMeters());
    } else if (Limelight.getDistanceMeters() < 2.2) {
      voltage = 3570;
      banana = 1;
      System.out.println(Limelight.getDistanceMeters());
    } else if (Limelight.getDistanceMeters() < 2.3) {
      voltage = 3600;
      banana = 1;
      System.out.println(Limelight.getDistanceMeters());
    } else if (Limelight.getDistanceMeters() < 2.45) {
      voltage = 3867;
      banana = 1;
      System.out.println(Limelight.getDistanceMeters());
    } else if (Limelight.getDistanceMeters() < 2.8) {
      voltage = 4305;
      banana = 1;
      System.out.println(Limelight.getDistanceMeters());
    } else if (Limelight.getDistanceMeters() < 3.2) {
      voltage = 4467;
      banana = 1;
      System.out.println(Limelight.getDistanceMeters());
    } else {
      voltage = 4600;
      System.out.println(Limelight.getDistanceMeters());
    }

    return voltage;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs(OuttakeConstants.kLoggingPath, inputs);
    System.out.println(Limelight.getDistanceMeters());
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

  public void scoreWithVision() {

    double currentTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp();

    // If we see a tag, update memory
    if (Limelight.hasTarget()) {
      lastSeenTime = currentTime;
      lastDistance = Limelight.getDistanceMeters();
    }

    // If we haven't seen a tag recently, stop
    if (currentTime - lastSeenTime > VISION_TIMEOUT) {
      stop();
      return;
    }

    // Use last known distance
    double distance = Limelight.getDistanceMeters();

    double voltage = calculateVoltage(lastDistance);

    io.setVelocityRPM(voltage);
  }

  // public void scoreWithVision() {

  //   if (!Limelight.hasTarget()) {
  //     stop();
  //     return;
  //   }

  //   double distance = Limelight.getDistanceMeters();

  //   double voltage = calculateVoltage(distance);

  //   io.setVelocityRPM(voltage);
  // }

  public void runPercent(double percent) {
    io.setVoltage(percent * 12.0); // 12.0, 12.5, 13.5, 16, 40
  }

  public void stop() {
    io.stop();
  }
}
