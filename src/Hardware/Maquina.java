package Hardware;

import java.util.ArrayList;

public class Maquina {

    private Processador mProcessador;
    private ArrayList<Dispositivo> mDispositivos;

    // CLASSE MAQUINA

    public Maquina(Processador eProcessador) {

        mProcessador = eProcessador;
        mDispositivos = new ArrayList<Dispositivo>();

    }

    public Processador getProcessador() {
        return mProcessador;
    }

    public ArrayList<Dispositivo> getDispositivos() {
        return mDispositivos;
    }

    public void adicionarDispositivo(Dispositivo eDispositivo){
        mDispositivos.add(eDispositivo);
    }

}
