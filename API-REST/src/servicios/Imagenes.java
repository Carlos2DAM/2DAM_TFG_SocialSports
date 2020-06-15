package servicios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import dao.UsuarioDAO;

@Path("/imagenes")
public class Imagenes {
	
	public static String path = "C:\\Users\\0xNea\\Pictures\\prueba\\"; 
	
	@POST  
    @Path("/upload/{correo}")  
    @Consumes(MediaType.MULTIPART_FORM_DATA)  
    public Response uploadFile(  
            @FormDataParam("file") InputStream uploadedInputStream,  
            @FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("correo") String correo) {
			
            String fileLocation = path + fileDetail.getFileName(); 
            System.out.println(fileDetail.getFileName());
            
            try {  
                FileOutputStream out = new FileOutputStream(new File(fileLocation));  
                int read = 0;  
                byte[] bytes = new byte[1024];  
                while ((read = uploadedInputStream.read(bytes)) != -1) {  
                    out.write(bytes, 0, read);  
                }  
                out.flush();  
                out.close();  
            } catch (IOException e) {
            	e.printStackTrace();
            }  
            
            return Response
            		.status(Status.OK)
            		.entity(UsuarioDAO.getFotoEnBase64(correo))
            		.build();
	}  	
	
	@GET
	@Path("/download")
	@Produces({"image/png", "image/jpeg", "image/jpg"})
	public Response getFile() {
		
		File file = new File(path);
		File[] archivos = file.listFiles();
		
		if(archivos == null || archivos.length == 0) {
			return Response
					.status(Status.NOT_FOUND)
					.entity("No hay imagenes subidas")
					.build();
		}else {
			File archivo = archivos[0];
	        return Response
	        			.status(Status.OK)
	        			.entity(archivo)
	        			.build();
		}
	}

}
