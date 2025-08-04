package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;

import java.util.List;

public interface CrudService<T, ID> {

    T obter();
    T salvar(T entity);
    List<T> obterLista();
    void excluir(ID id);
}
