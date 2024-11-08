import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Ejercicio3 {
    public static void main(String[] args) {
        // Cola para almacenar los números aleatorios
        Queue<Integer> cola = new LinkedList<>();
        final int TAMANIO = 50;
        Random random = new Random();

        // Generamos 50 números aleatorios entre 1 y 50 y los agregamos a la cola
        System.out.print("Números generados en la cola: ");
        for (int i = 0; i < TAMANIO; i++) {
            int num = random.nextInt(50) + 1; // Genera números entre 1 y 50
            cola.add(num);
            System.out.print(num + " ");
        }
        System.out.println(); // Salto de línea para separación

        // Mapa para contar las apariciones de cada número en la cola
        HashMap<Integer, Integer> contador = new HashMap<>();
        for (int num : cola) {
            contador.put(num, contador.getOrDefault(num, 0) + 1);
        }

        // Lista para almacenar los números únicos (que aparecen solo una vez)
        List<Integer> numerosUnicos = new ArrayList<>();
        for (int num : cola) {
            if (contador.get(num) == 1) {
                numerosUnicos.add(num);
            }
        }

        // Mostramos los números únicos en la lista
        System.out.print("Números únicos en la lista: ");
        for (int num : numerosUnicos) {
            System.out.print(num + " ");
        }
        System.out.println(); // Salto de línea al final
    }
}




