package com.previred.app_mobile_previred.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.previred.app_mobile_previred.model.DocumentoDTO;
import com.previred.app_mobile_previred.model.Login;
import com.previred.app_mobile_previred.model.LoginRespuesta;
import com.previred.app_mobile_previred.model.Mensaje;
import com.previred.app_mobile_previred.model.Notificacion;
import com.previred.app_mobile_previred.model.Usuario;
import com.previred.app_mobile_previred.servicio.ValidacionDocumentoService;
import com.previred.app_mobile_previred.util.JwtTokenProvider;



@Controller
@RequestMapping(path = "/documento")
public class DocumentoController {
	
	
	
	@Autowired
	ValidacionDocumentoService validacionDocumentoService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	final ObjectMapper mapper = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoController.class);
	
	@PostMapping(path = "/guardarDocumento")
	public Boolean guardarDocumento(@RequestBody DocumentoDTO documentoDTO) {
		return validacionDocumentoService.guardar(documentoDTO);
	}
	
	/*@GetMapping(path = "/obtenerDocumentoPorCodigo")
	public ResponseEntity<DocumentoDTO>	 obtenerDocumentoPorCodigo(@RequestBody String codigo, 
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Optional<DocumentoDTO> doc =validacionDocumentoService.obtenerPorCodigo(codigo);
		if(doc.isPresent()) {
			return new ResponseEntity<>(doc.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}*/
	@GetMapping(path = "/obtenerDocumentoPorCodigo")
    public ResponseEntity<DocumentoDTO>     obtenerDocumentoPorCodigo(@RequestParam String codigo) {
        Optional<DocumentoDTO> doc =validacionDocumentoService.obtenerPorCodigo(codigo);
        if(doc.isPresent()) {
            return new ResponseEntity<>(doc.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }       
    }
	@GetMapping(path = "/obtenerUsuario")
	public ResponseEntity<Usuario>	obtenerUsuario(@RequestBody String codigo, 
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Integer rut = jwtTokenProvider.obtenerRutUsuario(authorization);
		Usuario usuario = mapper.convertValue(jwtTokenProvider.obtenerClaims(authorization).get("usuario"), Usuario.class );
		LOGGER.info("EL RUT ES {} y el usuario {}", rut , usuario.getNombres());
		return new ResponseEntity<>(usuario, HttpStatus.OK);
		
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<Usuario>	login(@RequestBody Login login) throws JsonProcessingException {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept( Arrays.asList( MediaType.APPLICATION_JSON));
		
		HttpEntity<Login> request = new HttpEntity<>(login, headers);

		ResponseEntity<LoginRespuesta> response = restTemplate.postForEntity("http://login-route-serv-trans-dev.infra.devops.previred/api-login/login",request,  LoginRespuesta.class);
		LoginRespuesta loginRespuesta = response.getBody();
		Usuario usuario = new ObjectMapper().
				convertValue(jwtTokenProvider.obtenerClaims(loginRespuesta.getAccessToken()).get("usuario"), Usuario.class );
		LOGGER.info("EL RUT ES {} y el usuario {}", usuario.getRut() , usuario.getNombres());
		usuario.setAccessToken(loginRespuesta.getAccessToken());
		usuario.setRefreshToken(loginRespuesta.getRefreshToken());
		usuario.setAccessToken(loginRespuesta.getAccessToken());
		usuario.setAccessToken(loginRespuesta.getAccessToken());
		return new ResponseEntity<>(usuario, HttpStatus.OK);

	}
	
	@GetMapping(path = "/loginRefrescar")
	public ResponseEntity<Usuario>	loginRefrescar(@RequestBody String codigo, 
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Integer rut = jwtTokenProvider.obtenerRutUsuario(authorization);
		Usuario usuario = mapper.convertValue(jwtTokenProvider.obtenerClaims(authorization).get("usuario"), Usuario.class );
		LOGGER.info("EL RUT ES {} y el usuario {}", rut , usuario.getNombres());
		return new ResponseEntity<>(usuario, HttpStatus.OK);
		
	}
	
	@GetMapping(path = "/enviarNotificacion")
	public ResponseEntity<Usuario>	loginRefrescar(@RequestBody Notificacion notificacion ) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "key=AAAAos0h35Q:APA91bE1fQaZnNdkVIupREC8Gx9R3pBiinyVhmqVTzowb3Qb1U6CAGf0QnZkQZWC8l_SHzwHmgXzclICRgNPgQ8CTkrwv6AwODmRK5EppICgeOS4cMSBKVn8KP_M1hO1mHZ7P8UtvZCj");
		headers.setContentType(MediaType.APPLICATION_JSON);
		Mensaje mensaje = new Mensaje();
		mensaje.setTo("dvTOrm4MRbm2jsusBj1BFg:APA91bH-LMTL32VlC_rFGdq9RhWX45oHKg0rDFlzHhiaAg-PS23fvDC2FtAI-wgNOvsWnUpsq7kOksMfjL84Ui_ARUgVncfDdfs5zPjgWgEKLYc2x8LzYzH7Hy5l7IHmoi8q534B4h2X");
		mensaje.setNotification(notificacion);
		HttpEntity<Mensaje> request = new HttpEntity<>(mensaje, headers);

		ResponseEntity response = restTemplate.postForEntity("https://fcm.googleapis.com/fcm/send",request, null);
		//JsonNode loginRespuesta = mapper.readTree(response.getBody().toString());

		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping(path = "/enviarNotificacion2")
	public ResponseEntity<Usuario>	enviarNotificaciones(@RequestBody Notificacion notificacion ) throws Exception {
		
		FileInputStream serviceAccount =
				  new FileInputStream("C:\\key\\flutter-notificaciones-26f7c-firebase-adminsdk-itw8s-bbce0784db.json");

				FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				  .build();

				FirebaseApp.initializeApp(options);
		
		
		
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "key=AAAAos0h35Q:APA91bE1fQaZnNdkVIupREC8Gx9R3pBiinyVhmqVTzowb3Qb1U6CAGf0QnZkQZWC8l_SHzwHmgXzclICRgNPgQ8CTkrwv6AwODmRK5EppICgeOS4cMSBKVn8KP_M1hO1mHZ7P8UtvZCj");
		headers.setContentType(MediaType.APPLICATION_JSON);
		Mensaje mensaje = new Mensaje();
		mensaje.setTo("dvTOrm4MRbm2jsusBj1BFg:APA91bH-LMTL32VlC_rFGdq9RhWX45oHKg0rDFlzHhiaAg-PS23fvDC2FtAI-wgNOvsWnUpsq7kOksMfjL84Ui_ARUgVncfDdfs5zPjgWgEKLYc2x8LzYzH7Hy5l7IHmoi8q534B4h2X");
		mensaje.setNotification(notificacion);
		HttpEntity<Mensaje> request = new HttpEntity<>(mensaje, headers);

		ResponseEntity response = restTemplate.postForEntity("https://fcm.googleapis.com/fcm/send",request, null);
		//JsonNode loginRespuesta = mapper.readTree(response.getBody().toString());

		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
}
