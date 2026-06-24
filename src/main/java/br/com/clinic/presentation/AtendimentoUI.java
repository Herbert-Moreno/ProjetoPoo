package br.com.clinic.presentation;

import br.com.clinic.model.classes.atendimento.Atendimento;

import br.com.clinic.model.classes.procedimento.Procedimento;
import br.com.clinic.model.enums.FormaPagamento;

import br.com.clinic.model.enums.TipoProcedimento;
import br.com.clinic.service.interfaces.IAtendimentoService;

import java.util.ArrayList;
import java.util.List;

public class AtendimentoUI implements UI<IAtendimentoService>{
    InterfaceUi ui;

    public AtendimentoUI(InterfaceUi ui){
        this.ui = ui;
    }

    @Override
    public void show(IAtendimentoService service) {
        while (true) {
            System.out.println("\n--- Atendimento ---");
            System.out.println("1 - Cadastrar Atendimento");
            System.out.println("2 - Finalizar Atendimento");
            System.out.println("3 - Cancelar Atendimento");
            System.out.println("4 - Listar Atendimentos");
            System.out.println("5 - Deletar Atendimento");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            int opcao = this.ui.lerInt("");
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> this.criarAtendimento(service);
                case 2 -> this.finalizarAtendimento(service);
                case 3 -> this.cancelarAtendimento(service);
                case 4 -> this.listarAtendimento(service);
                case 5 -> this.deletarAtendimento(service);
                default -> System.out.println("Opcao inválida.");
            }
        }
    }

    private void criarAtendimento(IAtendimentoService service){
        this.ui.apagar();

        while (true){
            System.out.println("\n--- Cadrstrar Atendimento ---");

            Long idPaciente = this.ui.lerLong("ID Paciente: ");
            Long idProfissional = this.ui.lerLong("ID Profissional: ");

            List<Procedimento> procedimentos = this.adicionarProcedimentos();

            if (procedimentos.isEmpty()) {
                System.out.println("Erro: Não é possível agendar um atendimento sem procedimentos.");
                return;
            }

            try{
                Atendimento atendimento = service.criarAtendimento(
                        idPaciente, idProfissional, procedimentos);
                System.out.println("Atandimento agendado com sucesso.\n" +
                        "ID gerado: " + atendimento.getId());
                break;
            } catch (IllegalArgumentException | IllegalStateException e){
                System.out.println("Erro ao agendar: " + e.getMessage());
            }
        }

    }

    private List<Procedimento> adicionarProcedimentos() {

        int idSequencial = 0;
        TipoProcedimento tipoProcedimento;
        List<Procedimento> listaProcedimentos = new ArrayList<>();

        System.out.println("\n--- Inserir Procedimentos ---");

        while (true) {
            String resposta = this.ui.lerTexto("Deseja adicionar um procedimento? (S/N): ");

            if (resposta.equalsIgnoreCase("N")) {
                break;
            } else if (!resposta.equalsIgnoreCase("S")) {
                System.out.println("Opção inválida. Digite 'S' para Sim ou 'N' para Não.");
                continue;
            }

            //System.out.print("ID do Procedimento: ");
            idSequencial++;

            System.out.println("\nTipos de Procedimento disponíveis:");

            for (TipoProcedimento tipo: TipoProcedimento.values()) {
                System.out.println("- " + tipo.name());
            }
            String tipoStr = this.ui.lerTexto("Digite o tipo do procedimento: ").toUpperCase();

            try {
                tipoProcedimento = TipoProcedimento.valueOf(tipoStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Tipo de procedimento não reconhecido. Tente novamente.");
                continue;
            }

            double valor = this.ui.lerDouble("Valor (R$): ");

            System.out.print("Duração (minutos): ");
            int duracao = this.ui.lerInt("");

            String descricao = this.ui.lerTexto("Descrição: ");

            Procedimento procedimento = new Procedimento(idSequencial, tipoProcedimento, valor, duracao, descricao);

            listaProcedimentos.add(procedimento);
            System.out.println("Procedimento '" + tipoProcedimento.name() + "' adicionado temporariamente à lista do agendamento.");
        }

        return listaProcedimentos;
    }

    private void finalizarAtendimento(IAtendimentoService service){
        this.ui.apagar();
        FormaPagamento formaPagamento;
        System.out.println("\n--- Finalizar Atendimento ---");

        while (true){
            long atendimentoId = this.ui.lerLong("ID do Atendimento: ");

            System.out.println("\nTipos de Procedimento disponíveis:");

            for (FormaPagamento tipo: FormaPagamento.values()) {
                System.out.println("- " + tipo.name());
            }

            String formaPagamentoStr = this.ui.lerTexto("Digite forma de Pagamento: ").toUpperCase();

            try {
                formaPagamento = FormaPagamento.valueOf(formaPagamentoStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Forma de Pagamento nao reconhecida no sistema. Tente novamente.");
                continue;
            }
            try {
                service.finalizarAtendimento(atendimentoId, formaPagamento);
                System.out.println("Atendimento finalizado com sucesso!");
                break;
            } catch (IllegalArgumentException | IllegalStateException e){
                System.out.println("Erro ao finalizar: " + e.getMessage());
            }
        }

    }

    private void cancelarAtendimento(IAtendimentoService service){
        this.ui.apagar();
        System.out.println("\n--- Cancelar Atendimento ---");

        long atendimentoId = this.ui.lerLong("ID do Atendimento: ");

        try {
            service.cancelarAtendimento(atendimentoId);
            System.out.println("Atendimento cancelado com sucesso.");
        } catch (IllegalArgumentException e){
            System.out.println("Erro ao cancelar: " + e.getMessage());
        }
    }

    private void listarAtendimento(IAtendimentoService service){
        this.ui.apagar();
        System.out.println("\n--- Listar todos Atendimentos ---");

        List<Atendimento> atendimentos = service.listarTodos();

        if (atendimentos == null || atendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento registrado no sistema.");
        } else {
            for (Atendimento a : atendimentos) {
                System.out.println(a.toString());
            }
        }
    }

    private void deletarAtendimento(IAtendimentoService service) {
        this.ui.apagar();
        System.out.println("\n--- Deletar Atendimento ---");

        long atendimentoId = this.ui.lerLong("Digite o ID do atendimento que deseja excluir: ");

        try {
            service.deletar(atendimentoId);
            System.out.println("Atendimento excluído com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
        }
    }
}















