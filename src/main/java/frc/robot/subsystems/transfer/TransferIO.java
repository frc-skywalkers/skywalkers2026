package frc.robot.subsystems.transfer;

/** Hardware interface for Transfer subsystem */
public interface TransferIO {
    /** Set both transfer motors to a given speed (-1.0 to 1.0) */
    void setMotors(double speed);

    /** Stop both motors */
    void stopMotors();
}