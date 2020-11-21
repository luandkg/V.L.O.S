package Testes;

import VLOS.Despachante.DespachadorDeOperacoes;
import VLOS.Despachante.DespachadorDeProcessos;
import VLOS.Despachante.DespachanteProcesso;

import java.util.ArrayList;

public class Teste_Alpha {

    // CLASSE RESPONSAVEL POR CRIAR TESTES ESPECIFICOS
    //
    // testeProcessosEmTempos                           ->  Cria Processos em Tempos diferentes
    // testeProcessosSimultaneos                        ->  Cria Processos Simultaneos em Tempos diferentes
    // testeProcessosSimultaneosNoMesmoTempo            ->  Cria Processos Simultaneos no mersmo Tempo
    // testeProcessosSimultaneosEmTemposDiferentes      ->  Cria Processos Simultaneos em Tempos diferentes
    // testeProcessosPrioritariosNoMesmoTempo           ->  Cria Processos Prioritarios no mesmo Tempo
    // testeProcessosSimultaneosMultiplasFilas          ->  Cria Processos Simultaneos em Tempos diferentes e em Multiplas Filas


    public void testeProcessosEmTempos(DespachadorDeProcessos mDespachadorDeProcessos, DespachadorDeOperacoes mDespachadorDeOperacoes) {

        mDespachadorDeProcessos.getProcessos().clear();
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 1, 3, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 2, 5, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 3, 2, 0, 1);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 4, 4, 0, 5);

        mDespachadorDeOperacoes.setBlocos(30);
        mDespachadorDeOperacoes.setSegmentosOcupados(10);

        mDespachadorDeOperacoes.getItens().clear();
        mDespachadorDeOperacoes.getOperacoes().clear();

        mDespachadorDeOperacoes.adicionarOperacao(1, 0, "Oi.txt", 2);
        mDespachadorDeOperacoes.adicionarOperacao(1, 0, "Nomes.txt", 2);
        mDespachadorDeOperacoes.adicionarOperacao(3, 0, "Alunos.txt", 3);
        mDespachadorDeOperacoes.adicionarOperacao(3, 1, "Nomes.txt", 0);

    }

    public void testeProcessosSimultaneosNoMesmoTempo(ArrayList<DespachanteProcesso> mDespachantes) {

        mDespachantes.clear();
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 3, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 5, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 5, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 2, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 3, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 7, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 2, 0, 1);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 4, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 3, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachantes, 2, 1, 0, 5);


    }

    public void testeProcessosSimultaneosEmTemposDiferentes(ArrayList<DespachanteProcesso> mDespachantes) {

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


    public void testeProcessosSimultaneosMultiplasFilasPrioritarias(DespachadorDeProcessos mDespachadorDeProcessos,DespachadorDeOperacoes mDespachadorDeOperacoes) {

        mDespachadorDeProcessos.getProcessos().clear();
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 1, 3, 1, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 1, 5, 1, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 2, 5, 1, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 2, 3, 1, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 2, 7, 2, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 4, 4, 2, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 4, 3, 0, 7);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 4, 5, 0, 5);


        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 2, 1, 3, 4);

        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 4, 5, 1, 8);

        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 2, 5, 3, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 3, 1, 3, 9);

        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 4, 1, 6, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 5, 1, 5, 10);

        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 7, 3, 8, 50);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos.getProcessos(), 9, 2, 4, 4);

        mDespachadorDeOperacoes.setBlocos(30);
        mDespachadorDeOperacoes.setSegmentosOcupados(10);

        mDespachadorDeOperacoes.getItens().clear();
        mDespachadorDeOperacoes.getOperacoes().clear();

        mDespachadorDeOperacoes.adicionarOperacao(1, 0, "Oi.txt", 2);
        mDespachadorDeOperacoes.adicionarOperacao(1, 0, "Nomes.txt", 2);
        mDespachadorDeOperacoes.adicionarOperacao(3, 0, "Alunos.txt", 3);
        mDespachadorDeOperacoes.adicionarOperacao(3, 1, "Nomes.txt", 0);

        mDespachadorDeOperacoes.adicionarOperacao(5, 0, "Entao.txt", 3);

        mDespachadorDeOperacoes.adicionarOperacao(6, 1, "Entao.txt", 0);


    }

    public void testeProcessosPrioritariosNoMesmoTempo(ArrayList<DespachanteProcesso> mDespachantes) {

        mDespachantes.clear();
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 3, 5, 2);
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 5, 3, 3);
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 2, 1, 1);
        adicionarProcessoUsuarioEmFila(mDespachantes, 1, 4, 0, 5);


    }

    private void adicionarProcessoUsuarioEmFila(ArrayList<DespachanteProcesso> mDespachantes, int eTempo, int eTamanho, int ePrioridade, int eBlocos) {

        DespachanteProcesso eItem = new DespachanteProcesso();
        eItem.setInicializacao(eTempo);
        eItem.setTempoProcessador(eTamanho);
        eItem.setPrioridade(ePrioridade);
        eItem.setBlocos(eBlocos);

        mDespachantes.add(eItem);

    }
}
