/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

private Command Command;
private RobotContainer container;
private final SendableChooser<String> autoChooser = new SendableChooser<String>();

private NetworkTableInstance mjpgSteamer;

  @Override
  public void robotInit() {
    CommandScheduler.getInstance().cancelAll();
    container = new RobotContainer();
    autoChooser.addOption("Differential Drive", "differentialDrive");
    autoChooser.addOption("Mecanum Drive", "mecanumDrive");
    autoChooser.addOption("Absolute Mecanum", "absoluteMec");
    autoChooser.setDefaultOption("Differential Drive", "differentialDrive");
    SmartDashboard.putData("Drive Mode", autoChooser);
  }

  @Override
  public void robotPeriodic() {
    NetworkTableInstance.getDefault()
    .getEntry("/CameraPublisher/PiCamera/streams")
    .setStringArray(new String[]{"mjpeg:http://10.71.30.12:8085/?action=stream"});
  }

  @Override
  public void autonomousInit() {
    CommandScheduler.getInstance().cancelAll();
    Command = container.getAutonomousCommand();
    if (Command != null) {
      Command.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    CommandScheduler.getInstance().cancelAll();
    Command = container.getTeleopCommandDiffer();
    Command.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
    CommandScheduler.getInstance().cancelAll();
    Command = container.getDisableSafetyCommand();
    Command.schedule();
  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();
  }
}

