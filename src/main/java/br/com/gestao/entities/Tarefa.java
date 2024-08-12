package br.com.gestao.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "tarefa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tarefa {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private boolean status;
}
