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
public class Cartao {
    
    public String Documento;
    public String Codigo;
    public double Limite;
    public String Bandeira;
    
    public Cartao(String documento, String codigo, double limite, String bandeira)
    {
        Documento = documento;
        Codigo = codigo;
        Limite = limite;
        Bandeira = bandeira;
    }   
}
