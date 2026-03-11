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

    double[] pose =
        NetworkTableInstance.getDefault()
            .getTable("limelight")
            .getEntry("targetpose_robotspace")
            .getDoubleArray(new double[6]);

    double x = pose[0]; // forward/back from robot
    double y = pose[1]; // left/right
    double z = pose[2]; // up/down

    return Math.sqrt(x * x + y * y + z * z);
  }
}
