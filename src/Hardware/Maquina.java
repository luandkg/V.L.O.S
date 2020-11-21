package Hardware;

import java.util.ArrayList;

public class Maquina {

    private Arquitetura mArquitetura;
    private ArrayList<Dispositivo> mDispositivos;
    private ArrayList<Processador> mProcessadores;

    // CLASSE MAQUINA

    public Maquina(Arquitetura eArquitetura) {

        mArquitetura = eArquitetura;

        mProcessadores = new ArrayList<Processador>();
        mDispositivos = new ArrayList<Dispositivo>();

    }


    public Arquitetura getArquitetura() {
        return mArquitetura;
    }

    public TipoDeProcessador getTipoDeProcessador() {

        if (mProcessadores.size() == 1) {
            return TipoDeProcessador.MONOPROCESSADOR;
        } else if (mProcessadores.size() > 1) {
            return TipoDeProcessador.MULTIPROCESSADOR;
        } else {
            return TipoDeProcessador.DESCONHECIDO;
        }

    }


    public ArrayList<Dispositivo> getDispositivos() {
        return mDispositivos;
    }

    public ArrayList<Processador> getProcessadores() {
        return mProcessadores;
    }

    public void adicionarDispositivo(Dispositivo eDispositivo) {
        mDispositivos.add(eDispositivo);
    }

    public void adicionarProcessador(Processador eProcessador) {
        mProcessadores.add(eProcessador);
    }

}
