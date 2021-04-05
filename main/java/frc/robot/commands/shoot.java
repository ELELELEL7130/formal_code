package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;

import frc.robot.subsystems.*;

public class shoot extends CommandBase{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private DoubleSupplier distance;
  private Consumer<Double> output;
  private Consumer<Double> ballup;
  private BooleanSupplier activate;
  private boolean autoORtele;
  private static Timer time = new Timer();
  private static boolean Switch;

  public shoot(DoubleSupplier Distance, Consumer<Double> shootervelocity, Consumer<Double> ballUP, BooleanSupplier Activate, Subsystem requirements) {
    distance = Distance;
    output = shootervelocity;
    ballup = ballUP;
    activate = Activate;
    autoORtele = false;
    addRequirements(requirements);
  }

  public shoot(DoubleSupplier Distance, Consumer<Double> shootervelocity, Consumer<Double> ballUP, Subsystem requirements) {
    distance = Distance;
    output = shootervelocity;
    ballup = ballUP;
    autoORtele = true;
    addRequirements(requirements);
  }

  @Override
  public void initialize() {
      time.reset();
      time.stop();
      Switch = false;
  }

  @Override
  public void execute() {
    SmartDashboard.putNumber("time", time.get());
    if (activate.getAsBoolean() == true && !time.hasPeriodPassed(3)){
      if (distance.getAsDouble() == -1) {
          output.accept(0.0);
          ballup.accept(0.0);
          System.out.println("out of sight");
      }
      else {

        output.accept(1000.0);
        ballup.accept(1.0);
      }

      if (Switch == false){
        time.start();
        Switch = true;
      }
    }
    else {
      output.accept(0.0);
      ballup.accept(0.0);
      time.stop();
      Switch =false;
    }      
  }

  @Override
  public void end(boolean interrupted) {
      output.accept((double) 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (time.get() >= 3 && autoORtele) return true;
    else return false;
  }

}