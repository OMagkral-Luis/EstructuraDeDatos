import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ejercicio1 {

   
    private int[] listaNumeros;

   
    public Ejercicio1() {
        listaNumeros = new int[50];
        Random rand = new Random();
        for (int i = 0; i < listaNumeros.length; i++) {
            listaNumeros[i] = rand.nextInt(100) + 1; 
        }
    }

    
    public void imprimirNumeros() {
        System.out.print("Lista de numeros: ");
        for (int num : listaNumeros) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

   
    public double calcularMedia() {
        double suma = 0;
        for (int num : listaNumeros) {
            suma += num;
        }
        return suma / listaNumeros.length;
    }

 
    public int calcularModa() {
        
        Map<Integer, Integer> frecuencias = new HashMap<>();
        
        
        for (int num : listaNumeros) {
            if (frecuencias.containsKey(num)) {
                frecuencias.put(num, frecuencias.get(num) + 1);
            } else {
                frecuencias.put(num, 1);
            }
        }

   
        int moda = listaNumeros[0];
        int maxFrecuencia = 0;

        for (Map.Entry<Integer, Integer> entry : frecuencias.entrySet()) {
            if (entry.getValue() > maxFrecuencia) {
                maxFrecuencia = entry.getValue();
                moda = entry.getKey();
            }
        }

        return moda;
    }

   
    public double calcularMediana() {
        
        for (int i = 0; i < listaNumeros.length - 1; i++) {
            for (int j = 0; j < listaNumeros.length - i - 1; j++) {
                if (listaNumeros[j] > listaNumeros[j + 1]) {
                    // Intercambiamos los elementos
                    int temp = listaNumeros[j];
                    listaNumeros[j] = listaNumeros[j + 1];
                    listaNumeros[j + 1] = temp;
                }
            }
        }

        
        if (listaNumeros.length % 2 != 0) {
            return listaNumeros[listaNumeros.length / 2];
        }

      
        int mid1 = listaNumeros[(listaNumeros.length / 2) - 1];
        int mid2 = listaNumeros[listaNumeros.length / 2];
        return (mid1 + mid2) / 2.0;
    }

  
    public static void main(String[] args) {
        Ejercicio1 estadisticas = new Ejercicio1();
        
        estadisticas.imprimirNumeros();
        
        double media = estadisticas.calcularMedia();
        int moda = estadisticas.calcularModa();
        double mediana = estadisticas.calcularMediana();
        
        System.out.println("Media aritmetica: " + media);
        System.out.println("Moda: " + moda);
        System.out.println("Mediana: " + mediana);
    }
}
