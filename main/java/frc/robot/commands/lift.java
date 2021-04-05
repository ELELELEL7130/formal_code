package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class lift extends CommandBase{
    Consumer<Double> outputUP,outputSideWay;
    DoubleSupplier lift;
    Consumer<Double> brake;
    BooleanSupplier activateBrake;

    public lift(Consumer<Double> outputUp, Consumer<Double> outputSideway, DoubleSupplier lift, Consumer<Double> brake, BooleanSupplier ActivateBrake,Subsystem... requirements){
        outputUP = outputUp;
        this.lift = lift;
        outputSideWay = outputSideway;
        this.brake = brake;
        activateBrake = ActivateBrake;
        addRequirements(requirements);
    }

    @Override
    public void execute() {
        if (lift.getAsDouble() == -1){
            outputUP.accept(0.0);
            outputSideWay.accept(0.0);
        }
        else{
            outputUP.accept(Math.cos(Math.toRadians(lift.getAsDouble())));
            outputSideWay.accept(Math.sin(Math.toRadians(lift.getAsDouble())));
        }
        if (activateBrake.getAsBoolean() == true){
            brake.accept(0.0);
        }
        else {
            brake.accept(0.25);
        }
    }
}