package br.com.clinic.presentation;

import br.com.clinic.service.classes.FinanceiroService;
import br.com.clinic.service.classes.PacienteService;
import br.com.clinic.service.classes.ProfissionalService;
import br.com.clinic.service.interfaces.IAtendimentoService;
import br.com.clinic.service.interfaces.IFinanceiroService;
import br.com.clinic.service.interfaces.IPacienteService;
import br.com.clinic.service.interfaces.IProfissionalService;

public class Menu {
    IPacienteService pacienteService;
    IFinanceiroService financeiroService;
    IProfissionalService profissionalService;
    IAtendimentoService atendimentoService;

    public void run() {
        this.pacienteService = new PacienteService();
        this.financeiroService = new FinanceiroService();
        this.profissionalService = new ProfissionalService();

        InterfaceUi ui = new InterfaceUi();
        UI<IPacienteService> pacienteui = new PacienteUi(ui);
        UI<IFinanceiroService> financeiroui = new FinanceiroUi(ui);
        UI<IProfissionalService> profissionalui = new ProfissionalUi(ui);

        while (true) {
            System.out.println("\n=== Sistema da Clinica de Estetica ===");
            System.out.println("\nOpcoes:");
            System.out.println("1 - Paciente");
            System.out.println("2 - Profissional");
            System.out.println("3 - Atendimento");
            System.out.println("4 - Financeiro");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            int opcao = ui.lerInt("");
            if (opcao == 0) {
                ui.apagar();
                System.out.println("Saindo...");
                break;
            }

            ui.apagar();
            switch (opcao) {
                case 1 -> pacienteui.show(this.pacienteService);
                case 2 -> profissionalui.show(this.profissionalService);
                case 4 -> financeiroui.show(this.financeiroService);
                default -> System.out.println("Opcao invalida.");
            }
        }
        ui.close();
    }
}
