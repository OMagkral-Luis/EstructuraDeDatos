import java.util.Random;
import java.util.Stack;

public class Problema2 {
    public static void main(String[] args) {
        Stack<Integer> pila = new Stack<>();
        Random random = new Random();
        int suma = 0, numero;

        System.out.print("Números generados: ");
        while (true) {
            numero = random.nextInt(100) + 1; 
            System.out.print(numero + " ");
            pila.push(numero);

            if (numero == 50) {
                break;
            }
        }

        System.out.println("\nSe encontró un 50. Calculando la suma...");
        while (!pila.isEmpty()) {
            suma += pila.pop();
        }

        System.out.println("La suma total es: " + suma);
    }
}




