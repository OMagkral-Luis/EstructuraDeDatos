import java.util.Random;

public class Insercion {
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
        
        ordenarInsercion(numeros);
        
        System.out.println("\nNúmeros ordenados:");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
    }

    public static void ordenarInsercion(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
}
