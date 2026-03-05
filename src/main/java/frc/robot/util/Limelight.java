package frc.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants.OuttakeConstants;

public class Limelight {

  private static NetworkTable table =
      NetworkTableInstance.getDefault().getTable(OuttakeConstants.LIMELIGHT_NAME);

  public static boolean hasTarget() {
    return table.getEntry("tv").getDouble(0) == 1;
  }

  public static double getTY() {
    return table.getEntry("ty").getDouble(0);
  }

  public static double getDistanceMeters() {

    double ty = getTY();

    double angle = Math.toRadians(OuttakeConstants.LIMELIGHT_ANGLE_DEG + ty);

    double distance =
        (OuttakeConstants.TARGET_HEIGHT - OuttakeConstants.LIMELIGHT_HEIGHT) / Math.tan(angle);

    return distance;
  }
}
