package Hardware;

public class Modem implements Dispositivo  {

    private String mModelo;

    // IMPLEMENTACAO DO MODEM UTILIZANDO A CLASSE ABSTRATA DISPOSITIVO
    // TIPO = MODEM

    public Modem(String eModelo){

        mModelo=eModelo;

    }

    public String getModelo(){return mModelo;}

    @Override
    public String getTipo() {
        return "MODEM";
    }

    @Override
    public boolean mesmoTipo(String eTipo) {
        return eTipo.contentEquals("MODEM");
    }

}
