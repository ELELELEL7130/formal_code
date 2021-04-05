package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.RoboMath;

public class shooter extends SubsystemBase{
    WPI_TalonFX masterShooter = new WPI_TalonFX(5);
    WPI_TalonFX slaveShooter = new WPI_TalonFX(6);
    WPI_TalonSRX aimVert = new WPI_TalonSRX(0);
    WPI_TalonSRX aimHori = new WPI_TalonSRX(8);
    WPI_TalonSRX ballup = new WPI_TalonSRX(13);
    /*DigitalInput limit1 = new DigitalInput(0);
    DigitalInput limit2 = new DigitalInput(1);*/

    PIDController turn = new PIDController(Const.turnKp, Const.turnKi, Const.turnKd);

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    public shooter(){
        masterShooter.setInverted(false);
        slaveShooter.setInverted(true);
        aimVert.setInverted(true);
        aimHori.setInverted(false);
        ballup.setInverted(false);

        slaveShooter.follow(masterShooter);

        masterShooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
        masterShooter.setSelectedSensorPosition(0, 0, 30);
        masterShooter.selectProfileSlot(0, 0);
        masterShooter.config_kP(0, 0.3);
        masterShooter.config_kI(0, 0.0);
        masterShooter.config_kD(0, 0.0);
    
        masterShooter.configPeakOutputForward(1, 30);
        masterShooter.configPeakOutputReverse(-1, 30);
    
        aimVert.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        aimVert.setSelectedSensorPosition(0, 0, 30);

        aimVert.selectProfileSlot(0, 0);
        aimVert.config_kP(0, 0.3);
        aimVert.config_kI(0, 0.0);
        aimVert.config_kD(0, 0.0);
    
        aimVert.configPeakOutputForward(0.3, 30);
        aimVert.configPeakOutputReverse(-0.3, 30);

        aimHori.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        aimHori.setSelectedSensorPosition(0, 0, 30);
    
        aimHori.configPeakOutputForward(0.3, 30);
        aimHori.configPeakOutputReverse(-0.3, 30);

        turn.setSetpoint(0);
    }

    public double getError(){
        return tx.getDouble(Const.limelightDefault);
    }

    public void turnTurret(double output){
        /*if (limit1.get() == true || output>) aimVert.setVoltage(0);
        else*/ aimVert.set(ControlMode.PercentOutput, output);
    }

    public double getdistance(){
        return ty.getDouble(Const.limelightDefault);
    }

    public void shootOutput(double velocity){
        masterShooter.set(ControlMode.Velocity, velocity);
    }

    public PIDController getController(){
        return turn;
    }

    public boolean aimEnd() {
        return false;
    }

    public boolean autonomousShoot(){
        return true;
    }

    public double getVertPosition(){
        return RoboMath.tickstoAngle(aimVert.getSelectedSensorPosition()) / 7.5;
    }

    public void ballUP(double speed){
        ballup.set(ControlMode.PercentOutput, speed);
    }
}