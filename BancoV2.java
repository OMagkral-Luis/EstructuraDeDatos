import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner;

public class BancoV2 {

    private PriorityQueue<Account> accountQueue;
    private double totalBalance;

    // Constructor del banco
    public BancoV2() {
        // Inicializamos la cola de prioridad con un comparador personalizado para las cuentas
        accountQueue = new PriorityQueue<>(new AccountPriorityComparator());
        totalBalance = 0;
    }

    // Método para crear una nueva cuenta
    public void createAccount(String name, double balance) {
        Account newAccount = new Account(name, balance);
        accountQueue.add(newAccount);
        totalBalance += balance;
        System.out.println("¡Cuenta creada exitosamente!");
        System.out.println("Número de Cuenta: " + newAccount.getAccountNumber());
    }

    // Método para depositar dinero
    public void deposit(String accountNumber, double amount) {
        for (Account account : accountQueue) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.deposit(amount);
                totalBalance += amount;
                System.out.println("¡Depósito exitoso! Nuevo saldo: " + account.getBalance());
                return;
            }
        }
        System.out.println("¡Cuenta no encontrada!");
    }

    // Método para retirar dinero
    public void withdraw(String accountNumber, double amount) {
        for (Account account : accountQueue) {
            if (account.getAccountNumber().equals(accountNumber)) {
                if (account.withdraw(amount)) {
                    totalBalance -= amount;
                    System.out.println("¡Retiro exitoso! Saldo restante: " + account.getBalance());
                    return;
                } else {
                    System.out.println("¡Fondos insuficientes!");
                }
            }
        }
        System.out.println("¡Cuenta no encontrada!");
    }

    // Mostrar todas las cuentas, en orden de prioridad
    public void displayAllAccounts() {
        if (accountQueue.isEmpty()) {
            System.out.println("No hay cuentas para mostrar.");
            return;
        }

        PriorityQueue<Account> tempQueue = new PriorityQueue<>(accountQueue);
        while (!tempQueue.isEmpty()) {
            Account account = tempQueue.poll();
            System.out.println("Número de Cuenta: " + account.getAccountNumber());
            System.out.println("Titular: " + account.getHolder());
            System.out.println("Saldo: " + account.getBalance());
            System.out.println("--------------------");
        }
    }

    // Buscar una cuenta por el nombre del titular
    public void searchAccount(String holderName) {
        for (Account account : accountQueue) {
            if (account.getHolder().equalsIgnoreCase(holderName)) {
                System.out.println("Titular de la Cuenta: " + account.getHolder());
                System.out.println("Número de Cuenta: " + account.getAccountNumber());
                System.out.println("Saldo: " + account.getBalance());
                return;
            }
        }
        System.out.println("¡Cuenta no encontrada!");
    }

    // Cerrar una cuenta
    public void closeAccount(String accountNumber) {
        Account accountToRemove = null;
        for (Account account : accountQueue) {
            if (account.getAccountNumber().equals(accountNumber)) {
                accountToRemove = account;
                break;
            }
        }
        if (accountToRemove != null) {
            totalBalance -= accountToRemove.getBalance();
            accountQueue.remove(accountToRemove);
            System.out.println("¡Cuenta cerrada exitosamente!");
        } else {
            System.out.println("¡Cuenta no encontrada!");
        }
    }

    // Mostrar el saldo total del banco
    public void displayTotalBalance() {
        System.out.println("Saldo total en el Banco Central: $" + totalBalance);
    }

    // Clase interna que representa una cuenta bancaria
    class Account {
        private String accountNumber;
        private String holder;
        private double balance;

        public Account(String name, double initialBalance) {
            this.accountNumber = generateAccountNumber();
            this.holder = name;
            this.balance = initialBalance;
        }

        private String generateAccountNumber() {
            long number = (long) (Math.random() * 1_000_000_0000L);
            return String.format("%010d", number);
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getHolder() {
            return holder;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            balance += amount;
        }

        public boolean withdraw(double amount) {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        }

        // Método que determina la prioridad basada en el número de cuenta
        public int getPriority() {
            if (accountNumber.startsWith("042")) {
                return 1;
            } else if (accountNumber.startsWith("022")) {
                return 2;
            } else if (accountNumber.startsWith("011")) {
                return 3;
            }
            return 4; // Por defecto, prioridad menor
        }
    }

    // Comparador para ordenar las cuentas por prioridad
    class AccountPriorityComparator implements Comparator<Account> {
        @Override
        public int compare(Account a1, Account a2) {
            return Integer.compare(a1.getPriority(), a2.getPriority());
        }
    }

    // Método principal
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BancoV2 banco = new BancoV2();

        while (true) {
            System.out.println("\nMenú del Banco Central");
            System.out.println("1. Crear Cuenta");
            System.out.println("2. Depositar Dinero");
            System.out.println("3. Retirar Dinero");
            System.out.println("4. Mostrar Todas las Cuentas");
            System.out.println("5. Buscar Cuenta");
            System.out.println("6. Cerrar Cuenta");
            System.out.println("7. Mostrar Saldo Total");
            System.out.println("8. Salir");

            System.out.print("Ingrese su elección: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Ingrese el nombre del titular de la cuenta: ");
                    String holderName = scanner.next();
                    System.out.print("Ingrese el saldo inicial: $");
                    double initialBalance = scanner.nextDouble();
                    banco.createAccount(holderName, initialBalance);
                    break;
                case 2:
                    System.out.print("Ingrese el número de cuenta: ");
                    String accountNumber = scanner.next();
                    System.out.print("Ingrese la cantidad a depositar: $");
                    double depositAmount = scanner.nextDouble();
                    banco.deposit(accountNumber, depositAmount);
                    break;
                case 3:
                    System.out.print("Ingrese el número de cuenta: ");
                    accountNumber = scanner.next();
                    System.out.print("Ingrese la cantidad a retirar: $");
                    double withdrawalAmount = scanner.nextDouble();
                    banco.withdraw(accountNumber, withdrawalAmount);
                    break;
                case 4:
                    banco.displayAllAccounts();
                    break;
                case 5:
                    System.out.print("Ingrese el nombre del titular de la cuenta: ");
                    String searchHolder = scanner.next();
                    banco.searchAccount(searchHolder);
                    break;
                case 6:
                    System.out.print("Ingrese el número de cuenta a cerrar: ");
                    String closeAccountNumber = scanner.next();
                    banco.closeAccount(closeAccountNumber);
                    break;
                case 7:
                    banco.displayTotalBalance();
                    break;
                case 8:
                    System.out.println("¡Gracias por bancar con nosotros!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción inválida. Por favor, inténtelo de nuevo.");
            }
        }
    }
}

