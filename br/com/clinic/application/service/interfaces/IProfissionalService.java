package br.com.clinic.application.service.interfaces;

import br.com.clinic.domain.model.pessoa.Profissional;

import java.util.List;

/**
 *
 * @author Romulo Lopes
 * */
public interface IProfissionalService extends CrudService<Profissional, Long>{

    List<Profissional> buscarPorEspecialidade(String especialidade);
}
