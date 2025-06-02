/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unincor.sistema.bancario.admin.model.dao;

import com.unincor.sistema.bancario.admin.configurations.MySQL;
import com.unincor.sistema.bancario.admin.model.domain.Agencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dioge
 */
public class AgenciaDao {
    
    public void inserirAgencia(Agencia agencia) {
        String sql = "INSERT INTO AGENCIA(codigo_agencia, cidade, uf, "
                + "logradouro, numero, cep) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection con = MySQL.connect(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, agencia.getCodigoAgencia());
            ps.setString(2, agencia.getCidade());
            ps.setString(3, agencia.getUf());
            ps.setString(4, agencia.getLogradouro());
            ps.setString(5, agencia.getNumero());
            ps.setString(6, agencia.getCep());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Agencia> listarTodasAgencias() {
        String sql = "SELECT * FROM Agencias";
        List<Agencia> agencias = new ArrayList<>();
        try(Connection con = MySQL.connect(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                agencias.add(construirAgenciaSql(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agencias;
    }
    
    public Agencia buscarAgenciaPorId(Long id) {
        String sql = "SELECT * FROM Agencias where id_agencia = ?";
        try(Connection con = MySQL.connect(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return construirAgenciaSql(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Agencia construirAgenciaSql(ResultSet rs) throws SQLException {
        Agencia agencia = new Agencia();
        agencia.setIdAgencia(rs.getLong("id_agencia"));
        agencia.setCodigoAgencia(rs.getString("codigo_agencia"));
        agencia.setCidade(rs.getString("cidade"));
        agencia.setUf(rs.getString("uf"));
        agencia.setLogradouro(rs.getString("logradouro"));
        agencia.setNumero("numero");
        agencia.setCep(rs.getString("cep"));
        return agencia;
    }
    
}
