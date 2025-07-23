package frc.Demacia.Sysid;


import org.ejml.simple.SimpleMatrix;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.plant.LinearSystemId;

public class CalculateFeedbackGains {
    public static double calculateFeedbackGains(double kv, double ka) {
        var plant = LinearSystemId.identifyVelocitySystem(kv, ka);
        var A = plant.getA();
        var B = plant.getB();
        var Q = StateSpaceUtil.makeCostMatrix(VecBuilder.fill(0.1));
        var R = StateSpaceUtil.makeCostMatrix(VecBuilder.fill(12));
        var discABPair = discretizeAB(A, B, 0.02);
        var discA = discABPair.getFirst();
        var discB = discABPair.getSecond();

        var S = new Matrix<N1, N1>(RiccatiSolver.solveDARE(discA.getStorage(), discB.getStorage(), Q.getStorage(), R.getStorage()));

        // K = (BᵀSB + R)⁻¹BᵀSA
        var K = discB
            .transpose()
            .times(S)
            .times(discB)
            .plus(R)
            .solve(discB.transpose().times(S).times(discA));

        double kp = K.get(0, 0);
        return kp;
    }
    
    @SuppressWarnings("unchecked")
    public static <States extends Num, Inputs extends Num>
      Pair<Matrix<States, States>, Matrix<States, Inputs>> discretizeAB(
          Matrix<States, States> contA, Matrix<States, Inputs> contB, double dtSeconds) {

        int states = contA.getNumRows();
        int inputs = contB.getNumCols();

        // M = [A  B]
        //     [0  0]
        var M = new Matrix<>(new SimpleMatrix(states + inputs, states + inputs));
        M.assignBlock(0, 0, contA);
        M.assignBlock(0, contA.getNumCols(), contB);

        //  ϕ = eᴹᵀ = [A_d  B_d]
        //            [ 0    I ]
        var phi = calculateMatrixExponential(M.times(dtSeconds));

        var discA = new Matrix<States, States>(new SimpleMatrix(states, states));
        discA.extractFrom(0, 0, phi);

        var discB = new Matrix<States, Inputs>(new SimpleMatrix(states, inputs));
        discB.extractFrom(0, contB.getNumRows(), phi);

        return new Pair<>(discA, discB);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Matrix calculateMatrixExponential(Matrix A) {
        // calculate matrix exponential
        // using taylor terms
        // exp(A) = I + A + A²/2! + A³/3! + ... + Aⁿ/n!
        // using 20 series
        var res = new Matrix<>(SimpleMatrix.identity(A.getNumCols())).plus(A); // I + A
        var t = A.copy();
        double factorial = 1;
        for(double i = 2; i < 20; i++) {
            factorial *= i;
            t = t.times(A).times(1/factorial);
            res = res.plus(t);
        }
        return res;

    }
    public static void main(String[] args) {
        try {
            double kp = calculateFeedbackGains(0.5, 0.5);
            System.out.println("kp = " + kp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
