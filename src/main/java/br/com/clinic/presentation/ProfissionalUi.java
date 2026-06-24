package br.com.clinic.presentation;

import br.com.clinic.model.classes.pessoa.Endereco;
import br.com.clinic.model.classes.pessoa.Profissional;
import br.com.clinic.model.enums.SexoEnum;
import br.com.clinic.service.interfaces.IProfissionalService;

import java.util.concurrent.atomic.AtomicInteger;

public class ProfissionalUi implements UI<IProfissionalService> {
    InterfaceUi ui;

    public ProfissionalUi(InterfaceUi ui) {
        this.ui = ui;
    }

    public void show(IProfissionalService service) {
        while (true) {
            System.out.println("\n--- Profissional ---");
            System.out.println("1 - Cadastrar profissional");
            System.out.println("2 - Listar profissionais");
            System.out.println("3 - Buscar por especialidade");
            System.out.println("4 - Deletar profissional");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            int opcao = this.ui.lerInt("");
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> this.cadastrarProfissional(service);
                case 2 -> this.listarProfissionais(service);
                case 3 -> this.buscarProfissionalEspecialidade(service);
                case 4 -> this.deletarProfissional(service);
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    private void cadastrarProfissional(IProfissionalService service) {
        this.ui.apagar();
        System.out.println("\nCadastro de profissional");
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
        String cargo = this.ui.lerTexto("Cargo: ");
        String especialidade = this.ui.lerTexto("Especialidade: ");
        String registro = this.ui.lerTexto("Registro profissional: ");
        String dataContratacao = this.ui.lerTexto("Data de contratacao (dd/MM/yyyy): ");
        double salario = this.ui.lerDouble("Salario: ");

        Endereco endereco = new Endereco(rua, bairro, cidade, cep);
        //teste temporario
        Profissional profissional = new Profissional(cargo, especialidade, registro, dataContratacao, salario, nome, cpf, dataNascimento, telefone, email, endereco, sexo);

        service.salvar(profissional);
        System.out.println("Paciente cadastrado com sucesso.");
    }

    private void listarProfissionais(IProfissionalService service) {
        ui.apagar();
        System.out.println("---------------------------");
        service.listarTodos().forEach(v -> System.out.println(v.toString()));
        System.out.println("---------------------------");
    }

    private void buscarProfissionalEspecialidade(IProfissionalService service) {
        String especialidade = this.ui.lerTexto("Digite a Especialidade: ");
        System.out.println("----- "+especialidade+" -----");
        service.buscarPorEspecialidade(especialidade).forEach(v -> {
            System.out.println(v.getId() + ":" + v.toString());
            System.out.println();
        });
        System.out.println("--------------------");
    }

    private void deletarProfissional(IProfissionalService service) {
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
            System.out.println("Deletando Profissional");
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
