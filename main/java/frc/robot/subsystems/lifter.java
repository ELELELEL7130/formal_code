package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class lifter extends SubsystemBase{
    WPI_TalonSRX mLiftHori = new WPI_TalonSRX(15);
    
    WPI_TalonSRX mLiftVertMaster = new WPI_TalonSRX(12);       
    WPI_TalonSRX mLiftVertSlave = new WPI_TalonSRX(9);

    Servo braker = new Servo(0);

    //DigitalInput limit = new DigitalInput(1);

    public lifter(){
        mLiftVertMaster.setInverted(true);
        mLiftVertSlave.setInverted(false);
        mLiftVertSlave.follow(mLiftVertMaster);
        braker.setBounds(2.4, 0, 0, 0, 0.9);
    }

    public void liftUP(double input){
        //if (limit.get() == true && input<0)  mLiftVertMaster.setVoltage(0);
        //else 
        mLiftVertMaster.set(ControlMode.PercentOutput, input);
    }

    public void liftSideWay(double input){
        mLiftHori.set(ControlMode.PercentOutput, input);
    }

    public void brake(double input){

        braker.setPosition(input);
    }
}