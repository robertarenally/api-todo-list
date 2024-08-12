package br.com.gestao.controllers;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.gestao.entities.Tarefa;
import br.com.gestao.services.TarefaService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(TarefaController.class)
public class TarefaControllerTest {

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        webTestClient = bindToController(tarefaController).build();
    }

    @Test
    public void criarTarefaTest() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Testar");

        when(tarefaService.criarTarefa(Mockito.any(Tarefa.class))).thenReturn(Mono.just(tarefa));

        webTestClient.post().uri("/tarefas")
                .bodyValue(tarefa)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.titulo").isEqualTo("Testar");
    }

    @Test
    public void listarTarefasTest() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Testar");

        when(tarefaService.listarTarefas()).thenReturn(Flux.just(tarefa));

        webTestClient.get().uri("/tarefas")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].titulo").isEqualTo("Testar");
    }
}
