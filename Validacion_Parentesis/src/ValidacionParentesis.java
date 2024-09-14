import java.util.Stack;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ValidacionParentesis {

    public static boolean validarParentesis(String ecuacion) {
        Stack<Character> pila = new Stack<>();

        for (int i = 0; i < ecuacion.length(); i++) {
            char caracter = ecuacion.charAt(i);

            if (caracter == '(') {
                pila.push(caracter);
            } else if (caracter == ')') {
                if (pila.isEmpty() || pila.pop() != '(') {
                    return false;
                }
            }
        }

        return pila.isEmpty();
    }

    public static double resolverEcuacion(String ecuacion) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        return (double) engine.eval(ecuacion);
    }

    public static void main(String[] args) {
        String ecuacion = "(2 + 3) * (5 - (3 + 1))";
        
        if (validarParentesis(ecuacion)) {
            try {
                double resultado = resolverEcuacion(ecuacion);
                System.out.println("La ecuación es válida. Resultado: " + resultado);
            } catch (ScriptException e) {
                System.out.println("Error al resolver la ecuación: " + e.getMessage());
            }
        } else {
            System.out.println("Los paréntesis no son válidos.");
        }
    }
}
