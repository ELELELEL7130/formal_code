package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class pneumatics extends SubsystemBase{
    //空壓機
    Compressor compressor = new Compressor(1);
// 電磁閥
    Solenoid sLiftUp =new Solenoid(1);
    Solenoid sLIftDown =new Solenoid(0);
    Solenoid sSpeedUp =new Solenoid(4);
    Solenoid sSpeedDown = new Solenoid(5);
    Solenoid sBallUp =new Solenoid(2);
    Solenoid sBallDown = new Solenoid(3);
    public pneumatics(){
        
    }
}