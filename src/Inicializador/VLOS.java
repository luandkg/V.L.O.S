package Inicializador;

import Hardware.Maquina;

public class VLOS {

    public static void main(String args[]){

        System.out.println("");
        System.out.println("UnB 2020.01 - ISO");
        System.out.println("");
        System.out.println("\t Aluno : Luan Freitas - 17/0003191");
        System.out.println("\t Aluno : Vinicius Martins");

        System.out.println("");


        Maquina mMaquina = new Maquina();

        System.out.println("\t Processador : " + mMaquina.getProcessador().getProcessador());
        System.out.println("\t Memoria : " + mMaquina.getMemoria().getModelo() + " -> " + mMaquina.getMemoria().getTamanho());
        System.out.println("\t HD : " + mMaquina.getHD().getModelo() + " -> " + mMaquina.getHD().getTamanho());


    }

}
