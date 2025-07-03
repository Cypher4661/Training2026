public class OriTest {

    private static final double GEAR_RATIO = 10.0;
    private double motorPosition = 0.0;

    public void rotateMechanism(double targetAngle) {
        double initialPosition = motorPosition;
        double mechanismPosition = 0.0;

        while (mechanismPosition < targetAngle) {
            motorPosition += 1.0; 
            mechanismPosition = motorPosition / GEAR_RATIO;

            if (motorPosition != initialPosition) {
                System.out.println("Motor Angle: " + motorPosition + " degrees");
                initialPosition = motorPosition;
            }
        }
    }

    public static void main(String[] args) {
        OriTest oriTest = new OriTest();
        oriTest.rotateMechanism(90.0); 
    }
}
