/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author Gabriel
 */
public class Usuario {
    
    public String Documento;
    public String Senha;
    public String Nome;
    public String Email;
    
    public Usuario(String documento, String senha, String nome, String email)
    {
        Documento = documento;
        Senha = senha;
        Nome = nome;
        Email = email;
    }   
}
