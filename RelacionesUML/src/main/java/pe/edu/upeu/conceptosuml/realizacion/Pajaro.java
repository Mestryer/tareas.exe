package pe.edu.upeu.conceptosuml.realizacion;

public class Pajaro implements Volador {
    @Override
    public void volar() {
        System.out.println("el pajaro vuela");
    }
    @Override
    public void correr(){
        System.out.println("el pajaro corre para volar ");
    }
}
