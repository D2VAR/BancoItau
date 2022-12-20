package br.com.banco.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequest {
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
}
