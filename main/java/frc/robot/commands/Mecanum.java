package frc.robot.commands;

import java.util.function.DoubleSupplier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RoboMath;

public class Mecanum extends CommandBase{
    MecanumDrive drive;
    DoubleSupplier y,x,z;
    DoubleSupplier ahrs;
    PIDController turn;
    boolean absolute =false;
    boolean usingGyro;
    double yAxis, xAxis, rotation;

    public Mecanum(MecanumDrive driveSystem, DoubleSupplier yAxis, DoubleSupplier xAxis, DoubleSupplier zRotation, DoubleSupplier gyro, PIDController turncController, Boolean absoluteMec, Subsystem requirement){
        drive = driveSystem;
        y = yAxis;
        x = xAxis;
        z = zRotation;
        ahrs = gyro;
        turn = turncController;
        absolute = absoluteMec;
        usingGyro = true;
        addRequirements(requirement);
    }

    public Mecanum(MecanumDrive driveSystem, DoubleSupplier yAxis, DoubleSupplier xAxis, DoubleSupplier zRotation, Subsystem requirement){
        drive = driveSystem;
        y = yAxis;
        x = xAxis;
        z = zRotation;
        usingGyro = false;
        addRequirements(requirement);
    }


    @Override
    public void execute() {
        if (usingGyro == false) rotation = z.getAsDouble();
        else rotation = turn.calculate(ahrs.getAsDouble(), ahrs.getAsDouble()+z.getAsDouble());

        if (absolute == true && usingGyro == true){ 
            SmartDashboard.putNumber("math", RoboMath.cosaplb(Math.atan2(y.getAsDouble(), x.getAsDouble()),Math.toRadians(ahrs.getAsDouble()))) ;         
            yAxis = RoboMath.sinaplb(Math.atan2(y.getAsDouble(), x.getAsDouble()),Math.toRadians(ahrs.getAsDouble()));
            xAxis = RoboMath.cosaplb(Math.atan2(y.getAsDouble(), x.getAsDouble()),Math.toRadians(ahrs.getAsDouble()));
        }
        else {
            yAxis = y.getAsDouble();
            xAxis = x.getAsDouble();
        }
        drive.driveCartesian(xAxis, yAxis, rotation);
    }
}