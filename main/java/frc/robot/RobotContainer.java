/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

public class RobotContainer {

    private drivetrain drive = new drivetrain();
    private shooter shooter = new shooter();
    private shooter aimer = new shooter();
    private gatherer gatherer = new gatherer();
    private lifter lift = new lifter();
    private OI oi = new OI();

    public RobotContainer(){
        drive.setDefaultCommand(new Differential(drive.drive, oi::yAxis, oi::zRotation, drive));
    }
    //自主時跑的程式
    public Command getAutonomousCommand(){
        drive.initiallize();
        TrajectoryConfig config = new TrajectoryConfig(2,2);
        //第一個:在trajectory(路徑)中最快的速度.
        //第二個:在trajectory(路徑)中最快的加速度.
        config.setKinematics(drive.getKinematics());

        String trajectoryJSON = "paths/output/test.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
        Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        
        ParallelRaceGroup Command = new ParallelRaceGroup(
            new SequentialCommandGroup(
                new RamseteCommand(
                    trajectory,
                    drive::getposition,
                    new RamseteController(2,0.7), drive.getfFeedforward(),
                    drive.getKinematics(),
                    drive::getWheelSpeeds,
                    drive.getLeftPIDController(),
                    drive.getRightPIDController(),
                    drive::setVolts,drive)//,
                //new WaitCommand(3),
                //new shoot(shooter::getdistance, shooter::turnTurret, shooter::ballUP,shooter)
            )//,
            //new aim(aimer::getError, aimer::turnTurret, aimer.getController(), aimer::aimEnd, aimer::getVertPosition, aimer)
        );

        return Command;

        } catch (IOException ex) {
            SmartDashboard.putString("Unable to open trajectory: " , trajectoryJSON);
        }
        return null;
    }

    public Command getTeleopCommandDiffer(){
        ParallelCommandGroup Command = new ParallelCommandGroup(
            //new RunCommand(),
            new Differential(drive.drive, oi::yAxis, oi::zRotation, drive),
            new shoot(shooter::getdistance, shooter::shootOutput, shooter::ballUP, oi::activateShoot, shooter),
            //new aim(aimer::getError, aimer::turnTurret, aimer.getController(), aimer::aimEnd, aimer::getVertPosition, aimer),
            new gatherBall(gatherer::convey, gatherer::gatherBall, oi::activateGather, oi::activateOuttake, gatherer),
            new lift(lift::liftUP, lift::liftSideWay, oi::liftUP, lift::brake, oi::activateLiftBrake, lift)
        );

        return Command;
    }

    /*public Command getTeleopCommandMecanum(){
        ParallelCommandGroup Command = new ParallelCommandGroup(
            new Mecanum(drive.mecDrive, oi::MecyAxis, oi::MecxAxis, oi::MeczRotation, drive),
            new shoot(shooter::getdistance, shooter::shootOutput, shooter::ballUP, oi::activateShoot, shooter),
            new aim(aimer::getError, aimer::turnTurret, aimer.getController(), aimer::aimEnd, aimer::getVertPosition, aimer),
            new gatherBall(gatherer::convey, gatherer::gatherBall, oi::activateGather, oi::activateOuttake, gatherer)
        );

        return Command;
    }

    public Command getTeleopCommandAbsMec(){
        ParallelCommandGroup Command = new ParallelCommandGroup(
            new Mecanum(drive.mecDrive, oi::MecyAxis, oi::MecxAxis, oi::MeczRotation, drive::getAngle, drive.getTurnPIDController(), true, drive),
            new shoot(shooter::getdistance, shooter::shootOutput, shooter::ballUP, oi::activateShoot, shooter),
            new aim(aimer::getError, aimer::turnTurret, aimer.getController(), aimer::aimEnd, aimer::getVertPosition, aimer),
            new gatherBall(gatherer::convey, gatherer::gatherBall, oi::activateGather, oi::activateOuttake, gatherer)
        );

        return Command;
    }*/

    public Command getDisableSafetyCommand(){
        return new ParallelRaceGroup(
            new RunCommand(()->drive.drive.tankDrive(-drive.getLeftVelocity(), drive.getRightVelocity()), drive),
            new WaitCommand(0.1)
            );
    }
}
