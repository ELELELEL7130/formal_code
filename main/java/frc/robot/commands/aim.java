package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Const;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class aim extends CommandBase {
  DoubleSupplier Error;
  Consumer<Double> Output;
  BooleanSupplier endLogic;
  PIDController turnController;
  double setpoint = 0;
  DoubleSupplier position;

  public aim(DoubleSupplier error, Consumer<Double> turnOutput, PIDController controller, double Setpoint, BooleanSupplier end, DoubleSupplier Position, Subsystem... requirements){
    Error = error;
    Output = turnOutput;
    endLogic = end;
    turnController = controller;
    setpoint = Setpoint;
    position = Position;
    addRequirements(requirements);
  }

  public aim(DoubleSupplier error, Consumer<Double> turnOutput, PIDController controller, BooleanSupplier end, DoubleSupplier Position, Subsystem... requirements){
    Error = error;
    Output = turnOutput;
    endLogic = end;
    turnController = controller;
    position = Position;
    addRequirements(requirements);
  }

  @Override
  public void initialize() {
    turnController.setSetpoint(0);
  }

  @Override
  public void execute() {
    if (Error.getAsDouble() == Const.limelightDefault) {
      Output.accept((double) 0);
      System.out.println("out of sight");
    }  
    else if (position.getAsDouble() >= Const.aimVertMaxAngle/2 && Error.getAsDouble() < 0) Output.accept(0.0);
    else if (-position.getAsDouble() >= Const.aimVertMaxAngle/2 && Error.getAsDouble() > 0) Output.accept(0.0);
    else Output.accept(turnController.calculate(Error.getAsDouble()));
    SmartDashboard.putNumber("aimHoriPosition", position.getAsDouble());
  }

  @Override
  public boolean isFinished() {
    return endLogic.getAsBoolean();
  }
}