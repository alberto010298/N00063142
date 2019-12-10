package pe.edu.delfines.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.delfines.models.entity.Alquiler;
import pe.edu.delfines.models.entity.Cliente;
import pe.edu.delfines.services.AlquilerService;
import pe.edu.delfines.services.ClienteService;


@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private AlquilerService alquilerService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< List<Cliente> > fetchClientes() {
		try {
			List<Cliente> clientes = clienteService.findAll();
			return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);   
		} catch (Exception e) {
			return new ResponseEntity<List<Cliente>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Cliente > saveCliente(@RequestBody Cliente cliente) {
		try {
			Cliente newCliente = clienteService.save(cliente);
			return new ResponseEntity< Cliente >(newCliente, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity< Cliente >(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Cliente > updateCliente(@PathVariable("id") Integer id, 
			@RequestBody Cliente cliente) {
		try {
			if(id.equals(cliente.getId())) {
				Optional<Cliente> optional = clienteService.findById(id);
				if(optional.isPresent()) {
					Cliente updateCliente = clienteService.update(cliente);
					return new ResponseEntity<Cliente>(updateCliente, 
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
				}				
			} else {
				return new ResponseEntity<Cliente>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Cliente> deleteCliente(@PathVariable("id") Integer id) {
		try {			
			Optional<Cliente> optional = clienteService.findById(id);
			if(optional.isPresent()) {
				clienteService.deleteById(id);
				return new ResponseEntity<Cliente>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping(path = "/{id}/alquileres")
	public ResponseEntity<List<Alquiler>> fetchAlquiler(
			@PathVariable("id") Integer id){
		try {
			Optional<Cliente> optional = clienteService.findById(id);
			if(optional.isPresent()) {
				return new ResponseEntity<List<Alquiler>>(
						optional.get().getAlquileres(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Alquiler>>(
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<List<Alquiler>>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping( path = "/{id}/alquileres",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Alquiler > saveAlquiler(
			@PathVariable("id") Integer id,
			@RequestBody Alquiler alquiler) {
		try {
			Optional<Cliente> optional = clienteService.findById(id);
			if(optional.isPresent()) {				
				alquiler.setCliente( optional.get() );
				Alquiler nuevoAlquiler = alquilerService.save(alquiler);
				return new ResponseEntity<Alquiler>(nuevoAlquiler, 
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Alquiler>(HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity< Alquiler >(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
