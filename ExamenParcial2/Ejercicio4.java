import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa una palabra: ");
        String palabra = scanner.nextLine();

        palabra = palabra.toLowerCase();
        
        
        List<Character> lista = new ArrayList<>();
        for (char c : palabra.toCharArray()) {
            lista.add(c);
        }

       
        boolean esPalindromo = true;
        int n = lista.size();
        for (int i = 0; i < n / 2; i++) {
            if (!lista.get(i).equals(lista.get(n - i - 1))) {
                esPalindromo = false;
                break;
            }
        }

     
        if (esPalindromo) {
            System.out.println("En efecto... es un palindromo.");
        } else {
            System.out.println("Ni modo pa, no es un palindromo.");
        }
        
        scanner.close();
    }
}
