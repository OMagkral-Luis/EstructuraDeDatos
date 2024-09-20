import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Problema1 {

    static class Metodo {
        String[] parametros;

        public Metodo(String nombre, String... parametros) {
            this.parametros = parametros;
        }
    }

    public static void main(String[] args) {
       
        Map<String, Metodo> metodosValidos = new HashMap<>();
        metodosValidos.put("write", new Metodo("write", "String", "int"));
        metodosValidos.put("read", new Metodo("read", "String"));
        

        
        String llamada = "write(String, int)";

        
        Stack<String> pila = new Stack<>();
        String[] elementos = llamada.replaceAll("[()]", "").split("\\s+");
        for (int i = elementos.length - 1; i >= 0; i--) {
            pila.push(elementos[i]);
        }

        
        if (verificarLlamada(pila, metodosValidos)) {
            System.out.println("Compilado");
        } else {
            System.out.println("Error al compilar");
        }
    }

    private static boolean verificarLlamada(Stack<String> pila, Map<String, Metodo> metodosValidos) {
        String nombreMetodo = pila.pop();
        Metodo metodo = metodosValidos.get(nombreMetodo);
        if (metodo == null) {
            return false;
        }

        
        if (pila.size() != metodo.parametros.length) {
            return false;
        }

        for (String parametro : metodo.parametros) {
            if (!pila.pop().equals(parametro)) {
                return false;
            }
        }

        return true;
    }
}
