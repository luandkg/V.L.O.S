package VLOS;

import VLOS.Memoria.VLMemoria;
import VLOS.Processo.Processo;
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


}
