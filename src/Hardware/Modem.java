package Hardware;

public class Modem extends Dispositivo  {

    private String mModelo;

    public Modem(String eModelo){

        mModelo=eModelo;

    }

    public String getModelo(){return mModelo;}

    @Override
    public String getTipo() {
        return "MODEM";
    }

}
