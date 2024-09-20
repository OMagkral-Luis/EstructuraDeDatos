import java.util.Random;
import java.util.Stack;

public class Problema3 {
    public static void main(String[] args) {
        Stack<Integer> pilaOriginal = new Stack<>();
        Stack<Integer> pilaResultados = new Stack<>();
        Random random = new Random();

        
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(100) + 1;
            pilaOriginal.push(num);
            System.out.print(num + " ");
        }
        System.out.println();

       
        while (!pilaOriginal.isEmpty()) {
            int num1 = pilaOriginal.pop();
            if (pilaOriginal.isEmpty()) {
                pilaResultados.push(num1);
            } else {
                int num2 = pilaOriginal.pop();
                int suma = num1 + num2;
                pilaResultados.push(suma);
            }
        }

        
        while (pilaResultados.size() >= 2) {
            int num1 = pilaResultados.pop();
            int num2 = pilaResultados.pop();
            int resultado = (num1 - num2) * pilaResultados.peek();
            System.out.println(num1 + " - " + num2 + " * " + pilaResultados.peek() + " = " + resultado);
            pilaResultados.pop();
            resultado += pilaResultados.peek();
            System.out.println(resultado + " - " + pilaResultados.peek() + " = " + (resultado - pilaResultados.peek()));
            pilaResultados.pop(); 
        }

       
        if (!pilaResultados.isEmpty()) {
            System.out.println("Resultado final: " + pilaResultados.pop());
        }
    }
}