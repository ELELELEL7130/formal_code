package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class gatherer extends SubsystemBase{
    WPI_TalonSRX gatherer = new WPI_TalonSRX(7);
    //WPI_TalonSRX masterSideGatherer = new WPI_TalonSRX(10);
    //WPI_TalonSRX slaveSideGatherer = new WPI_TalonSRX(11);
    WPI_TalonSRX conveyer = new WPI_TalonSRX(14);


    public gatherer(){  
        gatherer.setInverted(true);
        //masterSideGatherer.setInverted(true);
        //slaveSideGatherer.setInverted(false);
        conveyer.setInverted(true);
        //slaveSideGatherer.follow(masterSideGatherer);
    }

    public void convey(double output){
        conveyer.set(ControlMode.PercentOutput, output);
        //masterSideGatherer.set(ControlMode.PercentOutput, output);
    }

    public void gatherBall(double output){
        //masterSideGatherer.set(ControlMode.PercentOutput, output);
        gatherer.set(ControlMode.PercentOutput, output);
    }

}