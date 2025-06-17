/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unincor.sistema.bancario.admin.model.services;

import com.unincor.sistema.bancario.admin.exceptions.CadastroException;
import com.unincor.sistema.bancario.admin.model.dao.AgenciaDao;
import com.unincor.sistema.bancario.admin.model.dao.GerenteDao;
import com.unincor.sistema.bancario.admin.model.domain.Agencia;
import com.unincor.sistema.bancario.admin.model.domain.Gerente;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LUAN
 */
public class GerenteService {
    
    private final GerenteDao gerenteDao = new GerenteDao();
    private Object gerenteBusca;
    
    public void salvarGerente(Gerente gerente) throws CadastroException {
        // Criar uma validação se o gerente já está cadastrado
        gerenteBusca = gerenteDao.buscarGerentePorId(gerente.getIdGerente());
        if (gerenteBusca != null) {
            throw new CadastroException("O Gerente já está cadastrado!");
        }
        
        if (gerente.getNome() == null || gerente.getNome().isBlank()) {
            throw new CadastroException ("O gerente não possui um nome informado!");
        }
        
         if (gerente.getCpf() == null || gerente.getCpf().isBlank()) {
            throw new CadastroException ("O gerente não possui um CPF informado!");
        }
         
        if(gerente.getDataNascimento()== null){
            throw new CadastroException ("O gerente não possui uma data de nascimento informada!");
        }
        
        if(gerente.getEmail()== null || gerente.getEmail().isBlank()){
            throw new CadastroException ("O gerente não possui um Email informado!");
        }
        
        if(gerente.getTelefone()== null || gerente.getTelefone().isBlank()){
            throw new CadastroException ("O gerente não possui um telefone informado!");
        }
        
        if(gerente.getSenhaHash()== null || gerente.getSenhaHash().isBlank()){
            throw new CadastroException ("O gerente não possui uma senha hash informada!");
        }
        
        if (gerente.getAgencia().getCodigoAgencia()== null) {
            throw new CadastroException ("O gerente não possui uma Agência!");
        }

        gerenteDao.inserirGerente(gerente);

    }
    
    public List<Gerente> buscarGerentes() {
        return gerenteDao.buscarTodosGerentes();
    }
    
    public static void main(String[] args)  {
        GerenteService gerenteService = new GerenteService();
        Agencia ag = new AgenciaDao().buscarAgenciaPorId(1l);
        
        Gerente gerente = new Gerente(null, "Luan Emidio Cruz", "23794688902", LocalDate.now(), "luan@gmail.com", "35911232425", "jdkalfjdksksks",ag);
        
        try {
            gerenteService.salvarGerente(gerente);
        } catch (CadastroException ex) {
            Logger.getLogger(GerenteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

