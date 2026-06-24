package br.com.clinic.presentation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InterfaceUi {
    Scanner scanner = new Scanner(System.in);
    public LocalDate lerData(String prompt) {
        DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        while (true) {
            String texto = lerTexto(prompt);
            try {
                return LocalDate.parse(texto, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida. Use dd/MM/yyyy.");
            }
        }
    }

    public void apagar() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int lerInt() {
        while (!this.scanner.hasNextInt()) {
            this.scanner.nextLine();
            System.out.print("Informe um número valido: ");
        }
        int valor = this.scanner.nextInt();
        this.scanner.nextLine();
        return valor;
    }

    public long lerLong(String prompt) {
        System.out.print(prompt);
        while (!this.scanner.hasNextLong()) {
            this.scanner.nextLine();
            System.out.print("Informe um número valido: ");
        }
        long valor = this.scanner.nextLong();
        this.scanner.nextLine();
        return valor;
    }

    public double lerDouble(String prompt) {
        System.out.print(prompt);
        while (!this.scanner.hasNextDouble()) {
            this. scanner.nextLine();
            System.out.print("Informe um número valido: ");
        }
        double valor = this.scanner.nextDouble();
        this.scanner.nextLine();
        return valor;
    }

    public String lerTexto(String prompt) {
        System.out.print(prompt);
        return this.scanner.nextLine().trim();
    }

    public void close() {
        this.apagar();
        this.scanner.close();
    }
}
