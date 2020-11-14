package Testes;

import VLOS.Despachante.ItemDespachante;

import java.util.ArrayList;

public class Teste_Alpha {

    public void testeProcessosEmTempos(ArrayList<ItemDespachante> mDespachantes) {

        mDespachantes.clear();
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 3, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 5, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 3, 2, 0, 1);
        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 4, 0, 5);


    }

    public void testeProcessosSimultaneos(ArrayList<ItemDespachante> mDespachantes) {

        mDespachantes.clear();
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 3, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 5, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 5, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 2, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 3, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 7, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 3, 2, 0, 1);
        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 4, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 3, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 1, 0, 5);


    }


    public void testeProcessosSimultaneosMultiplasFilas(ArrayList<ItemDespachante> mDespachantes) {

        mDespachantes.clear();
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 3, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 5, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 5, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 2, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 3, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 7, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 3, 2, 0, 1);
        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 4, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 3, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 1, 0, 5);


        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 3, 1, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 1, 3, 5);

        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 4, 2, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 5, 1, 5);

        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 5, 3, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 3, 1, 3, 5);

        adicionarProcessoUsuarioEmFila(mDespachantes, 4, 1, 6, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 5, 1, 5, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 5, 3, 1, 5);

    }

    public void testeProcessosPrioritarios(ArrayList<ItemDespachante> mDespachantes) {

        mDespachantes.clear();
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 3, 5, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 5, 3, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 2, 1, 1);
        adicionarProcessoUsuarioEmFila(mDespachantes, 3, 4, 0, 5);


    }

    public void adicionarProcessoUsuarioEmFila(ArrayList<ItemDespachante> mDespachantes, int eTempo, int eTamanho, int ePrioridade, int eBlocos) {

        ItemDespachante eItem = new ItemDespachante();
        eItem.setInicializacao(eTempo);
        eItem.setTempoProcessador(eTamanho);
        eItem.setPrioridade(ePrioridade);
        eItem.setBlocos(eBlocos);

        mDespachantes.add(eItem);

    }
}
