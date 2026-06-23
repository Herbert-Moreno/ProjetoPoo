package br.com.clinic.domain.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    T salvar(T entity);
    Optional<T> buscarPorId(ID id); // Utils, para lidar com nulos
    List<T> listarTodos();
    void deletar(ID id);
}
