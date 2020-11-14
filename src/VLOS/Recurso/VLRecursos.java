package VLOS.Recurso;

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

    public Recurso adicionarRecurso(String eNome) {

        Recurso eRecurso = new Recurso(mRID, eNome);

        mRID += 1;

        ControladorRecurso eControlador = new ControladorRecurso(eRecurso);
        eRecurso.setControlador(eControlador);

        mControladores.add(eControlador);

        return eRecurso;
    }

}
