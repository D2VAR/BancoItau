package com.itau.domain.service;

import com.itau.adapter.out.api.bacen.ApiBacen;
import com.itau.adapter.out.db.repository.ChavePixRepository;
import com.itau.domain.dto.ChavePixMensagem;
import com.itau.domain.dto.ChavePixRequest;
import com.itau.domain.dto.ChavePixResponse;
import com.itau.domain.model.ChavePix;
import com.itau.port.in.CadastroChavePixInputPort;
import com.itau.port.out.BacenProducerOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChavePixService implements CadastroChavePixInputPort{
    private final ChavePixRepository chavePixRepository;
    private final ApiBacen apiBacen;
    private final ContaService contaService;
    private final BacenProducerOutputPort bacenProducerOutputPort;

    @Override
    public void cadastrarChaveBacen(ChavePixRequest chavePixRequest){
        var entity = buildChavePixEntity(chavePixRequest);
        validarExistenciaChavePixBacen(chavePixRequest);
        bacenProducerOutputPort.enviarMensagemCadastroChave(entity);
    }

    private ChavePix buildChavePixEntity(ChavePixRequest chavePixRequest){
        var conta = contaService.getContaById(chavePixRequest.getIdConta());
        return new ChavePix(chavePixRequest.getValorChave(), chavePixRequest.getTipoChave(), conta);
    }
    private ChavePix buildChavePixEntity(ChavePixMensagem chavePixMensagem){
        var conta = contaService.getContaByAgenciaAndNumeroConta(chavePixMensagem.getAgenciaConta(), chavePixMensagem.getNumeroConta());
        return new ChavePix(chavePixMensagem.getValorChave(), chavePixMensagem.getTipoChave(), conta);
    }

    private void validarExistenciaChavePixBacen(ChavePixRequest chavePixRequest){
        var responseApiBacen = apiBacen.chavePixExists(chavePixRequest.getValorChave());
        log.info("retorno bacen", responseApiBacen);
        if (responseApiBacen.getChaveExists())
            throw new RuntimeException("Chave Pix ja existente!");

    }
    @Override
    public void cadastrarChaveInterna(ChavePixMensagem chavePix){
        validarExistenciaChavePixInterna(chavePix.getValorChave());
        var entity = buildChavePixEntity(chavePix);
        save(entity);
        //TODO: enviar notificacao ao cliente
    }
    private void validarExistenciaChavePixInterna(String valor){
        try{
            findByValor(valor);
            throw new RuntimeException("Chave ja existente!");
        } catch (RuntimeException e){
        }
    }

    public ChavePixResponse save(ChavePix chavePix){
        chavePixRepository.save(chavePix);
        return new ChavePixResponse(chavePix.getId(), chavePix.getValor(), chavePix.getConta());
    }

    public void delete(UUID chavePixId){
        ChavePix chavePix = findById(chavePixId);
        chavePixRepository.delete(chavePix);
    }

    public ChavePix findById(UUID chavePixId){
        return chavePixRepository.findById(chavePixId)
                .orElseThrow(() -> new RuntimeException("Chave Pix não encontrada!"));
    }

    private ChavePix findByValor(String valor){
        return chavePixRepository.findByValor(valor)
                .orElseThrow(() -> new RuntimeException("Chave Pix não encontrada!"));
    }

    public List<ChavePix> findAll(){
        return chavePixRepository.findAll();
    }

}
