/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unincor.sistema.bancario.admin.model.services;

import com.unincor.sistema.bancario.admin.exceptions.CadastroException;
import com.unincor.sistema.bancario.admin.model.dao.AgenciaDao;
import com.unincor.sistema.bancario.admin.model.domain.Agencia;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author LUAN
 */

public class AgenciaService {
    
    private final AgenciaDao agenciaDao = new AgenciaDao();
    private Object agenciaBusca;
    
    public void salvarAgencia(Agencia agencia) throws CadastroException {
        if (agencia.getCodigoAgencia() == null || agencia.getCodigoAgencia() .isBlank()) {
            throw new CadastroException ("A agência não possui" + "um código de agência!");
        }
        
        
        // Criar uma validação se o código da agência já está cadastrado
        agenciaDao.buscarClientePorCodigoAgencia(agencia.getCodigoAgencia());
        if (agenciaBusca != null) {
            throw new CadastroException("Código de Agência já está cadastrado!");
        }
        
        // Validar se a agência está com Cidade e UF preenchidos
        if (agencia.getCidade() == null || agencia.getCidade() .isBlank()) {
            throw new CadastroException ("A agência não possui" + "uma cidade informada!");
        }
        
         if (agencia.getUf() == null || agencia.getUf() .isBlank()) {
            throw new CadastroException ("A agência não possui" + "um estado!");
        }

        agenciaDao.inserirAgencia(agencia);

    }
    
    public List<Agencia> buscarAgencias() {
        return agenciaDao.listarTodasAgencias();
    }
    
    public static void main(String[] args)  {
        AgenciaService agenciaService = new AgenciaService();
        
        Agencia agencia = new Agencia(null, null, "Três Corações", "MG", "Rua José Bento", "304", "37414318");
        
        try {
            agenciaService.salvarAgencia(agencia);
        } catch (CadastroException ex) {
            Logger.getLogger(AgenciaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

