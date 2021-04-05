package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class outtake extends CommandBase{
    Consumer<Double> outtaker;
    BooleanSupplier activater;

    public outtake(Consumer<Double> outtake,BooleanSupplier activate, Subsystem... requirements){
        outtaker = outtake;
        activater = activate;
        addRequirements(requirements);
    }

    @Override
    public void execute() {
        if (activater.getAsBoolean() == true){
            outtaker.accept(1.0);
        }
        else {
            outtaker.accept(0.0);
        }
    }
}