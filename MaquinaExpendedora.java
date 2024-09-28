import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Clase que representa un producto con nombre, precio y cantidad.
 */
class Producto {
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void disminuirCantidad() {
        if (cantidad > 0) {
            cantidad--;
        }
    }
}

/**
 * Clase que representa la máquina expendedora.
 */
class MaquinaExpendedora {
    private final int MAX_PRODUCTOS = 10;
    ArrayList<Producto> productos;

    // Colas para cada denominación de moneda
    private Queue<Double> monedas10;
    private Queue<Double> monedas5;
    private Queue<Double> monedas2;
    private Queue<Double> monedas1;
    private Queue<Double> monedas05;
    private final int MAX_MONEDAS = 13;

    // Dinero que el cliente ha insertado en la transacción actual
    private double dineroCliente;
    private Producto productoSeleccionado;

    // Denominaciones en orden descendente para facilitar el cambio
    private final double[] DENOMINACIONES = {10, 5, 2, 1, 0.5};

    // Lista para rastrear las monedas insertadas por el cliente en la transacción actual
    private ArrayList<Double> monedasInsertadas;

    public MaquinaExpendedora() {
        productos = new ArrayList<>();
        monedas10 = new LinkedList<>();
        monedas5 = new LinkedList<>();
        monedas2 = new LinkedList<>();
        monedas1 = new LinkedList<>();
        monedas05 = new LinkedList<>();
        dineroCliente = 0;
        productoSeleccionado = null;
        monedasInsertadas = new ArrayList<>();

        // Inicializar la máquina con algunas monedas para dar cambio
        // Puedes ajustar estos valores según tus necesidades
        for (int i = 0; i < 5; i++) {
            monedas10.add(10.0);
            monedas5.add(5.0);
            monedas2.add(2.0);
            monedas1.add(1.0);
            monedas05.add(0.5);
        }
    }

    /**
     * Agrega un producto a la máquina si no se ha alcanzado el límite.
     */
    public void agregarProducto(String nombre, double precio, int cantidad) {
        if (productos.size() >= MAX_PRODUCTOS) {
            System.out.println("Máximo de 10 productos alcanzado. No se puede agregar más.");
            return;
        }
        productos.add(new Producto(nombre, precio, cantidad));
        System.out.println("Producto agregado: " + nombre + " - Precio: $" + precio + " - Cantidad: " + cantidad);
    }

    /**
     * Permite al cliente insertar una moneda para la compra.
     */
    public void insertarMoneda(double denominacion) {
        if (!esDenominacionValida(denominacion)) {
            System.out.println("Denominación no válida.");
            return;
        }
        if (getCantidadMonedas(denominacion) >= MAX_MONEDAS) {
            System.out.println("Máximo de monedas para $" + denominacion + " alcanzado. No se puede insertar más.");
            return;
        }
        dineroCliente += denominacion;
        monedasInsertadas.add(denominacion);
        agregarMonedaAlSistema(denominacion);
        System.out.println("Moneda de $" + denominacion + " insertada. Dinero acumulado: $" + dineroCliente);
    }

    /**
     * Agrega una moneda a la máquina en la denominación especificada.
     * Este método se utiliza para agregar monedas al sistema (por ejemplo, monedas de cambio).
     */
    private void agregarMonedaAlSistema(double denominacion) {
        switch ((int) (denominacion * 10)) { // Multiplicamos por 10 para manejar 0.5
            case 100: // 10
                monedas10.add(denominacion);
                break;
            case 50: // 5
                monedas5.add(denominacion);
                break;
            case 20: // 2
                monedas2.add(denominacion);
                break;
            case 10: // 1
                monedas1.add(denominacion);
                break;
            case 5: // 0.5
                monedas05.add(denominacion);
                break;
            default:
                // No se hace nada, denominación no válida
                break;
        }
    }

