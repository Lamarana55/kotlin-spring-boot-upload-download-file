package com.javasampleapproach.kotlin.uploadfile.filestorage

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
class FileStorageImpl: FileStorage{
	
	val log = LoggerFactory.getLogger(this::class.java)
	val rootLocation: Path = Paths.get("filestorage")
	
	override fun store(file: MultipartFile){
	    Files.copy(file.inputStream, this.rootLocation.resolve(file.originalFilename))
	}
	
	override fun loadFile(filename: String): Resource{
		val file = rootLocation.resolve(filename)
        val resource = UrlResource(file.toUri())
		
        if(resource.exists() || resource.isReadable) {
            return resource
        }else{  
        	throw RuntimeException("FAIL!")
        }
	}
	
	override fun deleteAll(){
		FileSystemUtils.deleteRecursively(rootLocation.toFile())
	}
	
	override fun init(){
		Files.createDirectory(rootLocation)
	}
	
	override fun loadFiles(): Stream<Path>{
		return Files.walk(this.rootLocation, 1)
                .filter{path -> !path.equals(this.rootLocation)}
                .map(this.rootLocation::relativize)
	}
}