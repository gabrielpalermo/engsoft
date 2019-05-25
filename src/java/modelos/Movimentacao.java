/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Date;

/**
 *
 * @author Gabriel
 */
public class Movimentacao {
    public Date Data;
    public double Valor;
    public String Categoria;
    public String Conta;
    public int IO;
    public String Documento;
    public int Id;

    public Movimentacao(Date data, double valor, String categoria, String conta, int io, String documento) {
        Data = data;
        Valor = valor;
        Categoria = categoria;
        Conta = conta;
        IO = io;
        Documento = documento;
        Id = 0;
    }
}
