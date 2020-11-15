package Hardware;

public class Scanner implements Dispositivo  {

    private String mModelo;

    // IMPLEMENTACAO DA SCANNER UTILIZANDO A INTERFACE DISPOSITIVO
    // TIPO = SCANNER

    public Scanner(String eModelo){

        mModelo=eModelo;

    }

    public String getModelo(){return mModelo;}

    @Override
    public String getTipo() {
        return "SCANNER";
    }

}
