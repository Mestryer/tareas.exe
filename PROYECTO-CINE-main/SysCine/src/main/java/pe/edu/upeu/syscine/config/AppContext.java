package pe.edu.upeu.syscine.config;

import pe.edu.upeu.syscine.controller.*;
import pe.edu.upeu.syscine.repository.*;
import pe.edu.upeu.syscine.service.*;
import pe.edu.upeu.syscine.service.impl.*;

import java.util.HashMap;
import java.util.Map;

public class AppContext {

    private static AppContext instance;

    public static synchronized AppContext getInstance() {
        if (instance == null) instance = new AppContext();
        return instance;
    }

    private final Map<Class<?>, Object> contenedor = new HashMap<>();

    private AppContext() {
        registrarRepositorios();
        registrarServicios();
        registrarControladores();
    }

    // CAPA 1 - REPOSITORIOS
    private void registrarRepositorios() {
        registrar(GeneroRepository.class, new GeneroRepository());
        registrar(SalaRepository.class, new SalaRepository());
        registrar(FormatoRepository.class, new FormatoRepository());
        registrar(PeliculaRepository.class, new PeliculaRepository());
        registrar(OpinionRepository.class, new OpinionRepository());
        registrar(ClienteRepository.class, new ClienteRepository());
        registrar(VentaRepository.class, new VentaRepository());
    }

    // CAPA 2 - SERVICIOS
    private void registrarServicios() {
        registrar(IGeneroService.class, new GeneroServiceImp(getBean(GeneroRepository.class)));
        registrar(ISalaService.class, new SalaServiceImp(getBean(SalaRepository.class)));
        registrar(IFormatoService.class, new FormatoServiceImp(getBean(FormatoRepository.class)));
        registrar(IPeliculaService.class, new PeliculaServiceImp(getBean(PeliculaRepository.class)));
        registrar(IOpinionService.class, new OpinionServiceImp(getBean(OpinionRepository.class)));
        registrar(IClienteService.class, new ClienteServiceImpl(getBean(ClienteRepository.class)));
        registrar(IVentaService.class, new VentaServiceImp(getBean(VentaRepository.class)));
    }

    // CAPA 3 - CONTROLADORES JavaFX
    private void registrarControladores() {
        registrar(PeliculaController.class,
                new PeliculaController(
                        getBean(ISalaService.class),
                        getBean(IGeneroService.class),
                        getBean(IPeliculaService.class),
                        getBean(IFormatoService.class),
                        getBean(IOpinionService.class),
                        getBean(IVentaService.class)));

        registrar(ClienteController.class, new ClienteController(getBean(IClienteService.class)));
    }

    private void registrar(Class<?> tipo, Object bean) {
        contenedor.put(tipo, bean);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> tipo) {
        Object bean = contenedor.get(tipo);
        if (bean == null) {
            bean = contenedor.values().stream()
                    .filter(b -> tipo.isAssignableFrom(b.getClass()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "Bean no encontrado: " + tipo.getName() +
                                    "\n-> ¿Lo registraste en AppContext?"));
        }
        return (T) bean;
    }
}
