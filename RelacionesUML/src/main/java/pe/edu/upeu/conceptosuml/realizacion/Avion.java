package pe.edu.upeu.conceptosuml.realizacion;

public class Avion implements Volador{
    @Override
    public void volar() {
        System.out.println("el avion vuela por las nubes  ");
    }

    @Override
    public void correr(){
        System.out.println("el avion antes de despegar correr");
    }
}
