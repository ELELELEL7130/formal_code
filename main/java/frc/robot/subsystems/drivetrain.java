/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C.Port;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.RoboMath;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
/**
 * Add your docs here.
 */
public class drivetrain extends SubsystemBase {
    WPI_TalonFX leftMaster = new WPI_TalonFX(3);
    WPI_TalonFX rightMaster = new WPI_TalonFX(4);
    WPI_TalonFX leftSlave = new WPI_TalonFX(2);
    WPI_TalonFX rightSlave = new WPI_TalonFX(1);

    //public MecanumDrive mecDrive = new MecanumDrive(leftMaster, leftSlave, rightMaster, rightSlave);

    private SpeedControllerGroup left = new SpeedControllerGroup(leftMaster, leftSlave);
    private SpeedControllerGroup right = new SpeedControllerGroup(rightMaster, rightSlave);
    public DifferentialDrive drive = new DifferentialDrive(left, right);
    
    private AHRS gyro = new AHRS(SPI.Port.kMXP);
    //private ADXRS450_Gyro gyroBp = new ADXRS450_Gyro(SPI.Port.kMXP);
    
    private Pose2d position = new Pose2d(0.0, 0.0, getHeading());
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Const.Ks, Const.Kv, Const.Ka);
    

    private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Const.TRACK_WIDTH);
    private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getHeading(),position);
    
    private PIDController leftPidController = new PIDController(Const.Kp, Const.Ki,Const.Kd); // .835
    private PIDController rightPidController = new PIDController(Const.Kp, Const.Ki,Const.Kd); // .835

    private PIDController turnController = new PIDController(Const.turnKp, Const.turnKi, Const.turnKd);
    //第一個kp,二:ki,三:kd
    
    public drivetrain(){
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        leftMaster.setSelectedSensorPosition(0);       
        rightMaster.setSensorPhase(false);

        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        rightMaster.setSelectedSensorPosition(0);       
        rightMaster.setSensorPhase(true);

        leftMaster.setInverted(false);
        leftSlave.setInverted(false);
        rightMaster.setInverted(false);
        rightSlave.setInverted(false);

        leftMaster.setExpiration(0.1);
        leftSlave.setExpiration(0.1);
        rightMaster.setExpiration(0.1);
        rightSlave.setExpiration(0.1);

        /*gyroBp.calibrate();
        gyroBp.reset();*/
        gyro.reset();
    }
    
    public Rotation2d getHeading(){
        return Rotation2d.fromDegrees(gyro.getAngle());
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        return new DifferentialDriveWheelSpeeds(RoboMath.ticksToMeters(leftMaster.getSelectedSensorVelocity(0) * 10),
                RoboMath.ticksToMeters(rightMaster.getSelectedSensorVelocity(0) * 10));
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("gyro", gyro.getAngle());
        position = odometry.update(getHeading(), RoboMath.ticksToMeters(leftMaster.getSelectedSensorPosition(0)), RoboMath.ticksToMeters(rightMaster.getSelectedSensorPosition(0)));
        SmartDashboard.putNumber("posi_X", position.getTranslation().getX());
        SmartDashboard.putNumber("posi_Y", position.getTranslation().getY());
        SmartDashboard.putNumber("leftrear", leftSlave.get());
        SmartDashboard.putNumber("leftfront", leftMaster.getSelectedSensorPosition());
        SmartDashboard.putNumber("rightrear", rightSlave.get());
        SmartDashboard.putNumber("rightfront", rightMaster.getSelectedSensorPosition());
        SmartDashboard.putNumber("brake", rightSlave.getBusVoltage());
    }

    public Pose2d getposition(){
        return position;
    }
    public DifferentialDriveKinematics getKinematics(){
        return kinematics;
    }
    public PIDController getLeftPIDController() {
        return leftPidController;
    }

    public PIDController getRightPIDController() {
        return rightPidController;
    }

    public PIDController getTurnPIDController() {
        return turnController;
    }

    public SimpleMotorFeedforward getfFeedforward(){
        return feedforward;
    }

    public void setVolts(double leftVolts, double rightVolts) {
        // System.out.println("position " + position.getTranslation().getX() + " y " + position.getTranslation().getY()
        //         + " Angle " + getHeading());

        // System.out.println("left " + leftMaster.getSelectedSensorPosition(0) + " right " + rightMaster.getSelectedSensorPosition(0));

        // System.out.println("left speed: " + RoboMath.ticksToMeters(leftMaster.getSelectedSensorVelocity(0) * 10) + "right speed: " + RoboMath.ticksToMeters(rightMaster.getSelectedSensorVelocity(0) * 10));
        drive.feed();
        left.set(leftVolts / 12);
        right.set(rightVolts / 12);
    }

    public AHRS getGyro(){
        return gyro;
    }

    public double getAngle(){
        return gyro.getAngle();
    }

    public double getRightVelocity(){
        return leftMaster.getSelectedSensorVelocity();
    }

    public double getLeftVelocity(){
        return -rightMaster.getSelectedSensorVelocity();
    }

    public void initiallize(){
        leftMaster.setSelectedSensorPosition(0);
        rightMaster.setSelectedSensorPosition(0);
        gyro.reset();
    }
}
