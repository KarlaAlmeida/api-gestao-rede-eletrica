package br.edu.infnet.karlaapi.model.service;

import java.util.List;

public interface CrudService<T, ID> {

    T incluir(T entity);
    T alterar(ID id, T entity);
    T obterPorId(ID id);
    List<T> obterLista();
    void excluir(ID id);
}
