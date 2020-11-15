package VLOS.Escalonadores;

import VLOS.Processo.Processo;

public interface Escalonador {

    String getNome();

    boolean temProcesso();

    Processo getEscalonado();


}
