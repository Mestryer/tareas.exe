package pe.edu.upeu.sisventas.java.config;

import pe.edu.upeu.sisventas.Repository.CategoriaRepository;
import pe.edu.upeu.sisventas.Repository.MarcaRepository;
import pe.edu.upeu.sisventas.Repository.ProductoRepository;
import pe.edu.upeu.sisventas.Repository.UnidadMedidaRepository;
import pe.edu.upeu.sisventas.controller.ProductoController;
import pe.edu.upeu.sisventas.service.ICategoriaService;
import pe.edu.upeu.sisventas.service.IMarcaService;
import pe.edu.upeu.sisventas.service.IProductoService;
import pe.edu.upeu.sisventas.service.IUnidadMedidaService;
import pe.edu.upeu.sisventas.service.impi.CategoriaServiceimp;
import pe.edu.upeu.sisventas.service.impi.MarcaServiceimp;
import pe.edu.upeu.sisventas.service.impi.UnidadMedidaServiceimp;
import pe.edu.upeu.sysventas.controller.*;
import pe.edu.upeu.sysventas.repository.*;
import pe.edu.upeu.sysventas.service.*;
import pe.edu.upeu.sysventas.service.impl.*;
import java.util.HashMap;
import java.util.Map;

public class AppContext {

    // Singleton: una sola instancia en toda la app
    private static AppContext instance;

    public static synchronized AppContext getInstance() {
        if (instance == null) instance = new AppContext();
        return instance;
    }

    // El "directorio": Clase → Objeto
    private final Map<Class<?>, Object> contenedor = new HashMap<>();

    // Constructor privado: aquí se arma toda la aplicación
    private AppContext() {
        registrarRepositorios();
        registrarServicios();
        registrarControladores();
    }

    // CAPA 1 — REPOSITORIOS
    // Cada repositorio sabe hablar con una tabla de la base de datos.
    // No reciben dependencias: solo necesitan la conexión (DatabaseConfig).
    private void registrarRepositorios() {
        //registrar(CategoriaRepository.class, new CategoriaRepository());


        registrar(CategoriaRepository.class,     new CategoriaRepository());
        registrar(MarcaRepository.class,         new MarcaRepository());
        registrar(UnidadMedidaRepository.class,  new UnidadMedidaRepository());
        registrar(ProductoRepository.class,      new ProductoRepository());

    }

    // CAPA 2 — SERVICIOS
    // Cada servicio recibe su repositorio por constructor.
    // Usamos getBean() para buscarlo en el directorio: no creamos nada nuevo.
    private void registrarServicios() {

        registrar(ICategoriaService.class,    new CategoriaServiceimp(   getBean(CategoriaRepository.class)));
        registrar(IMarcaService.class,        new MarcaServiceimp(       getBean(MarcaRepository.class)));
        registrar(IProductoService.class,     new ProductoServiceImp(    getBean(ProductoRepository.class)));
        registrar(IUnidadMedidaService.class, new UnidadMedidaServiceimp(getBean(pe.edu.upeu.sisventas.model.UnidMedida.class)));


    }

    // CAPA 3 — CONTROLADORES JavaFX
    // Cada controlador recibe los servicios que necesita por constructor.
    // El FXMLLoader los busca aquí a través de setControllerFactory().
    private void registrarControladores() {
        //registrar(LoginController.class, new LoginController(getBean(IUsuarioService.class)));
        registrar(ProductoController.class,
                new ProductoController(
                        getBean(IMarcaService.class),
                        getBean(ICategoriaService.class),
                        getBean(IUnidadMedidaService.class),
                        getBean(IProductoService.class)));

    }

    // API del contenedor — estos dos métodos son todo lo que hace la DI
    /** Guarda un objeto en el directorio, indexado por su tipo o interfaz. */
    private void registrar(Class<?> tipo, Object bean) {
        contenedor.put(tipo, bean);
    }

    /**
     * Busca y devuelve un objeto por su tipo o interfaz.
     * Equivale a lo que hace Spring/Micronaut con @Inject automáticamente.
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> tipo) {
        Object bean = contenedor.get(tipo);
        if (bean == null) {
            // Búsqueda por compatibilidad: sirve cuando se pide una interfaz
            // y el objeto guardado es su implementación concreta.
            bean = contenedor.values().stream()
                    .filter(b -> tipo.isAssignableFrom(b.getClass()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "Bean no encontrado: " + tipo.getName() +
                                    "\n→ ¿Lo registraste en AppContext?"));
        }
        return (T) bean;
    }
}