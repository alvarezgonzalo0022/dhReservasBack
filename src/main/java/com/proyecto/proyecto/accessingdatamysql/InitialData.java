package com.proyecto.proyecto.accessingdatamysql;

import com.proyecto.proyecto.accessingdatamysql.model.Categoria;
import com.proyecto.proyecto.accessingdatamysql.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class InitialData implements ApplicationRunner {

    private CategoriaRepository categoriaRepository;

    @Autowired
    public InitialData(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Categoria categoriaAuto = new Categoria("Auto", "Vehiculo de 4 ruedas", "https://images.unsplash.com/photo-1494976388531-d1058494cdd8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80");
        categoriaRepository.save(categoriaAuto);
        Categoria categoriaMoto = new Categoria("Moto", "Vehiculo de 2 ruedas", "https://images.unsplash.com/photo-1558981806-ec527fa84c39?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80");
        categoriaRepository.save(categoriaMoto);
        Categoria categoriaCamion = new Categoria("Camion", "Vehiculo de grandes dimensiones, con 4 ruedas", "https://images.unsplash.com/photo-1591768793355-74d04bb6608f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80");
        categoriaRepository.save(categoriaCamion);
        Categoria categoriaCamioneta = new Categoria("Camioneta", "Vehiculo de 4 ruedas, con caja trasera", "https://www.chevrolet.com.uy/content/dam/chevrolet/south-america/uruguay/espanol/index/pick-ups/2021-silverado/colorizer/blanco-invierno.jpg?imwidth=960");
        categoriaRepository.save(categoriaCamioneta);
    }
}
