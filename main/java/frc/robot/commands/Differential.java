package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Differential extends CommandBase{
    DifferentialDrive drive;
    DoubleSupplier y,x;


    public Differential(DifferentialDrive driveSystem, DoubleSupplier yAxis, DoubleSupplier zRotation, Subsystem requirement){
        drive = driveSystem;
        y = yAxis;
        x = zRotation;
        addRequirements(requirement);
    }

    @Override
    public void execute() {
        drive.arcadeDrive(y.getAsDouble(), x.getAsDouble());
    }
}