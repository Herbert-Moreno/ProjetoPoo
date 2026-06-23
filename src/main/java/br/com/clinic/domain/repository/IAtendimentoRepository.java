package br.com.clinic.domain.repository;

import br.com.clinic.domain.model.atendimento.Atendimento;

import java.time.LocalDate;
import java.util.List;

public interface IAtendimentoRepository extends BaseRepository<Atendimento, Long> {
    List<Atendimento> buscarData(LocalDate data);
}
