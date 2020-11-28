package VLOS.Recurso;

import Hardware.Dispositivo;
import Hardware.SATA;

import java.util.ArrayList;

public class VLRecursos {

    private int mRID;
    private ArrayList<ControladorRecurso> mControladores;

    // VLRECURSOS E RESPONSAVVEL POR GERENCIAR OS RECURSOS DO VLOS


    public VLRecursos() {

        mRID = 0;
        mControladores = new ArrayList<ControladorRecurso>();

    }

    public ArrayList<Recurso> getRecursos() {
        ArrayList<Recurso> mRecursos = new ArrayList<Recurso>();
        for (ControladorRecurso cr : mControladores) {
            mRecursos.add(cr.getRecurso());
        }
        return mRecursos;
    }

    public ArrayList<ControladorRecurso> getControladores() {
        return mControladores;
    }

    public Recurso adicionarRecurso(Dispositivo eDispositivo) {

        Recurso eRecurso = new Recurso(mRID, eDispositivo.getTipo());

        mRID += 1;

        ControladorRecurso eControlador = new ControladorRecurso(eDispositivo.getTipo(), eRecurso);
        eRecurso.setControlador(eControlador);
        mControladores.add(eControlador);


        return eRecurso;
    }

    public boolean temDisponivel(String eRecurso) {

        boolean ret = false;

        // System.out.println("Procurando por Recurso : " + eRecurso);

        for (ControladorRecurso eProcurando : mControladores) {
            //      System.out.println("Procurando por Recurso em VLRecursos : " + eProcurando.getTipo() + " -> " + eProcurando.getStatus());

            if (eProcurando.mesmoTipo(eRecurso)) {
                if (eProcurando.isDisponivel()) {
                    ret = true;
                    break;
                }
            }

        }

        //System.out.println("Resposta ao Recurso : " + ret);


        return ret;
    }

    public void liberarRecurso(int eRID) {
        for (ControladorRecurso eProcurando : mControladores) {
            if (eProcurando.getRecurso().getRID() == eRID) {
                eProcurando.liberar();
            }
        }
    }

    public String getNomePorRID(int eRID) {
        String eNome = "";
        for (ControladorRecurso eProcurando : mControladores) {
            if (eProcurando.getRecurso().getRID() == eRID) {
                eNome = eProcurando.getRecurso().getNome();
                break;
            }
        }
        return eNome;
    }

    public int getRecursoEBloqueio(String eRecurso) {

        int eID = -1;

        for (ControladorRecurso eProcurando : mControladores) {
            // System.out.println("Passando por Recurso : " + eProcurando.getTipo() + " -> " + eProcurando.getStatus());
            if (eProcurando.mesmoTipo(eRecurso)) {
                if (eProcurando.isDisponivel()) {
                    eID = eProcurando.getRecurso().getRID();
                    eProcurando.usar();
                    break;
                }
            }

        }


        return eID;

    }

}
