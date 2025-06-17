/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unincor.sistema.bancario.admin.model.services;

import com.unincor.sistema.bancario.admin.exceptions.CadastroException;
import com.unincor.sistema.bancario.admin.model.dao.ClienteDao;
import com.unincor.sistema.bancario.admin.model.domain.Cliente;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LUAN
 */
public class ClienteService {
    
    private final ClienteDao clienteDao = new ClienteDao();
    private Object clienteBusca;
    
    public void salvarCliente(Cliente cliente) throws CadastroException {
        //Criar uma validação se o cliente já está cadastrado
        clienteBusca = clienteDao.buscarClientePorId(cliente.getIdCliente());
        if(clienteBusca != null){
            throw new CadastroException("O cliente já está cadastrado!");
        }
        
        if(cliente.getNome() == null || cliente.getNome().isBlank()){
            throw new CadastroException("O cliente não possui um nome informado!");
        }
        
        if(cliente.getCpf() == null || cliente.getCpf().isBlank()){
            throw new CadastroException("O cliente não possui um CPF informado!");
        }
        
        if(cliente.getDataNascimento() == null){
            throw new CadastroException("O cliente não possui uma data de nascimento informada!");
        }
        
        if(cliente.getEmail() == null || cliente.getEmail().isBlank()){
            throw new CadastroException("O cliente não possui um email informado!");
        }
        
        if(cliente.getTelefone() == null || cliente.getTelefone().isBlank()){
            throw new CadastroException("O cliente não possui um telefone informado!");
        }
        
        if(cliente.getSenhaHash() == null || cliente.getSenhaHash().isBlank()){
            throw new CadastroException("O cliente não possui uma senhahash informada!");
        }
        
        clienteDao.inserirCliente(cliente);
    }
    
    public List<Cliente> buscarClientes() {
        return clienteDao.buscarTodosClientes();
    }
    
    public static void main(String[] args) {
        ClienteService clienteService = new ClienteService();
        
        Cliente cliente = new Cliente(null, "Luan Emilio", "14662469499", LocalDate.now(), "luan.emidio@gmail.com", "35988519746", "juji2345");
        
        try{
            clienteService.salvarCliente(cliente);
        } catch (CadastroException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
