package frc.robot.subsystems.transfer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Transfer extends SubsystemBase {

    private final TransferIO m_io;

    public Transfer(TransferIO io) {
        m_io = io;
    }

    /** Run forward at full speed */
    public void forward() {
        m_io.setMotors(1.0);
    }

    /** Run backward at full speed */
    public void backward() {
        m_io.setMotors(-1.0);
    }

    /** Stop transfer motors */
    public void stop() {
        m_io.stopMotors();
    }

    /** Set custom speed (-1.0 to 1.0) */
    public void setSpeed(double speed) {
        m_io.setMotors(speed);
    }
}