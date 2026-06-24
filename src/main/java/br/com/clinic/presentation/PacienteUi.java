package br.com.clinic.presentation;

import br.com.clinic.model.classes.pessoa.Endereco;
import br.com.clinic.model.classes.pessoa.Paciente;
import br.com.clinic.model.enums.SexoEnum;
import br.com.clinic.service.interfaces.IPacienteService;

import java.util.concurrent.atomic.AtomicInteger;

public class PacienteUi implements UI<IPacienteService> {
    InterfaceUi ui;

    public PacienteUi(InterfaceUi ui) {
        this.ui = ui;
    }

    public void show(IPacienteService service) {
        while (true) {
            System.out.println("\n--- Paciente ---");
            System.out.println("1 - Cadastrar paciente");
            System.out.println("2 - Listar pacientes");
            System.out.println("3 - Buscar por nome");
            System.out.println("4 - Deletar paciente");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            int opcao = this.ui.lerInt("");
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> this.cadastrarPaciente(service);
                case 2 -> this.listarPacientes(service);
                case 3 -> this.buscarPacienteNome(service);
                case 4 -> this.deletarPaciente(service);
                default -> System.out.println("Opcao inválida.");
            }
        }
    }

    private void cadastrarPaciente(IPacienteService service) {
        this.ui.apagar();
        System.out.println("\nCadastro de paciente");
        String nome = this.ui.lerTexto("Nome: ");
        String cpf = this.ui.lerTexto("CPF: ");
        String dataNascimento = this.ui.lerTexto("Data de nascimento (dd/MM/yyyy): ");
        String telefone = this.ui.lerTexto("Telefone: ");
        String email = this.ui.lerTexto("Email: ");
        String rua = this.ui.lerTexto("Rua: ");
        String bairro = this.ui.lerTexto("Bairro: ");
        String cidade = this.ui.lerTexto("Cidade: ");
        String cep = this.ui.lerTexto("CEP: ");
        SexoEnum sexo = this.escolherSexo();
        double altura = this.ui.lerDouble("Altura: ");
        double peso = this.ui.lerDouble("Peso: ");
        String alergia = this.ui.lerTexto("Alergias: ");
        String objetivo = this.ui.lerTexto("Objetivo estetico: ");

        Endereco endereco = new Endereco(rua, bairro, cidade, cep);
        Paciente paciente = new Paciente(altura, peso, alergia, objetivo, nome, cpf, dataNascimento, telefone, email, endereco, sexo);

        service.salvar(paciente);
        System.out.println("Paciente cadastrado com sucesso.");
    }

    private void listarPacientes(IPacienteService service) {
        ui.apagar();
        System.out.println("---------------------------");
        service.listarTodos().forEach(v -> System.out.println(v.toString()));
        System.out.println("---------------------------");
    }

    private void buscarPacienteNome(IPacienteService service) {
        String nome = this.ui.lerTexto("Digite o Nome: ");
        System.out.println(service.buscarPorNome(nome).toString());
    }

    private void deletarPaciente(IPacienteService service) {
        AtomicInteger idx = new AtomicInteger();
        System.out.println("-----------");
        service.listarTodos().forEach(v -> {
            System.out.println(idx + ": " + v.getNome());
            idx.getAndIncrement();
        });
        System.out.println("-----------");
        Long id = (long) ui.lerInt("Escolha o id: ");
        if (id > service.listarTodos().size()) {
            System.out.println("Esse ID não existe na lista!");
        } else {
            service.deletar(id);
            System.out.println("Deletando Paciente");
        }
    }

    private SexoEnum escolherSexo() {
        System.out.println("Sexo:");
        SexoEnum[] valores = SexoEnum.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + " - " + valores[i]);
        }
        System.out.print("Escolha: ");
        int opcao = this.ui.lerInt("");
        return valores[Math.max(0, Math.min(opcao - 1, valores.length - 1))];
    }
}
