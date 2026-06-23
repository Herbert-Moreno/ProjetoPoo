package br.com.clinic.domain.repository;

import br.com.clinic.domain.model.pessoa.Profissional;

import java.util.List;

public interface IProfissionalRepository extends BaseRepository<Profissional, Long>{
    List<Profissional> buscarPorEspecialidade(String especialidade);
}
