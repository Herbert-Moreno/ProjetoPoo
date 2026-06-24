package br.com.clinic.service.interfaces;

import br.com.clinic.model.classes.pessoa.Profissional;

import java.util.List;

/**
 *
 * @author Romulo Lopes
 * */
public interface IProfissionalService extends CrudService<Profissional, Long>{

    List<Profissional> buscarPorEspecialidade(String especialidade);
}
