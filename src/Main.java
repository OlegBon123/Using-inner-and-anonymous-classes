public class Main {
    public static void main(String[] args) {
        System.out.println("Метод Ньютона:");
        try {
            double root = NewtonMethod.newton(-10, 1e-6, 100);
            System.out.println("Корінь рівняння: " + root);
        } catch (ArithmeticException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        System.out.println("\nЕкстремуми функції:");
        try {
            double[] extrema = ExtremumFinder.findExtrema(-10, 10, 1e-6, 100);
            System.out.println("Мінімум: " + extrema[0]);
            System.out.println("Максимум: " + extrema[1]);
        } catch (ArithmeticException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        System.out.println("\nПіднесення до степеня:");
        double base = 2;
        int exponent = 3;
        System.out.println(base + " ^ " + exponent + " (ітеративно): " + PowerCalculator.powerIterative(base, exponent));
        System.out.println(base + " ^ " + exponent + " (рекурсивно): " + PowerCalculator.powerRecursive(base, exponent));
    }
}

class NewtonMethod {
    public static double f(double x) {
        return -2 * Math.pow(x, 3) + 3 * x + 4;
    }

    public static double df(double x) {
        return -6 * Math.pow(x, 2) + 3;
    }

    public static double newton(double initialGuess, double epsilon, int maxIterations) {
        double x = initialGuess;
        for (int i = 0; i < maxIterations; i++) {
            double fx = f(x);
            double dfx = df(x);

            if (Math.abs(dfx) < 1e-10) {
                throw new ArithmeticException("Похідна наближається до нуля");
            }

            double nextX = x - fx / dfx;

            if (Math.abs(nextX - x) < epsilon) {
                return nextX;
            }

            x = nextX;
        }

        throw new ArithmeticException("Метод Ньютона не збігся");
    }
}

class ExtremumFinder {
    public static double df(double x) {
        return -6 * Math.pow(x, 2) + 3;
    }

    public static double ddf(double x) {
        return -12 * x;
    }

    public static double[] findExtrema(double a, double b, double epsilon, int maxIterations) {
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        boolean foundExtremum = false;

        for (double x = a; x <= b; x += 0.01) {
            try {
                double extremum = NewtonMethod.newton(x, epsilon, maxIterations);
                double secondDerivative = ddf(extremum);

                if (secondDerivative > 0) {
                    min = Math.min(min, extremum);
                    foundExtremum = true;
                } else if (secondDerivative < 0) {
                    max = Math.max(max, extremum);
                    foundExtremum = true;
                }
            } catch (ArithmeticException e) {
            }
        }

        if (!foundExtremum) {
            throw new ArithmeticException("Не знайдено екстремуми на заданому проміжку");
        }

        return new double[]{min, max};
    }
}

class PowerCalculator {
    public static double powerIterative(double base, int exponent) {
        double result = 1;
        for (int i = 0; i < Math.abs(exponent); i++) {
            result *= base;
        }
        return exponent < 0 ? 1 / result : result;
    }

    public static double powerRecursive(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent < 0) {
            return 1 / powerRecursive(base, -exponent);
        }
        return base * powerRecursive(base, exponent - 1);
    }
}