    /**
     * Permite al cliente seleccionar un producto por su nombre.
     */
    public void seleccionarProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                if (p.getCantidad() <= 0) {
                    System.out.println("El producto seleccionado no está disponible.");
                    return;
                }
                productoSeleccionado = p;
                System.out.println("Producto seleccionado: " + p.getNombre() + " - Precio: $" + p.getPrecio());
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    /**
     * Realiza la compra del producto seleccionado si hay suficiente dinero y cambio disponible.
     */
    public void comprarProducto() {
        if (productoSeleccionado == null) {
            System.out.println("No se ha seleccionado ningún producto.");
            return;
        }

        double precio = productoSeleccionado.getPrecio();
        if (dineroCliente < precio) {
            System.out.println("Dinero insuficiente. Por favor, inserta más monedas.");
            return;
        }

        double cambio = Math.round((dineroCliente - precio) * 100.0) / 100.0;
        ArrayList<Double> cambioDevuelto = new ArrayList<>();

        if (cambio > 0) {
            boolean cambioDisponible = calcularCambio(cambio, cambioDevuelto);
            if (!cambioDisponible) {
                System.out.println("No hay suficiente cambio disponible. Transacción cancelada.");
                devolverMonedasInsertadas();
                return;
            }
        }

        // Dispensa el producto
        productoSeleccionado.disminuirCantidad();
        System.out.println("Dispensando producto: " + productoSeleccionado.getNombre());

        // Mostrar cambio
        if (cambio > 0) {
            System.out.println("Cambio devuelto: $" + cambio);
            for (Double moneda : cambioDevuelto) {
                System.out.println("- $" + moneda);
            }
        }

        // Reiniciar la transacción
        dineroCliente = 0;
        monedasInsertadas.clear();
        productoSeleccionado = null;
    }

    /**
     * Calcula y devuelve el cambio utilizando las monedas disponibles.
     * Devuelve true si el cambio pudo ser devuelto completamente, false de lo contrario.
     */
    private boolean calcularCambio(double cambio, ArrayList<Double> cambioDevuelto) {
        double cambioRestante = cambio;

        for (double denom : DENOMINACIONES) {
            while (cambioRestante >= denom - 0.001) { // Ajuste para precisión
                if (quitarMonedaDelSistema(denom)) {
                    cambioDevuelto.add(denom);
                    cambioRestante = Math.round((cambioRestante - denom) * 100.0) / 100.0;
                } else {
                    break;
                }
            }
        }

        return cambioRestante < 0.001; // Si cambioRestante es casi 0, el cambio fue exitoso
    }

    /**
     * Quita una moneda de la denominación especificada del sistema.
     * Devuelve true si se pudo quitar una moneda, false de lo contrario.
     */
    private boolean quitarMonedaDelSistema(double denominacion) {
        switch ((int) (denominacion * 10)) { // Multiplicamos por 10 para manejar 0.5
            case 100: // 10
                if (!monedas10.isEmpty()) {
                    monedas10.poll();
                    return true;
                }
                break;
            case 50: // 5
                if (!monedas5.isEmpty()) {
                    monedas5.poll();
                    return true;
                }
                break;
            case 20: // 2
                if (!monedas2.isEmpty()) {
                    monedas2.poll();
                    return true;
                }
                break;
            case 10: // 1
                if (!monedas1.isEmpty()) {
                    monedas1.poll();
                    return true;
                }
                break;
            case 5: // 0.5
                if (!monedas05.isEmpty()) {
                    monedas05.poll();
                    return true;
                }
                break;
            default:
                // No se hace nada, denominación no válida
                break;
        }
        return false;
    }

    /**
     * Reembolsa las monedas insertadas por el cliente en caso de fallo en la transacción.
     */
    private void devolverMonedasInsertadas() {
        if (monedasInsertadas.isEmpty()) {
            System.out.println("No hay monedas para reembolsar.");
            return;
        }
        System.out.println("Reembolsando monedas:");
        for (Double moneda : monedasInsertadas) {
            quitarMonedaDelSistema(moneda);
            System.out.println("- $" + moneda);
        }
        dineroCliente = 0;
        monedasInsertadas.clear();
    }

