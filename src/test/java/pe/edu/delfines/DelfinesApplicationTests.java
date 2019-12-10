package pe.edu.delfines;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pe.edu.delfines.models.entity.Alquiler;
import pe.edu.delfines.models.entity.Cliente;
import pe.edu.delfines.models.entity.Habitacion;
import pe.edu.delfines.models.entity.Tipo;
import pe.edu.delfines.models.entity.Vendedor;
import pe.edu.delfines.models.repository.AlquilerRepository;
import pe.edu.delfines.models.repository.ClienteRepository;
import pe.edu.delfines.models.repository.HabitacionRepository;
import pe.edu.delfines.models.repository.TipoRepository;
import pe.edu.delfines.models.repository.VendedorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class DelfinesApplicationTests {

	@Autowired
	private AlquilerRepository alquilerRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private HabitacionRepository habitacionRepository;
	@Autowired
	private TipoRepository tipoRepository;
	@Autowired
	private VendedorRepository vendedorRepository;
	
	@Test
	void contextLoads() {
		
		try {
			//Vendedores
			Vendedor juan = new Vendedor();
			juan.setId("V01");
			juan.setNombre("Juan");
			juan.setDireccion("Av. Garzon");
			juan.setTelefono("9823452");
			juan.setObservacion("al dia");
			juan.setSueldo((float) 979.34);
			
			Vendedor mario = new Vendedor();
			mario.setId("V02");
			mario.setNombre("Mario");
			mario.setDireccion("Av. Vizcarra");
			mario.setTelefono("9863852");
			mario.setObservacion("excelente");
			mario.setSueldo((float) 1050.90);
			
			juan = vendedorRepository.save(juan);
			mario = vendedorRepository.save(mario);
			
			
			//Alquileres
			Alquiler n01 = new Alquiler();
			n01.setPrecio((float) 50.10);
			n01.setFechaEntrada(new Date(119, 9, 15));
			n01.setFechaSalida(new Date(119, 9, 16));
			n01.setEstado("muy bueno");
			n01.setObservacion("duradero");
			
			Alquiler n02 = new Alquiler();
			n02.setPrecio((float) 40.10);
			n02.setFechaEntrada(new Date(119, 5, 10));
			n02.setFechaSalida(new Date(119, 5, 11));
			n02.setEstado("regular");
			n02.setObservacion("con ducha");
			
			n01 = alquilerRepository.save(n01);
			n02 = alquilerRepository.save(n02);
			
			
			
			//Habitaciones
			Habitacion h1 = new Habitacion();
			h1.setNumeroCamas(2);
			h1.setDescripcion("comodas");
			h1.setPrecio((float) 15.80);
			h1.setObservacion("acolchadas");
			
			Habitacion h2 = new Habitacion();
			h2.setNumeroCamas(3);
			h2.setDescripcion("blandas");
			h2.setPrecio((float) 34.42);
			h2.setObservacion("amobladas");
			
			h1 = habitacionRepository.save(h1);
			h2 = habitacionRepository.save(h2);
			
			
			//Clientes
			Cliente c1 = new Cliente();
			c1.setDocumento("73975654");
			c1.setNombre("Frank");
			c1.setFechaNacimiento(new Date(2019, 5, 9));
			c1.setLugarNacimiento("Miraflores");
			c1.setSexo('M');
			c1.setObservacion("cumplido");
			
			Cliente c2 = new Cliente();
			c2.setDocumento("03826654");
			c2.setNombre("Eliana");
			c2.setFechaNacimiento(new Date(2019, 12, 2));
			c2.setLugarNacimiento("San Miguel");
			c2.setSexo('F');
			c2.setObservacion("morosa");
			
			c1 = clienteRepository.save(c1);
			c2 = clienteRepository.save(c2);

			
			//Tipos
			Tipo t1 = new Tipo();
			t1.setId("T01");
			t1.setNombre("matrimonial");
			
			Tipo t2 = new Tipo();
			t2.setId("T02");
			t2.setNombre("extralarge");
			
			t1 = tipoRepository.save(t1);
			t2 = tipoRepository.save(t2);

			
			//Relacion vendedor-alquiler
			juan.addAlquiler(n01);;
			
			//Relacion alquiler-vendedor

			n02.setVendedor(mario);

			
			//Relacion alquiler-habitacion
			n01.setHabitacion(h2);
			
			//Relacion habitacion-alquiler
			h1.addAlquiler(n02);
			
			
		
			//Relacion alquiler-cliente
			n01.setCliente(c2);
			
			//Relacion cliente-alquiler
			c1.addAlquiler(n02);
			
			
			
			//Relacion tipo-habitacion
			t2.addHabitacion(h1);
			
			//Relacion habitacion-tipo
			h2.setTipo(t1);


			//grabar
			vendedorRepository.save(juan);
			vendedorRepository.save(mario);
			alquilerRepository.save(n01);
			alquilerRepository.save(n02);
			habitacionRepository.save(h1);
			habitacionRepository.save(h2);
			clienteRepository.save(c1);
			clienteRepository.save(c2);
			tipoRepository.save(t1);
			tipoRepository.save(t2);

			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

}
