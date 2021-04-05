package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class gatherBall extends CommandBase{
    Consumer<Double> conveyer, intaker;
    BooleanSupplier activater,outtaker;

    public gatherBall(Consumer<Double> convey, Consumer<Double> intake, BooleanSupplier activate, BooleanSupplier outtake, Subsystem... requirements){
        conveyer = convey;
        intaker = intake;
        activater = activate;
        outtaker =outtake;
        addRequirements(requirements);
    }

    @Override

    public void execute() {
        if (activater.getAsBoolean() == true){
            conveyer.accept(1.0);
            intaker.accept(1.0);
        }
        else if (outtaker.getAsBoolean() == true){
            conveyer.accept(-1.0);
            intaker.accept(-1.0);
        }
        else {
            conveyer.accept(0.0);
            intaker.accept(0.0);
        }
    }

}