package br.com.clinic.model.classes.atendimento;

import br.com.clinic.model.classes.financeiro.Pagamento;
import br.com.clinic.model.classes.pessoa.Paciente;
import br.com.clinic.model.classes.pessoa.Profissional;
import br.com.clinic.model.enums.StatusAtendimento;
import br.com.clinic.model.classes.procedimento.Procedimento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Raul Pablo
 * */
public class Atendimento {

    private long id;
    private Paciente paciente;
    private Profissional profissional;
    private LocalDate data;
    private StatusAtendimento status;
    private Pagamento pagamento;

    private final List<Procedimento> procedimentos = new ArrayList<>();

    public Atendimento(Paciente paciente, Profissional profissional, List<Procedimento> procedimentosIniciais) {

        this.paciente = Objects.requireNonNull(paciente, "O cliente é obrigatório.");
        this.profissional = Objects.requireNonNull(profissional, "O profissional é obrigatório.");

        if (procedimentosIniciais == null || procedimentosIniciais.isEmpty()) {
            throw new IllegalArgumentException("O atendimento deve conter pelo menos um procedimento.");
        }
        this.procedimentos.addAll(procedimentosIniciais);
        this.status = StatusAtendimento.AGENDADO;
    }

    public void adicionarProcedimento(Procedimento procedimento){
        if (procedimento == null) {
            throw new IllegalArgumentException("O procedimento não pode ser nulo.");
        }
        this.procedimentos.add(procedimento);
    }

    public void removerProcedimento(Procedimento procedimento){
        if (procedimento == null) {
            throw new IllegalArgumentException("O procedimento não pode ser nulo.");
        }
        this.procedimentos.remove(procedimento);
    }

    public double calcularTotalAtendimento(){
        return procedimentos.stream()
                .mapToDouble(Procedimento::getValor)
                .sum();
    }

    public void cancelar(){

        if (this.status == StatusAtendimento.REALIZADO) {
            throw new IllegalStateException("Um atendimento já realizado não pode ser cancelado.");
        }

        this.status = StatusAtendimento.CANCELADO;
    }

    public void finalizar(Pagamento pagamento){

        if (this.status != StatusAtendimento.AGENDADO) {
            throw new IllegalStateException("Apenas atendimentos agendados podem ser finalizados. Status atual: " + this.status);
        }
        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento não efetuado. É obrigatório informar a receita.");
        }

        this.status = StatusAtendimento.REALIZADO;
        this.pagamento = pagamento;
    }

    public List<Procedimento> getProcedimentos(){
        return List.copyOf(procedimentos);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id;}

    public Paciente getPaciente() { return paciente; }
    public Profissional getProfissional() { return profissional;}
    public LocalDate getDataHora() { return data;}
    public Pagamento getPagamento(){return pagamento;}

    public StatusAtendimento getStatus() {return status;}

    @Override
    public String toString() {
        return "\nAtendimento " +
                "\nID.: " + id +
                "\nPaciente.: " + (paciente != null ? paciente.getNome() : "Não informado") +
                "\nProfissional.: " + (profissional != null ? profissional.getNome() : "Não informado") +
                "\nProcedimentos.: " + procedimentos +
                "\nStatus.: " + status +
                "\nPagamento.: " + (pagamento != null ? "R$ " + pagamento.getValor() : "Pendente");
    }
}
