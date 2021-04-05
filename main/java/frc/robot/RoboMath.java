package frc.robot;

import frc.robot.Const;

/**
 * RoboMath
 */
public class RoboMath {
    //sin(a+b) a,b in arc
    public static double sinaplb(double arca, double arcb) {
        return Math.sin(arca)*Math.cos(arcb)+Math.sin(arcb)*Math.cos(arca);
    }
    //cos(a+b) a,b in arc
    public static double cosaplb(double arca, double arcb) {
        return Math.cos(arca)*Math.cos(arcb)-Math.sin(arcb)*Math.sin(arca);
    }

    public static double tickstoAngle(double ticks){
        return ticks / 4096 * 360;
    }

    public static double ticksToMeters(double ticks) {
        return ticks * Const.TICKS_TO_METERS_RATIO;
    }
}