package br.com.clinic.presentation;

import br.com.clinic.service.classes.PacienteService;
import br.com.clinic.service.interfaces.IAtendimentoService;
import br.com.clinic.service.interfaces.IFinanceiroService;
import br.com.clinic.service.interfaces.IPacienteService;
import br.com.clinic.service.interfaces.IProfissionalService;

import java.util.Scanner;

public class Menu {
    IPacienteService pacienteService;
    IFinanceiroService financeiroService;
    IProfissionalService profissionalService;
    IAtendimentoService atendimentoService;

    public void run() {
        this.pacienteService = new PacienteService();
        InterfaceUi ui = new InterfaceUi();
        PacienteUi pacienteui = new PacienteUi(ui);

        while (true) {
            System.out.println("\n=== Sistema da Clínica de Estética ===");
            System.out.println("\nOpções:");
            System.out.println("1 - Paciente");
            System.out.println("2 - Profissional");
            System.out.println("3 - Atendimento");
            System.out.println("4 - Financeiro");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = ui.lerInt();
            if (opcao == 0) {
                ui.apagar();
                System.out.println("Saindo...");
                break;
            }

            ui.apagar();
            switch (opcao) {
                case 1 -> pacienteui.show(this.pacienteService);
                default -> System.out.println("Opção inválida.");
            }
        }
        ui.close();
    }
}
