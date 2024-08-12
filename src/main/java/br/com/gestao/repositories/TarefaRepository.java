package br.com.gestao.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import br.com.gestao.entities.Tarefa;
import reactor.core.publisher.Flux;

public interface TarefaRepository extends ReactiveMongoRepository<Tarefa, String> {
    
	Flux<Tarefa> findByStatus(boolean concluida);
}