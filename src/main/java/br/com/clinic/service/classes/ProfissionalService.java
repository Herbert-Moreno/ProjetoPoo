package br.com.clinic.service.classes;

import br.com.clinic.model.classes.pessoa.Paciente;
import br.com.clinic.model.classes.pessoa.Profissional;
import br.com.clinic.service.interfaces.IProfissionalService;
import br.com.clinic.service.utils.ValidadorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Serviço que gerencia o ciclo de vida e os fluxos de consulta dos profissionais de estética da clínica.
 * @author Romulo Lopes
 */

/* Classe: ProfissionalService */
public class ProfissionalService implements IProfissionalService {
    private final List<Profissional> db = new ArrayList<>();
    private int idSequencial = 0;

    /**
     * Valida os dados obrigatórios e o registro do conselho antes de persistir o profissional.
     *
     * @param profissional O objeto Profissional a ser validado e salvo.
     * @return O profissional persistido com sucesso.
     * @throws IllegalArgumentException Se o nome, CPF ou o registro profissional forem inválidos ou nulos.
     */
    @Override
    public void salvar(Profissional profissional) {
        ValidadorUtils.validarTextoObrigatorio(profissional.getNome(), "O nome do profissional e obrigatório.");
        //ValidadorUtils.validarCpf(profissional.getCpf());
        ValidadorUtils.validarTextoObrigatorio(profissional.getRegistroProfissional(), "O registro do conselho profissional e obrigatório.");
        //ValidadorUtils.validarTextoObrigatorio(profissional.getRegistroEspecialidade(), "O registro do conselho profissional e obrigatório.");

        // teste de id
        if (profissional.getId() == 0){
            profissional.setId(idSequencial++);
            db.add(profissional);
        }else {
            db.removeIf(
                    a -> Objects.equals(a.getId(), profissional.getId()));
            db.add(profissional);
        }
        //this.db.add(profissional);
    }

    /**
     * Remove um profissional do sistema após validar sua existência prévia.
     *
     * @param id O identificador único do profissional.
     */
    @Override
    public void deletar(Long id) {
        Optional<Profissional> prf = this.buscarPorId(id);
        prf.ifPresent(value -> this.db.remove(value));
    }

    /**
     * Busca um profissional pelo seu ID.
     *
     * @param id O identificador único do profissional.
     * @return Um Optional contendo o profissional se encontrado.
     */
    @Override
    public Optional<Profissional> buscarPorId(Long id) {
        for (Profissional item : this.db) {
            if (item.getId() == id) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /**
     * Recupera uma lista de profissionais filtrada por uma determinada especialidade.
     *
     * @param especialidade O nome da especialidade médica/estética desejada.
     * @return Lista de profissionais que atuam na especialidade informada.
     * @throws IllegalArgumentException Se a string de especialidade for nula ou vazia.
     */
    @Override
    public List<Profissional> buscarPorEspecialidade(String especialidade) {
        ValidadorUtils.validarTextoObrigatorio(especialidade, "A especialidade informada para a busca nao pode ser vazia.");
        List<Profissional> ls = new ArrayList<>();
        for (Profissional pf : this.db) {
            if (pf.getEspecialidade().equals(especialidade)) {
                ls.add(pf);
            }
        }
        return ls;
    }

    /**
     * Lista todos os profissionais cadastrados na clínica.
     *
     * @return Lista contendo todos os profissionais da base de dados.
     */
    @Override
    public List<Profissional> listarTodos() {
        return this.db;
    }
}