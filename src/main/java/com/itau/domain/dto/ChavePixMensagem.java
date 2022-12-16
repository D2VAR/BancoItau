package com.itau.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itau.domain.enums.TipoChave;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChavePixMensagem {
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("codigo_banco")
    private String codBanco;
    @JsonProperty("numero_conta")
    private String numeroConta;
    @JsonProperty("agencia_conta")
    private String agenciaConta;
    @JsonProperty("cpf_cnpj")
    private String cpfCnpj;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("tipo_chave")
    private TipoChave tipoChave;
    @JsonProperty("valor_chave")
    private String valorChave;
}
