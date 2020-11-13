package AppVLOS;

import Hardware.*;
import VLOS.VLOS;
import VLUtils.OARoad;

import java.awt.*;


public class AppVLOS {

    public static void main(String args[]) {

        System.out.println("");
        System.out.println("UnB 2020.01 - ISO");
        System.out.println("");
        System.out.println("\t Aluno : Luan Freitas      -  17/0003191");
        System.out.println("\t Aluno : Vinicius Martins  -  17/0157962");

        OARoad mOARoadmap = new OARoad("res/roadmap.trilha");
        mOARoadmap.exportarImagemHV("res/roadmap.png", new Color(52, 73, 94), new Color(52, 73, 94));


        System.out.println("");


        Maquina_i368 mMaquina = new Maquina_i368();

        mMaquina.adicionarDispositivo(new Memoria("HX434C16FB3AK4", (1024 + 512) * 1024 * 1024));

        mMaquina.adicionarDispositivo(new SATA("ST2000DM008", 500 * 1024 * 1024));
        mMaquina.adicionarDispositivo(new SATA("WD20PURX", 200 * 1024 * 1024));

        mMaquina.adicionarDispositivo(new Impressora("Ink Tank 416 Z4B55A HP"));
        mMaquina.adicionarDispositivo(new Impressora("Deskjet Ink Advantage 2376 7WQ02A HP"));

        mMaquina.adicionarDispositivo(new Scanner("Avision FB5000"));

        mMaquina.adicionarDispositivo(new Modem("Mercusys MW301R"));
        mMaquina.adicionarDispositivo(new Modem("TP-Link AC1200"));

        VLOS mVLOS = new VLOS(mMaquina);

        mVLOS.ligar();


    }

}
