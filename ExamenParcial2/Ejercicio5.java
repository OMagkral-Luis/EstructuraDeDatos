import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Ejercicio5 {
    public static void main(String[] args) {
        
        Queue<Integer> cola = new LinkedList<>();
        final int TAMANIO = 50; 
        Random random = new Random();
        int contador25 = 0;

        
        for (int i = 0; i < TAMANIO; i++) {
            int num = random.nextInt(50) + 1; 
            cola.add(num);
            System.out.print(num + " "); 

            
            if (num == 25) {
                contador25++;
                System.out.println("\nSe encontró el número 25. Sacando 3 elementos de la cola:");
                for (int j = 0; j < 3; j++) {
                    if (!cola.isEmpty()) {
                        int eliminado = cola.poll(); 
                        System.out.println("Elemento eliminado: " + eliminado);
                    }
                }
            }
        }

        
        System.out.println("\nEl número 25 se repitió " + contador25 + " veces.");
        
        System.out.println("Contenido restante de la cola: " + cola);
    }
}
