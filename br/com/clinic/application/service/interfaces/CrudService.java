package br.com.clinic.application.service.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interface genérica base que define o contrato padrão para as operações de leitura e escrita (CRUD) do sistema.
 * @author Raul Pablo
 * @param <T> O tipo da entidade que será manipulada.
 * @param <ID> O tipo do identificador único da entidade.
 */

/* Interface base: CrudService */
public interface CrudService<T, ID> {
    /**
     * Persiste ou atualiza uma entidade no sistema.
     * @param entity A entidade a ser salva.
     * @return A entidade persistida com seu estado atualizado.
     */
    T salvar(T entity);

    /**
     * Busca uma entidade pelo seu identificador único.
     * @param id O identificador único da entidade.
     * @return Un Optional contendo a entidade se encontrada, ou vazio caso contrário.
     */
    Optional<T> buscarPorId(ID id); // Utils, para lidar com nulos

    /**
     * Retorna uma lista com todos os registros da entidade.
     * @return Lista contendo todas as entidades registradas.
     */
    List<T> listarTodos();

    /**
     * Remove o registro correspondente ao identificador informado.
     * @param id O identificador único da entidade a ser removida.
     */
    void deletar(ID id);
}
