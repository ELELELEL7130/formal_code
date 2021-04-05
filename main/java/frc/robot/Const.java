/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
/**
 * Add your docs here.
 */
public class Const {
    // public static final double TRACK_WIDTH_INCHES = 24.5;

    public static final double WHEEL_RADIUS = 3 * 0.0254;
    // wheel Radius 是
    // 輪輞直徑與輪胎高度的兩倍之和
    // :https://x-engineer.org/automotive-engineering/chassis/longitudinal-dynamics/calculate-wheel-radius/

    // public static final double TICKS_TO_INCHES_RATIO = (2 * Math.PI * 2) / 512;
    public static final double TICKS_TO_METERS_RATIO =  (7 / 44) * (2 * Math.PI * WHEEL_RADIUS) / 4096;

    // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    // SHOOTING CONSTANTS
    // ................................................................................................

    // public static final double NEO_SHOOTING_TARGET_RPM = 3200;
    // public static final double SHOOTING_MAX_RPM = 4000;

    public static final double HOOD_BACK = 9.71;
    public static final double HOOD_FRONT = 9.42;
    public static final double HOOD_MIDDLE = 9.57;
    public static final double HOOD_Kp = 2;
    public static final double HOOD_MAXOUTPUT = 0.1;
    public static final double HOOD_MINOUTPUT = -0.1;

    // public static final double NEO_SHOOTING_Kp = 0.001;
    // public static final double NEO_SHOOTING_Kd = 0.0000008;
    public static final double SHOOTING_Kp = 0.2;
    public static final double SHOOTING_Kd = 0.1; // 4.5
    public static final double SHOOTING_Kf = 0.0639;
    public static final double SHOOTING_Ki = .001; // .001
    public static final double SHOOTING_Kizone = 80; // 50
    public static final double SHOOTING_TARGET_RPM = 9000;
    public static final double SHOOTING_UNITS_PER_REV = 820;

    // TODO: prefix these

    // Programming chassis
    // public static final double Kp = 6.5;
    // public static final double Kd = 0.0;
    // public static final double Ks = 0.772;
    // public static final double Kv = 3.88;
    // public static final double Ka = 0.321;
    // public static final double TRACK_WIDTH = .77259721916115;
    public static final double Kp = 0.3;
    public static final double Kd = 0.0;
    public static final double Ki = 0.0;
    public static final double turnKp = 0.01;
    public static final double turnKd = 0.001;
    public static final double turnKi = 0.01;
    public static final double Ks = 0.109;
    public static final double Kv = 0.1;
    public static final double Ka = 0.000895;
    public static final double TRACK_WIDTH = 0.55;

    public static final boolean kGyroReversed = true;

    public static final double aimVertMaxAngle = 90;

    public static final double limelightDefault = 100;

    //OI const
    // 左蘑菇
    public static final int leftStick_x = 0;
    public static final int leftStick_y = 1;
    // 右蘑菇
    public static final int rightStick_x = 4;
    public static final int rightStick_y = 5;
    //
    public static final int shootButton = 1;
    public static final int gatherButton = 6;
    public static final int outtakeButton = 2;
    public static final int liftBrakebutton = 3;
}