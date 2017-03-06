package com.arnav.controller.rate.review;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.arnav.model.rate.review.UploadedFile;
import com.arnav.repository.rate.review.UploadedFileRepository;

@Path("/public/file")
public class UploadedFileController {
	
	@Autowired
	private UploadedFileRepository uploadedFileRepository;
	
	//this will be uncomment when get solution to replace application context to jersey acceptable format.
	/*@Autowired
	private ApplicationContext applicationContext;*/
	
	/*@POST
	@Path("/upload")
	@Produces("application/json")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
    public UploadedFile create1(@RequestParam("file") MultipartFile file,
    		@RequestParam("sub-directory") String subDirectory) {
		
		UploadedFile uf = new UploadedFile();
		
		String filename = new Date().getTime() + "-" + file.getOriginalFilename();
		
		if (!file.isEmpty()) {
            try {
	            	
            	//This will be replaced when multipart data file request is working.
            	File outputFile = applicationContext.getResource("/resources/uploads/"+ 
            			subDirectory + '/' + filename).getFile();
            	
            	File outputFile = new File(file.getOriginalFilename());
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(outputFile));
                stream.write(bytes);
                stream.close();	                	                   
            	
	            uf.setFilename(filename);
	            uf.setSubDirectory(subDirectory);
	            uf.setLastUpdate(new Date());
	            
	            uploadedFileRepository.save(uf);
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
		return uf;	
    }*/

}
