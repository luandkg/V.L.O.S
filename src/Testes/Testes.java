package Testes;

import VLOS.Despachante.DespachadorDeOperacoes;
import VLOS.Despachante.DespachadorDeProcessos;
import VLOS.Despachante.DespachanteProcesso;

public class Testes {

    // CLASSE RESPONSAVEL POR CRIAR TESTES ESPECIFICOS

    // testeProcessosEmTempos                           ->  Cria Processos em Tempos diferentes
    // testeProcessosSimultaneos                        ->  Cria Processos Simultaneos em Tempos diferentes
    // testeProcessosSimultaneosNoMesmoTempo            ->  Cria Processos Simultaneos no mersmo Tempo
    // testeProcessosSimultaneosEmTemposDiferentes      ->  Cria Processos Simultaneos em Tempos diferentes
    // testeProcessosPrioritariosNoMesmoTempo           ->  Cria Processos Prioritarios no mesmo Tempo
    // testeProcessosSimultaneosMultiplasFilas          ->  Cria Processos Simultaneos em Tempos diferentes e em Multiplas Filas


    public void testeProcessosEmTempos(DespachadorDeProcessos mDespachadorDeProcessos, DespachadorDeOperacoes mDespachadorDeOperacoes) {

        mDespachadorDeProcessos.getProcessos().clear();
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 3, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 5, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 3, 2, 0, 1);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 4, 0, 5);

        mDespachadorDeOperacoes.setBlocos(30);
        mDespachadorDeOperacoes.setSegmentosOcupados(10);

        mDespachadorDeOperacoes.getItens().clear();
        mDespachadorDeOperacoes.getOperacoes().clear();

        mDespachadorDeOperacoes.adicionarOperacao(1, 0, "Oi.txt", 2);
        mDespachadorDeOperacoes.adicionarOperacao(1, 0, "Nomes.txt", 2);
        mDespachadorDeOperacoes.adicionarOperacao(3, 0, "Alunos.txt", 3);
        mDespachadorDeOperacoes.adicionarOperacao(3, 1, "Nomes.txt", 0);

    }

    public void testeProcessosSimultaneosNoMesmoTempo(DespachadorDeProcessos mDespachadorDeProcessos, DespachadorDeOperacoes mDespachadorDeOperacoes) {

        mDespachadorDeProcessos.getProcessos().clear();
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 3, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 5, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 5, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 2, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 3, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 7, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 2, 0, 1);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 4, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 3, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 1, 0, 5);


    }

    public void testeProcessosSimultaneosEmTemposDiferentes(DespachadorDeProcessos mDespachadorDeProcessos, DespachadorDeOperacoes mDespachadorDeOperacoes) {

        mDespachadorDeProcessos.getProcessos().clear();
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 3, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 5, 0, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 5, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 2, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 3, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 7, 0, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 3, 2, 0, 1);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 4, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 3, 0, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 1, 0, 5);


    }


    public void testeProcessosSimultaneosMultiplasFilasPrioritarias(DespachadorDeProcessos mDespachadorDeProcessos, DespachadorDeOperacoes mDespachadorDeOperacoes) {

        mDespachadorDeProcessos.getProcessos().clear();

        DespachanteProcesso ep1 = adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 3, 0, 2);
        //  ep1.setCodigoModem(1);

        DespachanteProcesso ep2 = adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 3, 1, 2);
        DespachanteProcesso ep3 = adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 5, 1, 2);
        DespachanteProcesso ep4 = adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 5, 1, 3);
        DespachanteProcesso ep5 =   adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 3, 1, 3);
        DespachanteProcesso ep6 =   adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 7, 2, 3);
        DespachanteProcesso ep7 =   adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 4, 2, 5);
        DespachanteProcesso ep8 =   adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 3, 0, 7);
        DespachanteProcesso ep9 =   adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 5, 0, 5);

        //  ep1.setCodigoModem(1);
        ep2.setCodigoModem(1);
        ep3.setCodigoModem(1);
        ep4.setCodigoModem(1);

        ep5.setCodigoScanner(1);
        ep6.setCodigoScanner(1);
        ep6.setCodigoDisco(1);
        ep6.setCodigoScanner(1);
        ep7.setCodigoScanner(1);
        ep8.setCodigoScanner(1);

        ep7.setCodigoImpressora(1);
        ep8.setCodigoImpressora(1);
        ep9.setCodigoImpressora(1);
        ep9.setCodigoScanner(1);
        ep9.setCodigoModem(1);


        DespachanteProcesso ep10 =  adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 1, 3, 4);

        ep10.setCodigoImpressora(1);

        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 5, 1, 8);

        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 2, 5, 3, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 3, 1, 3, 9);

        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 4, 1, 6, 5);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 5, 1, 5, 10);

        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 7, 3, 8, 50);
        DespachanteProcesso ep11 =  adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 9, 2, 4, 4);

        ep11.setCodigoScanner(1);

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

    public void testeProcessosPrioritariosNoMesmoTempo(DespachadorDeProcessos mDespachadorDeProcessos, DespachadorDeOperacoes mDespachadorDeOperacoes) {

        mDespachadorDeProcessos.getProcessos().clear();
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 3, 5, 2);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 5, 3, 3);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 2, 1, 1);
        adicionarProcessoUsuarioEmFila(mDespachadorDeProcessos, 1, 4, 0, 5);


    }

    private DespachanteProcesso adicionarProcessoUsuarioEmFila(DespachadorDeProcessos mDespachadorDeProcessos, int eTempo, int eTamanho, int ePrioridade, int eBlocos) {

        DespachanteProcesso eItem = new DespachanteProcesso();
        eItem.setInicializacao(eTempo);
        eItem.setTempoProcessador(eTamanho);
        eItem.setPrioridade(ePrioridade);
        eItem.setBlocos(eBlocos);

        mDespachadorDeProcessos.getProcessos().add(eItem);

        return eItem;
    }
}
