import java.util.Scanner;
import java.util.Stack;

public class NotPolnv {


    public static boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

   
    public static double operar(double a, double b, char operador) {
        switch (operador) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            default: return 0;
        }
    }

   
    public static double resolverRPN(String ecuacion) {
        Stack<Double> pila = new Stack<>();
        String[] tokens = ecuacion.split(" ");

        for (String token : tokens) {
            if (token.length() == 1 && esOperador(token.charAt(0))) {
                double b = pila.pop();
                double a = pila.pop();
                double resultado = operar(a, b, token.charAt(0));
                pila.push(resultado);
            } else {
                pila.push(Double.parseDouble(token));
            }
        }

        return pila.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ecuación en notación polaca inversa , separada por espacios:");
        String ecuacion = scanner.nextLine();

        double resultado = resolverRPN(ecuacion);

        System.out.println("El resultado es: " + resultado);

        scanner.close();
    }
}

