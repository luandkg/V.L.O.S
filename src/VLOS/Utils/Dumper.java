package VLOS.Utils;

import VLOS.Despachante.DespachanteProcesso;
import VLOS.Memoria.VLMemoria;
import VLOS.Processo.Dependencia;
import VLOS.Processo.Processo;
import VLOS.Processo.ProcessoStatus;
import VLOS.Processo.VLProcessos;
import VLOS.Recurso.Recurso;
import VLOS.Recurso.VLRecursos;


public class Dumper {

    // CLASSE Dumper : Responsavel por imprimir logs no Console do Terminal

    private Utils mUtils;

    public Dumper() {
        mUtils = new Utils();
    }

    public void dump_processos(VLProcessos mVLProcessos) {

        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t - PROCESSOS ");

        System.out.println("\t\t - KERNEL");
        for (Processo eProcesso : mVLProcessos.getProcessosKernel()) {

            String mP0 = " - PROCESSO KERNEL  : " + mUtils.getIntCasas(eProcesso.getPID(), 3);
            String mP1 = " | Prioridade : " + mUtils.getIntCasas(eProcesso.getPrioridade(), 2);
            String mP2 = " | Memoria Offset : " + mUtils.getLongCasas(eProcesso.getOffset(), 8);
            String mP3 = " | Blocos Alocados : " + mUtils.getLongCasas(eProcesso.getBlocos(), 8);
            String mP4 = " | Status : " + mUtils.getOrganizado(eProcesso.getStatus().toString(), 10);
            String mP5 = " | Processamento : " + eProcesso.getProcessadoStatus();

            System.out.println("\t\t\t" + mP0 + mP1 + mP2 + mP3 + mP4 + mP5);

        }

        System.out.println("\t\t - FILA 1");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila1()) {


            String mP0 = " - PROCESSO USUARIO : " + mUtils.getIntCasas(eProcesso.getPID(), 3);
            String mP1 = " | Prioridade : " + mUtils.getIntCasas(eProcesso.getPrioridade(), 2);
            String mP2 = " | Memoria Offset : " + mUtils.getLongCasas(eProcesso.getOffset(), 8);
            String mP3 = " | Blocos Alocados : " + mUtils.getLongCasas(eProcesso.getBlocos(), 8);
            String mP4 = " | Status : " + mUtils.getOrganizado(eProcesso.getStatus().toString(), 10);
            String mP5 = " | Processamento : " + eProcesso.getProcessadoStatus();

            System.out.println("\t\t\t" + mP0 + mP1 + mP2 + mP3 + mP4 + mP5);

        }

