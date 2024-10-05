import java.util.Scanner;

class CentralBank {
    private Account[] accounts;
    private int accountCount;
    private double totalBalance;

    public CentralBank(int capacity) {
        accounts = new Account[capacity];
        accountCount = 0;
        totalBalance = 0;
    }

    public void createAccount(String name, double balance) {
        if (accountCount < accounts.length) {
            accounts[accountCount] = new Account(name, balance);
            accountCount++;
            totalBalance += balance;
            System.out.println("¡Cuenta creada exitosamente!");
            // Imprimir el número de cuenta generado
            System.out.println("Número de Cuenta: " + accounts[accountCount - 1].getAccountNumber());
        } else {
            System.out.println("¡Lo siento, se ha alcanzado el número máximo de cuentas!");
        }
    }

    public void deposit(String accountNumber, double amount) {
        for (Account account : accounts) {
            if (account != null && account.getAccountNumber().equals(accountNumber)) {
                account.deposit(amount);
                totalBalance += amount;
                System.out.println("¡Depósito exitoso! Nuevo saldo: " + account.getBalance());
                return;
            }
        }
        System.out.println("¡Cuenta no encontrada!");
    }

    public void withdraw(String accountNumber, double amount) {
        for (Account account : accounts) {
            if (account != null && account.getAccountNumber().equals(accountNumber)) {
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

    public void displayAllAccounts() {
        for (Account account : accounts) {
            if (account != null) {
                System.out.println("Número de Cuenta: " + account.getAccountNumber());
                System.out.println("Titular: " + account.getHolder());
                System.out.println("Saldo: " + account.getBalance());
                System.out.println("--------------------");
            }
        }
    }

    public void searchAccount(String holderName) {
        for (Account account : accounts) {
            if (account != null && account.getHolder().equalsIgnoreCase(holderName)) {
                System.out.println("Titular de la Cuenta: " + account.getHolder());
                System.out.println("Número de Cuenta: " + account.getAccountNumber());
                System.out.println("Saldo: " + account.getBalance());
                return;
            }
        }
        System.out.println("¡Cuenta no encontrada!");
    }

    public void closeAccount(String accountNumber) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getAccountNumber().equals(accountNumber)) {
                totalBalance -= accounts[i].getBalance();
                accounts[i] = null;
                System.out.println("¡Cuenta cerrada exitosamente!");
                return;
            }
        }
        System.out.println("¡Cuenta no encontrada!");
    }

    public void displayTotalBalance() {
        System.out.println("Saldo total en el Banco Central: $" + totalBalance);
    }
}

class Account {
    private String accountNumber;
    private String holder;
    private double balance;

    public Account(String name, double initialBalance) {
        accountNumber = generateAccountNumber();
        holder = name;
        balance = initialBalance;
    }

    private String generateAccountNumber() {
        // Generar un número de cuenta de 10 dígitos
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
}

public class Banco1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CentralBank centralBank = new CentralBank(10); // Máximo 10 cuentas

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
                    centralBank.createAccount(holderName, initialBalance);
                    break;
                case 2:
                    System.out.print("Ingrese el número de cuenta: ");
                    String accountNumber = scanner.next();
                    System.out.print("Ingrese la cantidad a depositar: $");
                    double depositAmount = scanner.nextDouble();
                    centralBank.deposit(accountNumber, depositAmount);
                    break;
                case 3:
                    System.out.print("Ingrese el número de cuenta: ");
                    accountNumber = scanner.next();
                    System.out.print("Ingrese la cantidad a retirar: $");
                    double withdrawalAmount = scanner.nextDouble();
                    centralBank.withdraw(accountNumber, withdrawalAmount);
                    break;
                case 4:
                    centralBank.displayAllAccounts();
                    break;
                case 5:
                    System.out.print("Ingrese el nombre del titular de la cuenta: ");
                    String searchHolder = scanner.next();
                    centralBank.searchAccount(searchHolder);
                    break;
                case 6:
                    System.out.print("Ingrese el número de cuenta a cerrar: ");
                    String closeAccountNumber = scanner.next();
                    centralBank.closeAccount(closeAccountNumber);
                    break;
                case 7:
                    centralBank.displayTotalBalance();
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
