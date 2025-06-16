/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unincor.sistema.bancario.admin.model.services;

import com.unincor.sistema.bancario.admin.exceptions.CadastroException;
import com.unincor.sistema.bancario.admin.model.dao.FuncionarioDao;
import com.unincor.sistema.bancario.admin.model.domain.Funcionario;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LUAN
 */
public class FuncionarioService {
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private Object funcionarioBusca;
    
    public void salvarFuncionario(Funcionario funcionario) throws CadastroException {
        // Criar uma validação se o funcionário já está cadastrado
        funcionarioDao.buscarFuncionarioPorId(funcionario.getIdFuncionario());
        if (funcionarioBusca != null) {
            throw new CadastroException("O Funcionário já está cadastrado!");
        }
        
        // Validar se o gerente está com os campos preenchidos
        if (funcionario.getIdFuncionario()== null) {
            throw new CadastroException ("O Funcionário não possui um ID!");
        }
        
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new CadastroException ("O funcionário não possui um nome informado!");
        }
        
         if (funcionario.getCpf() == null || funcionario.getCpf().isBlank()) {
            throw new CadastroException ("O funcionário não possui um CPF informado!");
        }
         
        if(funcionario.getDataNascimento()== null){
            throw new CadastroException ("O funcionário não possui uma data de nascimento informada!");
        }
        
        if(funcionario.getEmail()== null || funcionario.getEmail().isBlank()){
            throw new CadastroException ("O funcionário não possui um Email informado!");
        }
        
        if(funcionario.getTelefone()== null || funcionario.getTelefone().isBlank()){
            throw new CadastroException ("O funcionário não possui um telefone informado!");
        }
        
        if(funcionario.getSenhaHash()== null || funcionario.getSenhaHash().isBlank()){
            throw new CadastroException ("O funcionário não possui uma senha hash informada!");
        }   
        
        if (funcionario.getTurno()== null || funcionario.getTurno().isBlank()) {
            throw new CadastroException ("O funcionário não possui um turno!");
        }

        funcionarioDao.inserirFuncionario(funcionario);

    }
    
    public List<Funcionario> buscarFuncionarios() {
        return funcionarioDao.buscarTodosFuncionarios();
    }
    
    public static void main(String[] args)  {
        FuncionarioService funcionarioService = new FuncionarioService();
        
        Funcionario funcionario = new Funcionario(null, "Luan Emilio", "23712388902", LocalDate.now(), "luan@gmail.com.br", "35922232425", "jdkalfjdksjjks", "Noturno");
        
        try {
            funcionarioService.salvarFuncionario(funcionario);
        } catch (CadastroException ex) {
            Logger.getLogger(GerenteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
