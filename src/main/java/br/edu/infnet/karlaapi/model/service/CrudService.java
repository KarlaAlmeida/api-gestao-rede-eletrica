package br.edu.infnet.karlaapi.model.service;

import java.util.List;

public interface CrudService<T, ID> {

    T salvar(T entity);
    T obterporId(ID id);
    List<T> obterLista();
    void excluir(ID id);
}
