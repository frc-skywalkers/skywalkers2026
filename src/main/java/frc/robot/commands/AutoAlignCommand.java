package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;

public class AutoAlignCommand {

  public static Command create(Drive drive) {
    return DriveCommands.autoAlign(drive);
  }
}
