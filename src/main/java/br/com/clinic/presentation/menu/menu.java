package br.com.clinic.presentation.menu;

import br.com.clinic.application.service.implementations.AtendimentoService;
import br.com.clinic.application.service.implementations.FinanceiroService;
import br.com.clinic.application.service.implementations.PacienteService;
import br.com.clinic.application.service.implementations.ProfissionalService;
import br.com.clinic.application.service.interfaces.IAtendimentoService;
import br.com.clinic.application.service.interfaces.IFinanceiroService;
import br.com.clinic.application.service.interfaces.IPacienteService;
import br.com.clinic.application.service.interfaces.IProfissionalService;
import br.com.clinic.domain.enums.FormaPagamento;
import br.com.clinic.domain.enums.SexoEnum;
import br.com.clinic.domain.enums.TipoProcedimento;
import br.com.clinic.domain.model.atendimento.Atendimento;
import br.com.clinic.domain.model.financeiro.Pagamento;
import br.com.clinic.domain.model.procedimento.Procedimento;
import br.com.clinic.domain.model.pessoa.Endereco;
import br.com.clinic.domain.model.pessoa.Paciente;
import br.com.clinic.domain.model.pessoa.Profissional;
import br.com.clinic.infrastructure.repository.AtendimentoRepository;
import br.com.clinic.infrastructure.repository.FinanceiroRepository;
import br.com.clinic.domain.repository.IPacienteRepository;
import br.com.clinic.domain.repository.IProfissionalRepository;
import br.com.clinic.infrastructure.repository.AtendimentoRepository;
import br.com.clinic.infrastructure.repository.PacienteRepository;
import br.com.clinic.infrastructure.repository.ProfissionalRepository;
import br.com.clinic.infrastructure.repository.FinanceiroRepository;
import br.com.clinic.infrastructure.repository.AtendimentoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class menu {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        IPacienteRepository pacienteRepository = new PacienteRepository();
        IProfissionalRepository profissionalRepository = new ProfissionalRepository();
        br.com.clinic.domain.repository.IFinanceiroRepository financeiroRepository = new FinanceiroRepository();
        br.com.clinic.domain.repository.IAtendimentoRepository atendimentoRepository = new AtendimentoRepository();

        IPacienteService pacienteService = new PacienteService(pacienteRepository);
        IProfissionalService profissionalService = new ProfissionalService(profissionalRepository);
        IFinanceiroService financeiroService = new FinanceiroService(financeiroRepository);
        IAtendimentoService atendimentoService = new AtendimentoService(atendimentoRepository, pacienteService, profissionalService, financeiroService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== CLÍNICA TUI ===");
            System.out.println("1 - Paciente");
            System.out.println("2 - Profissional");
            System.out.println("3 - Atendimento");
            System.out.println("4 - Financeiro");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInt(scanner);
            if (opcao == 0) {
                System.out.println("Saindo...");
                break;
            }

            switch (opcao) {
                case 1 -> menuPaciente(scanner, pacienteService);
                case 2 -> menuProfissional(scanner, profissionalService);
                case 3 -> menuAtendimento(scanner, atendimentoService);
                case 4 -> menuFinanceiro(scanner, financeiroService);
                default -> System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    private static void menuPaciente(Scanner scanner, IPacienteService service) {
        while (true) {
            System.out.println("\n--- PACIENTE ---");
            System.out.println("1 - Cadastrar paciente");
            System.out.println("2 - Listar pacientes");
            System.out.println("3 - Buscar por CPF");
            System.out.println("4 - Buscar por nome");
            System.out.println("5 - Deletar paciente");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            int opcao = lerInt(scanner);
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> cadastrarPaciente(scanner, service);
                case 2 -> listarPacientes(service);
                case 3 -> buscarPacienteCpf(scanner, service);
                case 4 -> buscarPacienteNome(scanner, service);
                case 5 -> deletarPaciente(scanner, service);
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuProfissional(Scanner scanner, IProfissionalService service) {
        while (true) {
            System.out.println("\n--- PROFISSIONAL ---");
            System.out.println("1 - Cadastrar profissional");
            System.out.println("2 - Listar profissionais");
            System.out.println("3 - Buscar por especialidade");
            System.out.println("4 - Deletar profissional");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            int opcao = lerInt(scanner);
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> cadastrarProfissional(scanner, service);
                case 2 -> listarProfissionais(service);
                case 3 -> buscarProfissionalEspecialidade(scanner, service);
                case 4 -> deletarProfissional(scanner, service);
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuAtendimento(Scanner scanner, IAtendimentoService service) {
        while (true) {
            System.out.println("\n--- ATENDIMENTO ---");
            System.out.println("1 - Agendar atendimento");
            System.out.println("2 - Listar atendimentos");
            System.out.println("3 - Buscar por data");
            System.out.println("4 - Finalizar atendimento");
            System.out.println("5 - Cancelar atendimento");
            System.out.println("6 - Deletar atendimento");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            int opcao = lerInt(scanner);
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> agendarAtendimento(scanner, service);
                case 2 -> listarAtendimentos(service);
                case 3 -> buscarAtendimentoData(scanner, service);
                case 4 -> finalizarAtendimento(scanner, service);
                case 5 -> cancelarAtendimento(scanner, service);
                case 6 -> deletarAtendimento(scanner, service);
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuFinanceiro(Scanner scanner, IFinanceiroService service) {
        while (true) {
            System.out.println("\n--- FINANCEIRO ---");
            System.out.println("1 - Gerar pagamento");
            System.out.println("2 - Listar pagamentos");
            System.out.println("3 - Buscar pagamento por ID");
            System.out.println("4 - Deletar pagamento");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            int opcao = lerInt(scanner);
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> gerarPagamento(scanner, service);
                case 2 -> listarPagamentos(service);
                case 3 -> buscarPagamentoPorId(scanner, service);
                case 4 -> deletarPagamento(scanner, service);
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarPaciente(Scanner scanner, IPacienteService service) {
        System.out.println("\nCadastro de paciente");
        String nome = lerTexto(scanner, "Nome: ");
        String cpf = lerTexto(scanner, "CPF: ");
        String dataNascimento = lerTexto(scanner, "Data de nascimento (dd/MM/yyyy): ");
        String telefone = lerTexto(scanner, "Telefone: ");
        String email = lerTexto(scanner, "Email: ");
        String rua = lerTexto(scanner, "Rua: ");
        String bairro = lerTexto(scanner, "Bairro: ");
        String cidade = lerTexto(scanner, "Cidade: ");
        String cep = lerTexto(scanner, "CEP: ");
        SexoEnum sexo = escolherSexo(scanner);
        double altura = lerDouble(scanner, "Altura: ");
        double peso = lerDouble(scanner, "Peso: ");
        String alergia = lerTexto(scanner, "Alergias: ");
        String objetivo = lerTexto(scanner, "Objetivo estético: ");

        Endereco endereco = new Endereco(rua, bairro, cidade, cep);
        Paciente paciente = new Paciente(altura, peso, alergia, objetivo, nome, cpf, dataNascimento, telefone, email, endereco, sexo);

        service.salvar(paciente);
        System.out.println("Paciente cadastrado com sucesso.");
    }

    private static void cadastrarProfissional(Scanner scanner, IProfissionalService service) {
        System.out.println("\nCadastro de profissional");
        String nome = lerTexto(scanner, "Nome: ");
        String cpf = lerTexto(scanner, "CPF: ");
        String dataNascimento = lerTexto(scanner, "Data de nascimento (dd/MM/yyyy): ");
        String telefone = lerTexto(scanner, "Telefone: ");
        String email = lerTexto(scanner, "Email: ");
        String rua = lerTexto(scanner, "Rua: ");
        String bairro = lerTexto(scanner, "Bairro: ");
        String cidade = lerTexto(scanner, "Cidade: ");
        String cep = lerTexto(scanner, "CEP: ");
        SexoEnum sexo = escolherSexo(scanner);
        String cargo = lerTexto(scanner, "Cargo: ");
        String especialidade = lerTexto(scanner, "Especialidade: ");
        String registro = lerTexto(scanner, "Registro profissional: ");
        String dataContratacao = lerTexto(scanner, "Data de contratação (dd/MM/yyyy): ");
        double salario = lerDouble(scanner, "Salário: ");

        Endereco endereco = new Endereco(rua, bairro, cidade, cep);
        Profissional profissional = new Profissional(cargo, especialidade, registro, dataContratacao, salario, nome, cpf, dataNascimento, telefone, email, endereco, sexo);

        service.salvar(profissional);
        System.out.println("Profissional cadastrado com sucesso.");
    }

    private static void agendarAtendimento(Scanner scanner, IAtendimentoService service) {
        System.out.println("\nAgendamento de atendimento");
        Long pacienteId = lerLong(scanner, "ID do paciente: ");
        Long profissionalId = lerLong(scanner, "ID do profissional: ");
        LocalDate data = lerData(scanner, "Data do atendimento (dd/MM/yyyy): ");

        int quantidade = lerInt(scanner, "Quantidade de procedimentos: ");
        List<Procedimento> procedimentos = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            System.out.println("Procedimento " + (i + 1));
            TipoProcedimento tipo = escolherProcedimento(scanner);
            double valor = lerDouble(scanner, "Valor: ");
            int duracao = lerInt(scanner, "Duração em minutos: ");
            String descricao = lerTexto(scanner, "Descrição: ");
            procedimentos.add(new Procedimento(i + 1, tipo, valor, duracao, descricao));
        }

        Atendimento atendimento = service.criarAtendimento(pacienteId, profissionalId, procedimentos, data);
        System.out.println("Atendimento criado: " + atendimento);
    }

    private static void finalizarAtendimento(Scanner scanner, IAtendimentoService service) {
        Long id = lerLong(scanner, "ID do atendimento: ");
        FormaPagamento forma = escolherFormaPagamento(scanner);
        Atendimento atendimento = service.finalizarAtendimento(id, forma);
        System.out.println("Atendimento finalizado: " + atendimento);
    }

    private static void cancelarAtendimento(Scanner scanner, IAtendimentoService service) {
        Long id = lerLong(scanner, "ID do atendimento: ");
        Atendimento atendimento = service.cancelarAtendimento(id);
        System.out.println("Atendimento cancelado: " + atendimento);
    }

    private static void gerarPagamento(Scanner scanner, IFinanceiroService service) {
        System.out.println("\nGerar pagamento");
        double valor = lerDouble(scanner, "Valor: ");
        FormaPagamento forma = escolherFormaPagamento(scanner);
        Pagamento pagamento = service.gerarPagamento(valor, forma);
        System.out.println("Pagamento gerado: " + pagamento);
    }

    private static void listarPacientes(IPacienteService service) {
        System.out.println("\nPacientes cadastrados:");
        service.listarTodos().forEach(p -> System.out.println(p));
    }

    private static void listarProfissionais(IProfissionalService service) {
        System.out.println("\nProfissionais cadastrados:");
        service.listarTodos().forEach(p -> System.out.println(p));
    }

    private static void listarAtendimentos(IAtendimentoService service) {
        System.out.println("\nAtendimentos cadastrados:");
        service.listarTodos().forEach(a -> System.out.println(a));
    }

    private static void listarPagamentos(IFinanceiroService service) {
        System.out.println("\nPagamentos cadastrados:");
        service.listarTodos().forEach(p -> System.out.println(p));
    }

    private static void buscarPacienteCpf(Scanner scanner, IPacienteService service) {
        String cpf = lerTexto(scanner, "CPF: ");
        service.buscarPorCpf(cpf).ifPresentOrElse(
                p -> System.out.println(p),
                () -> System.out.println("Paciente não encontrado.")
        );
    }

    private static void buscarPacienteNome(Scanner scanner, IPacienteService service) {
        String nome = lerTexto(scanner, "Nome: ");
        List<?> lista = service.buscarPorNome(nome);
        System.out.println("Resultados:");
        lista.forEach(System.out::println);
    }

    private static void buscarProfissionalEspecialidade(Scanner scanner, IProfissionalService service) {
        String especialidade = lerTexto(scanner, "Especialidade: ");
        List<?> lista = service.buscarPorEspecialidade(especialidade);
        System.out.println("Resultados:");
        lista.forEach(System.out::println);
    }

    private static void buscarAtendimentoData(Scanner scanner, IAtendimentoService service) {
        LocalDate data = lerData(scanner, "Data (dd/MM/yyyy): ");
        service.buscarPorData(data).forEach(System.out::println);
    }

    private static void buscarPagamentoPorId(Scanner scanner, IFinanceiroService service) {
        Long id = lerLong(scanner, "ID do pagamento: ");
        Optional<Pagamento> pagamento = service.buscarPorId(id);
        pagamento.ifPresentOrElse(System.out::println, () -> System.out.println("Pagamento não encontrado."));
    }

    private static void deletarPaciente(Scanner scanner, IPacienteService service) {
        Long id = lerLong(scanner, "ID do paciente: ");
        service.deletar(id);
        System.out.println("Paciente excluído (se existente).");
    }

    private static void deletarProfissional(Scanner scanner, IProfissionalService service) {
        Long id = lerLong(scanner, "ID do profissional: ");
        service.deletar(id);
        System.out.println("Profissional excluído (se existente).");
    }

    private static void deletarAtendimento(Scanner scanner, IAtendimentoService service) {
        Long id = lerLong(scanner, "ID do atendimento: ");
        service.deletar(id);
        System.out.println("Atendimento excluído (se existente).");
    }

    private static void deletarPagamento(Scanner scanner, IFinanceiroService service) {
        Long id = lerLong(scanner, "ID do pagamento: ");
        service.deletar(id);
        System.out.println("Pagamento excluído (se existente).");
    }

    private static TipoProcedimento escolherProcedimento(Scanner scanner) {
        System.out.println("Tipos de procedimento:");
        TipoProcedimento[] valores = TipoProcedimento.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + " - " + valores[i]);
        }
        System.out.print("Escolha: ");
        int opcao = lerInt(scanner);
        return valores[Math.max(0, Math.min(opcao - 1, valores.length - 1))];
    }

    private static FormaPagamento escolherFormaPagamento(Scanner scanner) {
        System.out.println("Formas de pagamento:");
        FormaPagamento[] valores = FormaPagamento.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + " - " + valores[i].getDescricao());
        }
        System.out.print("Escolha: ");
        int opcao = lerInt(scanner);
        return valores[Math.max(0, Math.min(opcao - 1, valores.length - 1))];
    }

    private static SexoEnum escolherSexo(Scanner scanner) {
        System.out.println("Sexo:");
        SexoEnum[] valores = SexoEnum.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + " - " + valores[i]);
        }
        System.out.print("Escolha: ");
        int opcao = lerInt(scanner);
        return valores[Math.max(0, Math.min(opcao - 1, valores.length - 1))];
    }

    private static LocalDate lerData(Scanner scanner, String prompt) {
        while (true) {
            String texto = lerTexto(scanner, prompt);
            try {
                return LocalDate.parse(texto, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use dd/MM/yyyy.");
            }
        }
    }

    private static int lerInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Informe um número válido: ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private static int lerInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return lerInt(scanner);
    }

    private static long lerLong(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            scanner.nextLine();
            System.out.print("Informe um número válido: ");
        }
        long valor = scanner.nextLong();
        scanner.nextLine();
        return valor;
    }

    private static double lerDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.nextLine();
            System.out.print("Informe um número válido: ");
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    private static String lerTexto(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}