package Hardware;

public class Maquina_i368 extends Maquina {

    // IMPLEMENTACAO MAQUINA REAL UTILIZANDO A CLASSE ABSTRATA MAQUINA

    public Maquina_i368() {

        super(Arquitetura.X86);
        this.adicionarProcessador(new Processador(Arquitetura.X86, "Pentium 4", 1));

    }


}
