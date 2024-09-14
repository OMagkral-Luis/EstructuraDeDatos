
import java.util.Random;

public class Seleccion {
    public static void main(String[] args) {
        int[] numeros = new int[10];
        Random random = new Random();
        
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = random.nextInt(51);
        }
        
        System.out.println("Números originales:");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
        
        ordenarSeleccion(numeros);
        
        System.out.println("\nNúmeros ordenados:");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
    }

    public static void ordenarSeleccion(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}