        System.out.println("\t\t - FILA 2");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila2()) {


            String mP0 = " - PROCESSO USUARIO : " + mUtils.getIntCasas(eProcesso.getPID(), 3);
            String mP1 = " | Prioridade : " + mUtils.getIntCasas(eProcesso.getPrioridade(), 2);
            String mP2 = " | Memoria Offset : " + mUtils.getLongCasas(eProcesso.getOffset(), 8);
            String mP3 = " | Blocos Alocados : " + mUtils.getLongCasas(eProcesso.getBlocos(), 8);
            String mP4 = " | Status : " + mUtils.getOrganizado(eProcesso.getStatus().toString(), 10);
            String mP5 = " | Processamento : " + eProcesso.getProcessadoStatus();

            System.out.println("\t\t\t" + mP0 + mP1 + mP2 + mP3 + mP4 + mP5);

        }

        System.out.println("\t\t - FILA 3");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila3()) {


            String mP0 = " - PROCESSO USUARIO : " + mUtils.getIntCasas(eProcesso.getPID(), 3);
            String mP1 = " | Prioridade : " + mUtils.getIntCasas(eProcesso.getPrioridade(), 2);
            String mP2 = " | Memoria Offset : " + mUtils.getLongCasas(eProcesso.getOffset(), 8);
            String mP3 = " | Blocos Alocados : " + mUtils.getLongCasas(eProcesso.getBlocos(), 8);
            String mP4 = " | Status : " + mUtils.getOrganizado(eProcesso.getStatus().toString(), 10);
            String mP5 = " | Processamento : " + eProcesso.getProcessadoStatus();

            System.out.println("\t\t\t" + mP0 + mP1 + mP2 + mP3 + mP4 + mP5);

        }

        System.out.println("\t-----------------------------------------------------");

    }

    private String getProcessoDebug(String eInicio, Processo eProcesso, long mTempoCorrente) {

        String mRet = "";

        String mP0 = eInicio + mUtils.getIntCasas(eProcesso.getPID(), 3);
        String mP1 = " | Prio. : " + mUtils.getIntCasas(eProcesso.getPrioridade(), 2);
        String mP2 = " | M.Offset : " + mUtils.getLongCasas(eProcesso.getOffset(), 5);
        String mP3 = " | B.Alocados : " + mUtils.getLongCasas(eProcesso.getBlocos(), 5);
        String mP4 = " | Status : " + mUtils.getOrganizado(eProcesso.getStatus().toString(), 10);
        String mP5 = " | Proc : " + eProcesso.getProcessadoStatus();
        String mP6 = " | T.Criacao : " + mUtils.getLongCasas(eProcesso.getTempoCriacao(), 3) + "s";

        long mTempoEspera = 0;
        if (eProcesso.isConcluido()) {
            mTempoEspera = eProcesso.getTempoConclusao() - eProcesso.getTempoCriacao();
        } else {
            mTempoEspera = mTempoCorrente - eProcesso.getTempoCriacao();
        }
        String mP7 = " | T.Espera : " + mUtils.getLongCasas(mTempoEspera, 3) + "s";
        if (eProcesso.getProcessado() > 0 || eProcesso.getStatus() == ProcessoStatus.EXECUTANDO) {
            String mP8 = " | T.Inicio : " + mUtils.getLongCasas(eProcesso.getTempoExecucaoInicio(), 3) + "s";
            if (eProcesso.isConcluido()) {
                String mP9 = " | T.Conclusao : " + mUtils.getLongCasas(eProcesso.getTempoConclusao(), 3) + "s";
                long mTempoExecucao = eProcesso.getTempoConclusao() - eProcesso.getTempoExecucaoInicio();
                String mP10 = " | T.Execucao : " + mUtils.getLongCasas(mTempoExecucao, 3) + "s";
                mRet = mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7 + mP8 + mP9 + mP10;
            } else {
                mRet = mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7 + mP8;
            }
        } else {
            mRet = mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7;
        }

        return mRet;
    }

    private String getProcessoEsperandoDebug(String eInicio, Processo eProcesso) {

        String mRet = "";

        String mP0 = eInicio + mUtils.getIntCasas(eProcesso.getPID(), 3);
        String mP1 = " | Prio. : " + mUtils.getIntCasas(eProcesso.getPrioridade(), 2);
        String mP2 = " | M.Offset : " + mUtils.getLongCasas(eProcesso.getOffset(), 5);
        String mP3 = " | B.Alocados : " + mUtils.getLongCasas(eProcesso.getBlocos(), 5);
        String mP4 = " | Status : " + mUtils.getOrganizado(eProcesso.getStatus().toString(), 10);
        String mP5 = " | Proc : " + eProcesso.getProcessadoStatus();
        String mP6 = " | T.Criacao : " + mUtils.getLongCasas(eProcesso.getTempoCriacao(), 3) + "s";

        int mPrecisa = 0;
        int mConseguiu = 0;

        for (Dependencia mDependencia : eProcesso.getDependencias()) {
            mPrecisa += 1;

            if (mDependencia.getStatus()) {
                mConseguiu += 1;
            } else {

            }
        }


        String mP7 = " | Precisa : " + mPrecisa;

        String mP8 = " | Conseguiu : " + mConseguiu;

        mRet = mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7 + mP8;


        return mRet;
    }


    public void dump_processosCompleto(long mTempoCorrente, VLProcessos mVLProcessos) {

        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t - PROCESSOS ");

        System.out.println("\t\t - KERNEL");
        for (Processo eProcesso : mVLProcessos.getProcessosKernel()) {

            String eDebug = getProcessoDebug(" - P.KERNEL  : ", eProcesso, mTempoCorrente);
            System.out.println("\t\t\t" + eDebug);

        }

        System.out.println("\t\t - FILA 1");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila1()) {

            String eDebug = getProcessoDebug(" - P.USUARIO : ", eProcesso, mTempoCorrente);
            System.out.println("\t\t\t" + eDebug);

        }

        System.out.println("\t\t - FILA 2");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila2()) {

            String eDebug = getProcessoDebug(" - P.USUARIO : ", eProcesso, mTempoCorrente);
            System.out.println("\t\t\t" + eDebug);

        }

        System.out.println("\t\t - FILA 3");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila3()) {

            String eDebug = getProcessoDebug(" - P.USUARIO : ", eProcesso, mTempoCorrente);
            System.out.println("\t\t\t" + eDebug);

        }

        System.out.println("\t-----------------------------------------------------");

    }


    public void dump_processosEsperando(VLProcessos mVLProcessos) {

        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t - PROCESSOS ESPERANDO ");

        System.out.println("\t\t - KERNEL");
        for (Processo eProcesso : mVLProcessos.getProcessosKernel()) {

            if (eProcesso.isEsperando()) {
                String eDebug = getProcessoEsperandoDebug(" - P.KERNEL  : ", eProcesso);
                System.out.println("\t\t\t" + eDebug);
            }


        }

        System.out.println("\t\t - FILA 1");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila1()) {

            if (eProcesso.isEsperando()) {
                String eDebug = getProcessoEsperandoDebug(" - P.USUARIO : ", eProcesso);
                System.out.println("\t\t\t" + eDebug);
            }

        }

        System.out.println("\t\t - FILA 2");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila2()) {

            if (eProcesso.isEsperando()) {
                String eDebug = getProcessoEsperandoDebug(" - P.USUARIO : ", eProcesso);
                System.out.println("\t\t\t" + eDebug);
            }

        }

        System.out.println("\t\t - FILA 3");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila3()) {

            if (eProcesso.isEsperando()) {
                String eDebug = getProcessoEsperandoDebug(" - P.USUARIO : ", eProcesso);
                System.out.println("\t\t\t" + eDebug);
            }

        }

        System.out.println("\t-----------------------------------------------------");

    }

    public void dump_processo(Processo eProcesso) {

        String mP0 = " - PROCESSO USUARIO : " + mUtils.getIntCasas(eProcesso.getPID(), 3);
        String mP1 = " | Prioridade : " + mUtils.getIntCasas(eProcesso.getPrioridade(), 2);
        String mP2 = " | Memoria Offset : " + mUtils.getLongCasas(eProcesso.getOffset(), 8);
        String mP3 = " | Blocos Alocados : " + mUtils.getLongCasas(eProcesso.getBlocos(), 8);
        String mP4 = " | Status : " + mUtils.getOrganizado(eProcesso.getStatus().toString(), 10);
        String mP5 = " | Processamento : " + eProcesso.getProcessadoStatus();

        System.out.println("\t\t\t" + mP0 + mP1 + mP2 + mP3 + mP4 + mP5);


    }

    public void dump_processoCompleto(long mTempoCorrente, Processo eProcesso) {

        String mP0 = "";

        if (eProcesso.isKernel()) {
            mP0 = " - P.KERNEL   : " + mUtils.getIntCasas(eProcesso.getPID(), 3);
        } else {
            mP0 = " - P.USUARIO  : " + mUtils.getIntCasas(eProcesso.getPID(), 3);
        }

        String mP1 = " | Prio. : " + mUtils.getIntCasas(eProcesso.getPrioridade(), 2);
        String mP2 = " | M.Offset : " + mUtils.getLongCasas(eProcesso.getOffset(), 5);
        String mP3 = " | B.Alocados : " + mUtils.getLongCasas(eProcesso.getBlocos(), 5);
        String mP4 = " | Status : " + mUtils.getOrganizado(eProcesso.getStatus().toString(), 10);
        String mP5 = " | Proc : " + eProcesso.getProcessadoStatus();
        String mP6 = " | T.Criacao : " + mUtils.getLongCasas(eProcesso.getTempoCriacao(), 3) + "s";

        long mTempoEspera = 0;
        if (eProcesso.isConcluido()) {
            mTempoEspera = eProcesso.getTempoConclusao() - eProcesso.getTempoCriacao();
        } else {
            mTempoEspera = mTempoCorrente - eProcesso.getTempoCriacao();
        }
        String mP7 = " | T.Espera : " + mUtils.getLongCasas(mTempoEspera, 3) + "s";
        if (eProcesso.getProcessado() > 0) {
            String mP8 = " | T.Inicio : " + mUtils.getLongCasas(eProcesso.getTempoExecucaoInicio(), 3) + "s";
            if (eProcesso.isConcluido()) {
                String mP9 = " | T.Conclusao : " + mUtils.getLongCasas(eProcesso.getTempoConclusao(), 3) + "s";
                long mTempoExecucao = eProcesso.getTempoConclusao() - eProcesso.getTempoExecucaoInicio();
                String mP10 = " | T.Execucao : " + mUtils.getLongCasas(mTempoExecucao, 3) + "s";
                System.out.println("\t\t\t" + mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7 + mP8 + mP9 + mP10);
            } else {
                System.out.println("\t\t\t" + mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7 + mP8);
            }
        } else {
            System.out.println("\t\t\t" + mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7);
        }


    }

    public void dump_processosTrocaDeContexto(VLProcessos mVLProcessos) {

        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t - TROCADOR DE CONTEXTO DE PROCESSOS");

        System.out.println("\t\t - KERNEL");
        for (Processo eProcesso : mVLProcessos.getProcessosKernel()) {

            String eDebug = getProcessoDebugTrocaDeContexto(" - P.KERNEL  : ", eProcesso);
            System.out.println("\t\t\t" + eDebug);

        }

        System.out.println("\t\t - FILA 1");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila1()) {

            String eDebug = getProcessoDebugTrocaDeContexto(" - P.USUARIO : ", eProcesso);
            System.out.println("\t\t\t" + eDebug);

        }

        System.out.println("\t\t - FILA 2");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila2()) {

            String eDebug = getProcessoDebugTrocaDeContexto(" - P.USUARIO : ", eProcesso);
            System.out.println("\t\t\t" + eDebug);

        }

        System.out.println("\t\t - FILA 3");
        for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila3()) {

            String eDebug = getProcessoDebugTrocaDeContexto(" - P.USUARIO : ", eProcesso);
            System.out.println("\t\t\t" + eDebug);

        }

        System.out.println("\t-----------------------------------------------------");


    }

    private String getProcessoDebugTrocaDeContexto(String eInicio, Processo eProcesso) {

        String mRet = "";

        String mP0 = eInicio + mUtils.getIntCasas(eProcesso.getPID(), 3);
        String mP11 = " | Processado  : " + mUtils.getIntCasas(eProcesso.getProcessado(), 3);

        String mP1 = " | Contexto Enviado  : " + mUtils.getIntCasas(eProcesso.getTrocaDeContexto_Enviado(), 3);
        String mP2 = " | Contexto Recebido : " + mUtils.getIntCasas(eProcesso.getTrocaDeContexto_Recebido(), 3);

        mRet = mP0 + mP11 + mP1 + mP2;


        return mRet;
    }

    public void dump_memoria(VLMemoria mVLMemoria) {

        System.out.println("\t-----------------------------------------------------");
        System.out.println("\tMEMORIA");
        System.out.println("\t\t - Disponivel : " + mVLMemoria.getTamanho());
        System.out.println("\t\t - Bloco Tamanho : " + mVLMemoria.getTamanhoBloco() + " bytes :: " + (mVLMemoria.getTamanhoBloco() / 1024) + " kb");
        System.out.println("\t\t - Blocos : " + mVLMemoria.getBlocos());

        System.out.println("\t\t - KERNEL Reservado : " + mVLMemoria.getBlocos_KernelReservados());
        System.out.println("\t\t\t  Livre : " + mVLMemoria.getBlocos_KernelReservados_Livre());
        System.out.println("\t\t\t  Ocupado : " + mVLMemoria.getBlocos_KernelReservados_Ocupado());

        System.out.println("\t\t - Blocos de Usuario : " + mVLMemoria.getBlocos_Usuarios());
        System.out.println("\t\t\t Livre : " + mVLMemoria.getBlocos_Usuarios_Livre());
        System.out.println("\t\t\t Ocupado : " + mVLMemoria.getBlocos_Usuarios_Ocupado());

        System.out.println("\t-----------------------------------------------------");


    }


    public void dump_recursos(VLRecursos mVLRecursos) {

        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t - RECURSOS ");

        for (Recurso eRecurso : mVLRecursos.getRecursos()) {

            System.out.println("\t\t - RECURSO : " + eRecurso.getRID() + " ->> " + eRecurso.getStatus());

        }

        System.out.println("\t-----------------------------------------------------");

    }

    public void dump_despachante(DespachanteProcesso mDespachante) {

        Utils mUtils = new Utils();

        String mP0 = " - Inicializacao  : " + mUtils.getIntCasas(mDespachante.getInicializacao(), 2);
        String mP1 = " | Prioridade : " + mUtils.getIntCasas(mDespachante.getPrioridade(), 2);
        String mP2 = " | Tempo Processamento : " + mUtils.getIntCasas(mDespachante.getTempoProcessador(), 2);
        String mP3 = " | Blocos Alocados : " + mUtils.getLongCasas(mDespachante.getBlocos(), 8);
        String mP4 = " | Impressora : " + mUtils.getIntCasas(mDespachante.getCodigoImpressora(), 2);
        String mP5 = " | Scanner : " + mUtils.getIntCasas(mDespachante.getCodigoScanner(), 2);
        String mP6 = " | Modem : " + mUtils.getIntCasas(mDespachante.getCodigoModem(), 2);
        String mP7 = " | Sata : " + mUtils.getIntCasas(mDespachante.getCodigoSata(), 2);

        System.out.println("\t\t -->> DESPACHAR " + mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7);


    }


    public void dump_processoLista(Processo eProcesso) {


        System.out.println("\t---------------------------------------------");

        System.out.println("\t -->> PID : " + eProcesso.getPID());
        System.out.println("\t");
        System.out.println("\t\t - TIPO        : " + eProcesso.getTipoFormatado());
        System.out.println("\t\t - PRIORIDADE  : " + eProcesso.getPrioridade());
        System.out.println("\t\t - STATUS      : " + eProcesso.getStatus());
        System.out.println("\t\t - OFFSET      : " + eProcesso.getOffset());
        System.out.println("\t\t - BLOCOS      : " + eProcesso.getBlocos());
        System.out.println("\t\t - IMPRESSORA  : " + "");
        System.out.println("\t\t - SCANNER     : " + "");
        System.out.println("\t\t - DRIVER      : " + "");
        System.out.println("\t\t - PROCESSADO  : " + eProcesso.getProcessadoStatus());

        System.out.println("\t---------------------------------------------");


    }

}
