/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unincor.sistema.bancario.admin.model.dao;

import com.unincor.sistema.bancario.admin.configurations.MySQL;
import com.unincor.sistema.bancario.admin.model.domain.Agencia;
import com.unincor.sistema.bancario.admin.model.domain.Gerente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LUAN
 */
public class GerenteDao {
    
    public void inserirGerente(Gerente gerente) {
        String sql = "INSERT INTO gerentes(nome,cpf,data_nascimento,"
                + "email,telefone,senha_hash,id_agencia) VALUES (?,?,?,?,?,?,?)";
        try(Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gerente.getNome());
            ps.setString(2, gerente.getCpf());
            ps.setDate(3, Date.valueOf(gerente.getDataNascimento()));
            ps.setString(4, gerente.getEmail());
            ps.setString(5, gerente.getTelefone());
            ps.setString(6, gerente.getSenhaHash());
            if(gerente.getAgencia() != null){
                ps.setLong(7, gerente.getAgencia().getIdAgencia());
            } else {
                ps.setObject(7, null);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Gerente> buscarTodosGerentes() {
        List<Gerente> gerentes = new ArrayList<>();
        String sql = "SELECT * FROM gerentes";
        try(Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                var gerente = construirGerenteSql(rs);
                gerentes.add(gerente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gerentes;
    }
    
    public Gerente buscarGerentePorId(Long idGerente) {
        String  sql = "SELECT * FROM gerentes WHERE id_gerente = ?";
        try(Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setLong(1, idGerente);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return construirGerenteSql(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Gerente construirGerenteSql (ResultSet rs) throws SQLException {
        Gerente gerente = new Gerente();
        gerente.setIdGerente(rs.getLong("id_gerente"));
        gerente.setNome(rs.getString("nome"));
        gerente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        gerente.setEmail(rs.getString("email"));
        gerente.setTelefone(rs.getString("telefone"));
        gerente.setSenhaHash(rs.getString("senha_hash"));
        long idAgencia = rs.getLong("agencia");
    if (idAgencia != 0) {
        Agencia agencia = new Agencia();
        agencia.setIdAgencia(idAgencia);
    } else {
        gerente.setAgencia(null);
    }
        return gerente;
    }
    
    public static void main(String[] args) {
        GerenteDao gerenteDao = new GerenteDao();
        
        //Exemplo de inserção
        /*
        Gerente novoGerente = new Gerente(null, "Luan Emidio", "14229798302", LocalDate.now(), "luan@unincor.edu.br", 
        "35911121314", "20928347921kkk", "Agencia Nu");
        gerenteDao.inserirGerente(novoGerente);
        */
        
        //Exemplo de buscar todos os gerentes
        var gerentes = gerenteDao.buscarTodosGerentes();
        gerentes.forEach(g -> System.out.println("id: "+g.getIdGerente()+" Nome: "+g.getNome()+" Agência: " + g.getAgencia()));
        
        //Exemplo de buscar gerente por ID
        var gerente = gerenteDao.buscarGerentePorId(1l);
        if(gerente!=null) {
            System.out.println("Id: "+gerente.getIdGerente()+" Nome: "+gerente.getNome()+" Agência: "+gerente.getAgencia());
        }else{
            System.out.println("Gerente não encontrado.");
        }
    }
}
