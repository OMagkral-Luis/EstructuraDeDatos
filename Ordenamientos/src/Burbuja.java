
import java.util.Random;

public class Burbuja {
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
        
        ordenarBurbuja(numeros);
        
        System.out.println("\nNúmeros ordenados:");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
    }

    public static void ordenarBurbuja(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
