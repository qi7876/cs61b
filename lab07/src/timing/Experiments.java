package timing;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Random;

public class Experiments {

    private static void printTimingTable(TimingData data) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        IO.println("------------------------------------------------------------");
        for (TimingData.Trial t : data.trials()) {
            System.out.printf("%12d %12.2f %12d %12.2f\n", t.N(), t.seconds(), t.ops(), t.usPerOp());
        }
    }

    public static TimingData exampleListExperiment() {
        TimingData td = new TimingData("ArrayList contains");

        // A single contains call on a small list takes about a microsecond,
        // too little for our stopwatch to see, so each trial times 10000 of them.
        int ops = 10000;

        for (int N = 1000; N <= 512000; N *= 2) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < N; i += 1) {
                list.add(i);
            }
            Random random = new Random(61);

            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < ops; j += 1) {
                list.contains(random.nextInt(N));
            }
            td.add(N, ops, sw.elapsedTime());
        }

        return td;
    }

    public static TimingData exampleFibonacciExperiment() {
        TimingData td = new TimingData("Naive Recursive Fibonacci");

        // One call of fib(N) per trial is enough: for these N, a single call
        // takes milliseconds to a fraction of a second, long enough to measure.
        int ops = 1;

        for (int N = 25; N <= 40; N++) {
            Stopwatch sw = new Stopwatch();
            int fib = FunctionsToBeTimed.fib(N);
            td.add(N, ops, sw.elapsedTime());
        }

        return td;
    }

    public static TimingData timeSp16fp9p1() {
        TimingData td = new TimingData("sp16fp9p1");

        int ops = 1;

        for (int N = 4000; N <= 128000; N *= 2) {
            Stopwatch sw = new Stopwatch();
            long sum = FunctionsToBeTimed.sp16fp9p1(N);
            td.add(N, ops, sw.elapsedTime());
        }

        return td;
    }

    public static TimingData timeSp16fp9p2() {
        TimingData td = new TimingData("sp16fp9p2");

        int ops = 1;

        for (int N = 1000000; N <= 128000000; N *= 2) {
            Stopwatch sw = new Stopwatch();
            long sum = FunctionsToBeTimed.sp16fp9p2(N);
            td.add(N, ops, sw.elapsedTime());
        }

        return td;
    }

    public static TimingData timeMsf() {
        TimingData td = new TimingData("msf");

        int ops = 1;

        for (int N = 1000_000; N <= 128_000_000; N *= 2) {
            Stopwatch sw = new Stopwatch();
            long sum = FunctionsToBeTimed.sp16fp9p2(N);
            td.add(N, ops, sw.elapsedTime());
        }

        return td;
    }

    public static TimingData timeSp16fp9p5() {
        TimingData td = new TimingData("sp16fp9p5");

        int ops = 1;

        for (int N = 1_000; N <= 32_000; N *= 2) {
            Stopwatch sw = new Stopwatch();
            long sum = FunctionsToBeTimed.sp16fp9p5(N);
            td.add(N, ops, sw.elapsedTime());
        }

        return td;
    }

    public static TimingData timeAListConstruction() {
        TimingData td = new TimingData("AList addLast construction");

        for (int N = 1_000_000; N <= 256_000_000; N *= 2) {
            AList<Integer> l = new AList<Integer>();
            Stopwatch sw = new Stopwatch();
            for (int i = 0; i < N; i++) {
                l.addLast(1);
            }
            td.add(N, N, sw.elapsedTime());
        }
        return td;
    }

    void main() {
        TimingData td = timeAListConstruction();

        printTimingTable(td);
        Fit.plotWithFit(td, Fit.Growth.N);
    }
}
