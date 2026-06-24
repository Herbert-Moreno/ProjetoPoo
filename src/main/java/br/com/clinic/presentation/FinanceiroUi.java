package br.com.clinic.presentation;

import br.com.clinic.service.interfaces.IFinanceiroService;

public class FinanceiroUi implements UI<IFinanceiroService> {
    InterfaceUi ui;

    public FinanceiroUi(InterfaceUi ui) {
        this.ui = ui;
    }

    @Override
    public void show(IFinanceiroService service) {
        while (true) {
            System.out.println("\n--- Financeiro ---");
            System.out.println("1 - Listar pagamentos");
            System.out.println("2 - Buscar pagamento por ID");
            System.out.println("3 - Deletar pagamento");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            int opcao = ui.lerInt("");
            if (opcao == 0) return;
            ui.apagar();

            switch (opcao) {
                case 1 -> this.listarPagamentos(service);
                case 2 -> this.buscarPagamentoPorId(service);
                case 3 -> this.deletarPagamento(service);
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    public void listarPagamentos(IFinanceiroService service) {
        System.out.println("\nPagamentos cadastrados:");
        System.out.println("---------------------------");
        service.listarTodos().forEach(p -> System.out.println(p.toString()));
        System.out.println("---------------------------");
    }

    public void buscarPagamentoPorId(IFinanceiroService service) {
        System.out.println("-----------");
        service.listarTodos().forEach(v -> {
            System.out.println(v.getId_pagamento() + ": " + v.getData_pagamento() + ", " + v.getValor());
        });
        System.out.println("-----------");
        Long id = (long) ui.lerInt("Escolha o id: ");
        if (id > service.listarTodos().size()) {
            System.out.println("Esse ID nao existe na lista!");
        } else {
            System.out.println(service.buscarPorId(id).toString());
        }
    }

    public void deletarPagamento(IFinanceiroService service) {
        System.out.println("-----------");
        service.listarTodos().forEach(v -> {
            System.out.println(v.getId_pagamento() + ": " + v.getData_pagamento() + ", " + v.getValor());
        });
        System.out.println("-----------");
        Long id = (long) ui.lerInt("Escolha o id: ");
        if (id > service.listarTodos().size()) {
            System.out.println("Esse ID nao existe na lista!");
        } else {
            service.deletar(id);
            System.out.println("Deletando Paciente");
        }
    }
}
