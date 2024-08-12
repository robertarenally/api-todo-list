package br.com.gestao.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.gestao.entities.Tarefa;
import br.com.gestao.repositories.TarefaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    public TarefaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void criarTarefaTest() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Testar");
        tarefa.setDescricao("Descrição de teste");

        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(Mono.just(tarefa));

        Mono<Tarefa> resultado = tarefaService.criarTarefa(tarefa);

        StepVerifier.create(resultado)
                .expectNextMatches(t -> t.getTitulo().equals("Testar"))
                .verifyComplete();
    }

    @Test
    public void listarTarefasTest() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Testar");

        when(tarefaRepository.findAll()).thenReturn(Flux.just(tarefa));

        Flux<Tarefa> resultado = tarefaService.listarTarefas();

        StepVerifier.create(resultado)
                .expectNextMatches(t -> t.getTitulo().equals("Testar"))
                .verifyComplete();
    }
}