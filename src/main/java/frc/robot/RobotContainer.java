package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.transfer.Transfer;
import frc.robot.subsystems.transfer.TransferIOTalonFX;

public class RobotContainer {

  // Subsystem
  private final Transfer transfer;

  // Timer to delay start
  private final Timer timer = new Timer();

  public RobotContainer() {
    // Instantiate Transfer subsystem
    transfer = new Transfer(new TransferIOTalonFX());

    // Reset and start the timer
    timer.reset();
    timer.start();

    // Default command: wait 3 seconds, then run forward continuously
    transfer.setDefaultCommand(
        Commands.run(
            () -> {
              if (timer.get() >= 10.0) { // wait 3 seconds
                transfer.forward();
              }
            },
            transfer));
  }

  /** Autonomous command (none) */
  public Command getAutonomousCommand() {
    return null;
  }
}
