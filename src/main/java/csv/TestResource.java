package csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import au.com.bytecode.opencsv.CSVReader;

@Path("/csv")  
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

	@Inject
	EntityManager em;
	
	
	@POST
	@Transactional
	@Path("/register")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String upload( MultipartFormDataInput input) throws IOException {
		
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();//gets the multipart file
		List<InputPart> inputParts = uploadForm.get("uploadedFile");//gets the file directly

		for (InputPart inputPart : inputParts) {

		 try {

			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);

			Reader read = new InputStreamReader(inputStream);
			CSVReader reader = new CSVReader((read), ',');
			
			List<String[]> records = reader.readAll();

			Iterator<String[]> iterator = records.iterator();
			
			if(iterator.hasNext())
				iterator.next(); //skip header

			while (iterator.hasNext()) {
				String[] record = iterator.next();
				Test emp = new Test();
				emp.setName(record[0]);
				emp.setAge(record[1]);
				emp.setSex(record[2]);
				em.persist(emp);
			}

			reader.close();
			

		  } catch (IOException e) {
			e.printStackTrace();
		  }

		}
		
		
		
		return "Success";
	}
}
