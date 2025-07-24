package frc.robot.utils;

import edu.wpi.first.wpilibj.XboxController;

public class Utils {

    public static double getJoystickX(XboxController controller, boolean isLeft, double maxValue, double minValue, double power) {
        return getJoystickValue(isLeft ? controller.getLeftX() : controller.getRightX(), maxValue, minValue, power);
    }

    public static double getJoystickX(XboxController controller, boolean isLeft, double maxValue, double minValue) {
        return getJoystickX(controller, isLeft, maxValue, minValue, 2);
    }
    public static double getJoystickX(XboxController controller, boolean isLeft, double maxValue) {
        return getJoystickX(controller, isLeft, maxValue, 0.1, 2);
    }
    public static double getJoystickX(XboxController controller, boolean isLeft) {
        return getJoystickX(controller, isLeft, 1, 0.1, 2);
    }
    public static double getJoystickLeftX(XboxController controller) {
        return getJoystickX(controller, true, 1, 0.1, 2);
    }
    public static double getJoystickRightX(XboxController controller) {
        return getJoystickX(controller, false, 1, 0.1, 2);
    }
    public static double getJoystickLeftX(XboxController controller, double maxValue) {
        return getJoystickX(controller, true, maxValue, 0.1, 2);
    }
    public static double getJoystickRightX(XboxController controller, double maxValue) {
        return getJoystickX(controller, false, maxValue, 0.1, 2);
    }
    
    public static double getJoystickY(XboxController controller, boolean isLeft, double maxValue, double minValue, double power) {
        return getJoystickValue(isLeft ? -controller.getLeftY() : -controller.getRightY(), maxValue, minValue, power);
    }
    public static double getJoystickY(XboxController controller, boolean isLeft, double maxValue, double minValue) {
        return getJoystickY(controller, isLeft, maxValue, minValue, 2);
    }
    public static double getJoystickY(XboxController controller, boolean isLeft, double maxValue) {
        return getJoystickY(controller, isLeft, maxValue, 0.1, 2);
    }
    public static double getJoystickY(XboxController controller, boolean isLeft) {
        return getJoystickY(controller, isLeft, 1, 0.1, 2);
    }
    public static double getJoystickLeftY(XboxController controller) {
        return getJoystickY(controller, true, 1, 0.1, 2);
    }
    public static double getJoystickRightY(XboxController controller) {
        return getJoystickY(controller, false, 1, 0.1, 2);
    }
    public static double getJoystickLeftY(XboxController controller, double maxValue) {
        return getJoystickY(controller, true, maxValue, 0.1, 2);
    }
    public static double getJoystickRightY(XboxController controller, double maxValue) {
        return getJoystickY(controller, false, maxValue, 0.1, 2);
    }

    public static double getJoystickValue(double value, double maxValue, double minValue, double power) {
        if(Math.abs(value) < minValue) {
            return 0;
        }
        value = Math.pow(Math.abs(value), power) * Math.signum(value);
        return value * maxValue;
    }

    public static void main(String[] args) {
        for(double d = -1; d <= 1; d+=0.1)
            System.out.printf("%4.2f -> %4.2f\n", d, getJoystickValue(d,0.7, 0.1, 1.5));
    }

}
