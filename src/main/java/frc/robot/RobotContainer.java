// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.AutoAlignCommand;
import frc.robot.commands.DriveCommands;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOTalonFX;
import frc.robot.subsystems.shooter.Outtake;
import frc.robot.subsystems.shooter.OuttakeIO;
import frc.robot.subsystems.shooter.OuttakeIOTalonFX;
import frc.robot.subsystems.transfer.Transfer;
import frc.robot.subsystems.transfer.TransferIO;
import frc.robot.subsystems.transfer.TransferIOTalonFX;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // subsystem
  private final Drive drive;
  private final Outtake outtake;
  private final Transfer transfer;
  private final Intake intake;
  //   private final Intake intake;

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);
  //   private final CommandXboxController operator = new CommandXboxController(1);

  // dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    switch (Constants.currentMode) {
      case REAL:

        // Real robot, instantiate hardware IO implementations
        // ModuleIOTalonFX is intended for modules with TalonFX drive, TalonFX turn, and
        // a CANcoder

        drive =
            new Drive(
                new GyroIOPigeon2(),
                new ModuleIOTalonFX(TunerConstants.FrontLeft),
                new ModuleIOTalonFX(TunerConstants.FrontRight),
                new ModuleIOTalonFX(TunerConstants.BackLeft),
                new ModuleIOTalonFX(TunerConstants.BackRight));

        outtake = new Outtake(new OuttakeIOTalonFX());
        intake = new Intake(new IntakeIOTalonFX());
        intake.setTargetPosition(-17.75390625); // 10, 350, -10, -40, 40
        transfer = new Transfer(new TransferIOTalonFX());

        // intake = new Intake(new IntakeIOTalonFX());
        break;
        // The ModuleIOTalonFXS implementation provides an example implementation for
        // TalonFXS controller connected to a CANdi with a PWM encoder. The
        // implementations
        // of ModuleIOTalonFX, ModuleIOTalonFXS, and ModuleIOSpark (from the Spark
        // swerve
        // template) can be freely intermixed to support alternative hardware
        // arrangements.
        // Please see the AdvantageKit template documentation for more information:
        // https://docs.advantagekit.org/getting-started/template-projects/talonfx-swerve-template#custom-module-implementations
        //
        // drive =
        // new Drive(
        // new GyroIOPigeon2(),
        // new ModuleIOTalonFXS(TunerConstants.FrontLeft),
        // new ModuleIOTalonFXS(TunerConstants.FrontRight),
        // new ModuleIOTalonFXS(TunerConstants.BackLeft),
        // new ModuleIOTalonFXS(TunerConstants.BackRight));

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(TunerConstants.FrontLeft),
                new ModuleIOSim(TunerConstants.FrontRight),
                new ModuleIOSim(TunerConstants.BackLeft),
                new ModuleIOSim(TunerConstants.BackRight));

        // prolly no sim (no time)
        outtake = new Outtake(new OuttakeIO() {});
        intake = new Intake(new IntakeIO() {});
        // intake = new Intake(new IntakeIO() {});
        transfer =
            new Transfer(
                new TransferIO() {
                  @Override
                  public void stopMotors() {}

                  @Override
                  public void setMotors(double speed) {}
                });
        break;

      default:
        // Replayed robot, disable IO implementations

        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});

        // replay mode — no real IO, just log replay, so use empty IO implementations
        outtake = new Outtake(new OuttakeIO() {});
        intake = new Intake(new IntakeIO() {});
        transfer =
            new Transfer(
                new TransferIO() {
                  @Override
                  public void stopMotors() {}

                  @Override
                  public void setMotors(double speed) {}
                });
        break;
    }

    // NameCommands.registerCommand("intake in", Commands.run(intake::, transfer));
    NamedCommands.registerCommand(
        "shoot start",
        Commands.sequence(
            Commands.runOnce(outtake::ampScore, outtake), // start outtake immediately
            Commands.waitSeconds(1),
            Commands.runOnce(() -> transfer.forward(), transfer),
            Commands.waitSeconds(6),
            Commands.runOnce(
                () -> {
                  outtake.stop();
                  transfer.stop();
                },
                outtake,
                transfer)));
    NamedCommands.registerCommand(
        "intakeFLOOR",
        Commands.sequence(
            Commands.runOnce(
                () -> {
                  intake.setTargetPosition(-110.0); // 123.2
                  intake.intakeRoller();
                },
                intake), // start outtake immediately
            Commands.waitSeconds(5),
            Commands.runOnce(
                () -> {
                  intake.stopRoller();
                  intake.holdCurrentPosition();
                },
                intake),
            Commands.waitSeconds(4)));
    NamedCommands.registerCommand(
        "intakeDRIVE",
        Commands.sequence(
            Commands.runOnce(
                () -> {
                  intake.setTargetPosition(-70); // 123.2
                },
                intake), // start outtake immediately
            Commands.waitSeconds(0.5),
            Commands.runOnce(
                () -> {
                  intake.holdCurrentPosition();
                },
                intake)));
                
      NamedCommands.registerCommand(
        "shootEIGHT",
        Commands.sequence(
            Commands.runOnce(outtake::ampScore, outtake),
            Commands.runOnce(() -> intake.setTargetPosition(-20), intake),
                // Commands.waitSeconds(1.2),
            Commands.waitSeconds(1),
            Commands.runOnce(transfer::forward, transfer),
            Commands.runOnce(() -> intake.setTargetPosition(-25), intake),
            Commands.run(transfer::forward, transfer),
            Commands.waitSeconds(3.5),
            Commands.runOnce(
                () -> {
                  outtake.stop();
                  transfer.stop();
                  intake.stopRoller();
                },
                outtake,
                transfer, intake)));
    NamedCommands.registerCommand(
        "fifteen outtake",
        Commands.sequence(
            Commands.runOnce(outtake::ampScore, outtake), // start outtake immediately
            Commands.waitSeconds(1),
            Commands.runOnce(() -> transfer.forward(), transfer),
            Commands.waitSeconds(10),
            Commands.runOnce(
                () -> {
                  outtake.stop();
                  transfer.stop();
                },
                outtake,
                transfer)));
    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Set up SysId routines
    autoChooser.addOption(
        "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive));
    autoChooser.addOption(
        "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command, normal field-relative drive
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> -controller.getLeftY(),
            () -> -controller.getLeftX(),
            () -> -controller.getRightX()));

    // Lock to 0° when A button is held
    // controller
    //     .a()
    //     .whileTrue(
    //         DriveCommands.joystickDriveAtAngle(
    //             drive,
    //             () -> -controller.getLeftY(),
    //             () -> -controller.getLeftX(),
    //             () -> Rotation2d.kZero));

    // Switch to X pattern when X button is pressed
    // controller.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

    // // Reset gyro to 0° when B button is pressed
    // controller
    //     .b()
    //     .onTrue(
    //         Commands.runOnce(
    //                 () ->
    //                     drive.setPose(
    //                         new Pose2d(drive.getPose().getTranslation(), Rotation2d.kZero)),
    //                 drive)
    //             .ignoringDisable(true));
    // operator right bumper to test outtake
    controller
        .rightBumper()
        .whileTrue(
            Commands.sequence(
                Commands.runOnce(outtake::scoreWithVision, outtake),
                Commands.runOnce(() -> intake.setTargetPosition(-20), intake),
                Commands.waitSeconds(1.2),
                // 3. Start transfer
                Commands.runOnce(transfer::forward, transfer),

                // 4. NOW move pivot
                Commands.runOnce(() -> intake.setTargetPosition(-25), intake),

                // 5. Keep transfer running while held
                Commands.run(transfer::forward, transfer)))
        .onFalse(
            Commands.runOnce(
                () -> {
                  outtake.stop();
                  transfer.stop();
                  intake.stopRoller();
                },
                outtake,
                transfer));

    /*
    controller
        .leftBumper()
        .whileTrue(
            Commands.sequence(
                Commands.runOnce(outtake::scoreWithVision, outtake),
                Commands.waitSeconds(1.2),
                Commands.run(transfer::forward, transfer)))
        .onFalse(
            Commands.runOnce(
                () -> {
                  outtake.stop();
                  transfer.stop();
                },
                outtake,
                transfer));
      */
    controller
        .rightTrigger()
        .whileTrue(
            Commands.sequence(
                Commands.runOnce(outtake::scoreWithVision, outtake),
                Commands.waitSeconds(1.2),
                Commands.run(transfer::forward, transfer)))
        .onFalse(
            Commands.runOnce(
                () -> {
                  outtake.stop();
                  transfer.stop();
                },
                outtake,
                transfer));

    controller
        .leftBumper()
        .whileTrue(
            new RunCommand(
                () -> {
                  intake.setTargetPosition(-110.0); // 123.32, 115.0, 105
                  intake.intakeRoller();
                },
                intake))
        .onFalse(
            new InstantCommand(
                () -> {
                  intake.stopRoller();
                  intake.holdCurrentPosition();
                },
                intake));
    controller
        .b()
        .whileTrue(
            new RunCommand(
                () -> {
                  intake.setTargetPosition(-30.0); // 65, 62
                },
                intake))
        .onFalse(
            new InstantCommand(
                () -> {
                  intake.stopRoller();
                  intake.holdCurrentPosition();
                },
                intake));

    controller.a().whileTrue(AutoAlignCommand.create(drive));
    // operator
    //     .a()
    //     .whileTrue(Commands.run(outtake::scoreWithVision, outtake))
    //     .onFalse(Commands.runOnce(outtake::stop, outtake));

    // operator
    //     .x()
    //     .whileTrue(Commands.run(transfer::forward, transfer))
    //     .onFalse(Commands.runOnce(transfer::stop, transfer));

    // operator
    //     .b()
    //     .whileTrue(Commands.run(transfer::backward, transfer))
    //     .onFalse(Commands.runOnce(transfer::stop, transfer));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
