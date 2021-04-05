package frc.robot;

import edu.wpi.first.hal.sim.mockdata.DIODataJNI;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

class OI{
    public static Joystick js1 = new Joystick(0);
    public static Joystick js2 = new Joystick(1);
    private static Timer time = new Timer();

    public double zRotation(){
        return js1.getRawAxis(Const.rightStick_x);
    }

    public double yAxis(){
        return -js1.getRawAxis(Const.leftStick_y);
    }

    public double MecyAxis(){
        return -js1.getRawAxis(Const.leftStick_y);
    }

    public double MecxAxis(){
        return js1.getRawAxis(Const.leftStick_x);
    }

    public double MeczRotation(){
        return js1.getRawAxis(Const.rightStick_x);
    }

    public boolean activateShoot(){
        return js1.getRawButton(Const.shootButton);
    }

    public boolean activateGather(){
        return js1.getRawButton(Const.gatherButton);
    }

    public boolean activateOuttake(){
        return js1.getRawButton(Const.outtakeButton);
    }

    public double liftUP(){
        return js1.getPOV();
    }

    public Boolean activateLiftBrake(){
        return js1.getRawButton(Const.liftBrakebutton);
    }
}
