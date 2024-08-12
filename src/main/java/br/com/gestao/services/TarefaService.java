package br.com.gestao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestao.entities.Tarefa;
import br.com.gestao.repositories.TarefaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;
    
    @Autowired
    private LambdaService lambdaService;

    public Mono<Tarefa> criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa).doOnSuccess(savedTarefa -> {
            // Preparar o payload como JSON
            String payload = "{ \"tarefaId\": \"" + savedTarefa.getId() + "\", \"titulo\": \"" + savedTarefa.getTitulo() + "\" }";
            
            // Invocar a função Lambda AWS
            lambdaService.invocarLambda(payload);
        });
    }

    public Flux<Tarefa> listarTarefas() {    
        return tarefaRepository.findAll();
    }

    public Mono<Tarefa> atualizarTarefa(String id, Tarefa tarefa) {
        return tarefaRepository.findById(id)
                .flatMap(existingTarefa -> {
                    existingTarefa.setTitulo(tarefa.getTitulo());
                    existingTarefa.setDescricao(tarefa.getDescricao());
                    existingTarefa.setStatus(tarefa.isStatus());
                    return tarefaRepository.save(existingTarefa);
                });
    }

    public Mono<Void> deletarTarefa(String id) {
        return tarefaRepository.deleteById(id);
    }

    public Flux<Tarefa> buscarPorStatus(boolean concluida) {
        return tarefaRepository.findByStatus(concluida);
    }
}
