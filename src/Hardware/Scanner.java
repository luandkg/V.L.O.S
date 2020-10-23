package Hardware;

public class Scanner extends Dispositivo  {

    private String mModelo;

    public Scanner(String eModelo){

        mModelo=eModelo;

    }

    public String getModelo(){return mModelo;}

    @Override
    public String getTipo() {
        return "SCANNER";
    }

}
