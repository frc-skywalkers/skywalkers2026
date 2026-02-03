// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.vision;

/** Add your docs here. */
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class LimelightScan extends SubsystemBase {

  private static final String LIMELIGHT_NAME = "limelight";

  public LimelightScan() {}

  /**
   * @param headingDeg //comes from gyro
   */
  public void updateRobotOrientation(double headingDeg) {
    LimelightHelpers.SetRobotOrientation(
        LIMELIGHT_NAME,
        headingDeg,
        0,
        0, // pitch, roll
        0,
        0,
        0 // angular rates
        );
  }

  /**
   * @return Robot pose from Limelight, or null if no valid target
   */
  public Pose3d getBotPose() {
    if (!hasTarget()) {
      return null;
    }

    Alliance alliance = DriverStation.getAlliance().orElse(Alliance.Blue);

    if (alliance == Alliance.Red) {
      return LimelightHelpers.getBotPose3d_wpiRed(LIMELIGHT_NAME);
    } else {
      return LimelightHelpers.getBotPose3d_wpiBlue(LIMELIGHT_NAME);
    }
  }

  public double getBotPoseX() {
    Pose3d pose = getBotPose();
    return pose != null ? pose.getX() : -1;
  }

  public double getBotPoseY() {
    Pose3d pose = getBotPose();
    return pose != null ? pose.getY() : -1;
  }

  public double getDistanceToTarget(double targetX, double targetY) {
    Pose3d pose = getBotPose();
    if (pose == null) return -1;

    return Math.hypot(targetX - pose.getX(), targetY - pose.getY());
  }

  public void setPipeline(int pipelineNumber) {
    LimelightHelpers.setPipelineIndex(LIMELIGHT_NAME, pipelineNumber);
  }

  public int getCurrentPipeline() {
    return (int) LimelightHelpers.getCurrentPipelineIndex(LIMELIGHT_NAME);
  }

  public boolean hasTarget() {
    return LimelightHelpers.getTV(LIMELIGHT_NAME);
  }

  public double getTx() {
    return hasTarget() ? LimelightHelpers.getTX(LIMELIGHT_NAME) : 1000;
  }

  public double getTy() {
    return hasTarget() ? LimelightHelpers.getTY(LIMELIGHT_NAME) : 1000;
  }

  public double getTa() {
    return hasTarget() ? LimelightHelpers.getTA(LIMELIGHT_NAME) : 0;
  }

  // @return AprilTag ID, or -1 if no tag visible
  public int getID() {
    if (!hasTarget()) return -1;
    return (int) LimelightHelpers.getFiducialID(LIMELIGHT_NAME);
  }

  /*
   * Camera space convention (meters):
   * X = right
   * Y = down
   * Z = forward
   */

  public double getTargetPoseZ_CameraSpace() {
    if (!hasTarget()) return -1;
    return LimelightHelpers.getTargetPose3d_CameraSpace(LIMELIGHT_NAME).getZ();
  }

  public double getTargetPoseX_CameraSpace() {
    if (!hasTarget()) return -1;
    return LimelightHelpers.getTargetPose3d_CameraSpace(LIMELIGHT_NAME).getX();
  }

  public double getTargetPoseY_CameraSpace() {
    if (!hasTarget()) return -1;
    return LimelightHelpers.getTargetPose3d_CameraSpace(LIMELIGHT_NAME).getY();
  }
}
