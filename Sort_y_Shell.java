
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Sort_y_Shell {

    // Método para generar una lista de números aleatorios
    public static int[] generarLista(int tamano, int min, int max) {
        Random random = new Random();
        int[] lista = new int[tamano];
        for (int i = 0; i < tamano; i++) {
            lista[i] = random.nextInt((max - min) + 1) + min;
        }
        return lista;
    }

    // Implementación de QuickSort
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // Implementación de ShellSort
    public static void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    // Método de búsqueda binaria
    public static int busquedaBinaria(int[] arr, int clave) {
        int inicio = 0;
        int fin = arr.length - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;

            // Compara el valor en el medio con la clave
            if (arr[medio] == clave) {
                return medio;
            }

            // Si la clave es mayor, ignora la mitad izquierda
            if (arr[medio] < clave) {
                inicio = medio + 1;
            } else {
                // Si la clave es menor, ignora la mitad derecha
                fin = medio - 1;
            }
        }
        // Si no se encuentra, retorna -1
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Generar una lista de 20 números aleatorios del 1 al 100
        int[] lista = generarLista(20, 1, 100);
        System.out.println("Lista generada: " + Arrays.toString(lista));

        // Pedir al usuario que elija un método de ordenación
        System.out.println("Elija el método de ordenación: ");
        System.out.println("1. QuickSort");
        System.out.println("2. ShellSort");
        int eleccion = scanner.nextInt();

        // Ordenar la lista según la elección del usuario
        if (eleccion == 1) {
            quickSort(lista, 0, lista.length - 1);
            System.out.println("Lista ordenada con QuickSort: " + Arrays.toString(lista));
        } else if (eleccion == 2) {
            shellSort(lista);
            System.out.println("Lista ordenada con ShellSort: " + Arrays.toString(lista));
        } else {
            System.out.println("Opción inválida");
            return;
        }

        // Pedir al usuario un número para buscar en la lista ordenada
        System.out.println("Ingrese un número para buscar en la lista: ");
        int numero = scanner.nextInt();

        // Búsqueda binaria
        int resultado = busquedaBinaria(lista, numero);
        if (resultado == -1) {
            System.out.println("Número no encontrado.");
        } else {
            System.out.println("Número encontrado en la posición: " + resultado);
        }

        scanner.close();
    }
}
