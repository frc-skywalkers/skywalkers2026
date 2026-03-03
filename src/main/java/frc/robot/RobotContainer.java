package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.transfer.Transfer;
import frc.robot.subsystems.transfer.TransferIOTalonFX;

public class RobotContainer {


  private final Transfer transfer;


  private final CommandXboxController controller = new CommandXboxController(0);

  public RobotContainer() {

    transfer = new Transfer(new TransferIOTalonFX());

    // Configure button bindings
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // Y button = forward
    controller.y().whileTrue(Commands.run(transfer::forward, transfer));

    // Right bumper = backward
    controller.rightBumper().whileTrue(Commands.run(transfer::backward, transfer));

    // Left bumper = stop
    controller.leftBumper().onTrue(Commands.runOnce(transfer::stop, transfer));
  }


  public Command getAutonomousCommand() {
    return null;
  }
}