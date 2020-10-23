package Hardware;

public class Impressora extends Dispositivo  {

    private String mModelo;

    public Impressora(String eModelo){

        mModelo=eModelo;

    }

    public String getModelo(){return mModelo;}

    @Override
    public String getTipo() {
        return "PRINTER";
    }

}
