package br.com.clinic.presentation;

import br.com.clinic.model.classes.pessoa.Endereco;
import br.com.clinic.model.classes.pessoa.Paciente;
import br.com.clinic.model.classes.pessoa.Profissional;
import br.com.clinic.model.enums.SexoEnum;
import br.com.clinic.service.classes.AtendimentoService;
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
        this.atendimentoService = new AtendimentoService(pacienteService, profissionalService, financeiroService);

        InterfaceUi ui = new InterfaceUi();
        UI<IPacienteService> pacienteui = new PacienteUi(ui);
        UI<IFinanceiroService> financeiroui = new FinanceiroUi(ui);
        UI<IProfissionalService> profissionalui = new ProfissionalUi(ui);
        UI<IAtendimentoService> atendimentoui = new AtendimentoUI(ui);

        //region Mock-Dados
        // Criando um endereço para os testes
        Endereco endereco1 = new Endereco(
                "Rua das Flores, 123",
                "Centro",
                "São Paulo",
                "01000-000"
        );

        Endereco endereco2 = new Endereco(
                "Avenida Brasil, 456",
                "Jardim Paulista",
                "Campinas",
                "13000-000"
        );

        //--- Mock de Profissional ---
        Profissional profissional1 = new Profissional(
                "Fisioterapeuta",
                "Ortopedia",
                "CREFITO-12345",
                "10/01/2020",
                4500.00,
                "Carlos Mendes",
                "333.333.333-33",
                "05/03/1980",
                "(11) 97777-7777",
                "carlos.mendes@clinic.com",
                endereco1,
                SexoEnum.M
        );

        Profissional profissional2 = new Profissional(
                "Nutricionista",
                "Nutrição Esportiva",
                "CRN-54321",
                "15/06/2022",
                5000.00,
                "Ana Lima",
                "444.444.444-44",
                "12/08/1992",
                "(11) 96666-6666",
                "ana.lima@clinic.com",
                endereco2,
                SexoEnum.F
        );

        // --- Mock de Paciente ---
        Paciente paciente1 = new Paciente(
                1.75,
                70.5,
                "Nenhuma",
                "Emagrecimento",
                "João Silva",
                "111.111.111-11",
                "15/05/1990",
                "(11) 99999-9999",
                "joao.silva@email.com",
                endereco1,
                SexoEnum.M
        );

        Paciente paciente2 = new Paciente(
                1.60,
                65.0,
                "Dipirona",
                "Hipertrofia",
                "Maria Souza",
                "222.222.222-22",
                "20/10/1985",
                "(11) 98888-8888",
                "maria.souza@email.com",
                endereco2,
                SexoEnum.F
        );
        //endregion

        pacienteService.salvar(paciente1);
        pacienteService.salvar(paciente2);
        profissionalService.salvar(profissional1);
        profissionalService.salvar(profissional2);

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
                case 3 -> atendimentoui.show(this.atendimentoService);
                case 4 -> financeiroui.show(this.financeiroService);
                default -> System.out.println("Opcao invalida.");
            }
        }
        ui.close();
    }
}