    /**
     * Verifica si la máquina tiene dinero para devolver cambio.
     */
    public boolean tieneDinero() {
        return !monedas10.isEmpty() || !monedas5.isEmpty() || !monedas2.isEmpty()
                || !monedas1.isEmpty() || !monedas05.isEmpty();
    }

    /**
     * Imprime el estado actual de la máquina, mostrando productos y monedas disponibles.
     */
    public void imprimirEstado() {
        System.out.println("----- Estado de la Máquina Expendedora -----");
        System.out.println("Productos disponibles:");
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
        } else {
            for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                System.out.println((i + 1) + ". " + p.getNombre() + " - Precio: $" + p.getPrecio() + " - Cantidad: " + p.getCantidad());
            }
        }
        System.out.println("\nMonedas disponibles:");
        System.out.println("$10: " + monedas10.size());
        System.out.println("$5: " + monedas5.size());
        System.out.println("$2: " + monedas2.size());
        System.out.println("$1: " + monedas1.size());
        System.out.println("$0.5: " + monedas05.size());
        System.out.println("--------------------------------------------");
    }

    /**
     * Verifica si una denominación es válida.
     */
    private boolean esDenominacionValida(double denominacion) {
        for (double denom : DENOMINACIONES) {
            if (denom == denominacion) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna la cantidad de monedas disponibles para una denominación específica.
     */
    private int getCantidadMonedas(double denominacion) {
        switch ((int) (denominacion * 10)) { // Multiplicamos por 10 para manejar 0.5
            case 100: // 10
                return monedas10.size();
            case 50: // 5
                return monedas5.size();
            case 20: // 2
                return monedas2.size();
            case 10: // 1
                return monedas1.size();
            case 5: // 0.5
                return monedas05.size();
            default:
                return 0;
        }
    }


/**
 * Clase principal que contiene el menú interactivo para la máquina expendedora.
 */

    public static void main(String[] args) {
        MaquinaExpendedora maquina = new MaquinaExpendedora();
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Máquina Expendedora ---");
            System.out.println("1. Agregar Producto (Máximo 10)");
            System.out.println("2. Comprar Producto y Mostrar Estado");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    // Agregar Producto
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = scanner.nextLine();

                    double precio = 0;
                    while (true) {
                        System.out.print("Ingrese el precio del producto: ");
                        try {
                            precio = Double.parseDouble(scanner.nextLine());
                            if (precio <= 0) {
                                System.out.println("El precio debe ser mayor que 0.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Precio inválido. Intente nuevamente.");
                        }
                    }

                    int cantidad = 0;
                    while (true) {
                        System.out.print("Ingrese la cantidad del producto: ");
                        try {
                            cantidad = Integer.parseInt(scanner.nextLine());
                            if (cantidad <= 0) {
                                System.out.println("La cantidad debe ser mayor que 0.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Cantidad inválida. Intente nuevamente.");
                        }
                    }

                    maquina.agregarProducto(nombre, precio, cantidad);
                    break;

                case 2:
                    // Comprar Producto y Mostrar Estado
                    if (maquina.productos.isEmpty()) {
                        System.out.println("No hay productos disponibles para comprar.");
                        break;
                    }

                    maquina.imprimirEstado();

                    System.out.print("Ingrese el nombre del producto que desea comprar: ");
                    String prodSeleccion = scanner.nextLine();
                    maquina.seleccionarProducto(prodSeleccion);

                    // Insertar monedas
                    while (true) {
                        System.out.print("Inserte una moneda (10, 5, 2, 1, 0.5) o escriba 'fin' para finalizar la inserción: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("fin")) {
                            break;
                        }
                        double moneda = 0;
                        try {
                            moneda = Double.parseDouble(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Denominación inválida. Intente nuevamente.");
                            continue;
                        }
                        maquina.insertarMoneda(moneda);
                    }

                    maquina.comprarProducto();
                    maquina.imprimirEstado();
                    break;

                case 0:
                    System.out.println("Saliendo de la máquina expendedora. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción del 0 al 2.");
            }
        }

        scanner.close();
    }
}



