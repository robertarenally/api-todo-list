package br.com.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import br.com.gestao.entities.Tarefa;
import br.com.gestao.services.TarefaService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Operation(summary = "Cria uma nova tarefa", 
            description = "Cria uma nova tarefa com os dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso")
    @PostMapping
    public Mono<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.criarTarefa(tarefa);
    }

    @Operation(summary = "Lista todas as tarefas", 
            description = "Retorna uma lista de todas as tarefas.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso", 
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping
    public Flux<Tarefa> listarTarefas() {
        return tarefaService.listarTarefas();
    }

    @Operation(summary = "Atualiza uma tarefa existente", 
            description = "Atualiza uma tarefa existente com os dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso")
    @PutMapping("/{id}")
    public Mono<Tarefa> atualizarTarefa(@PathVariable String id, @RequestBody Tarefa tarefa) {
        return tarefaService.atualizarTarefa(id, tarefa);
    }

    @Operation(summary = "Deleta uma tarefa", 
            description = "Deleta uma tarefa existente pelo ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso")
    @DeleteMapping("/{id}")
    public Mono<Void> deletarTarefa(@PathVariable String id) {
        return tarefaService.deletarTarefa(id);
    }

    @Operation(summary = "Busca tarefas por status", 
            description = "Retorna tarefas filtradas pelo status fornecido.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas filtradas retornada com sucesso",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping("/status")
    public Flux<Tarefa> buscarPorStatus(@RequestParam boolean concluida) {
        return tarefaService.buscarPorStatus(concluida);
    }
}