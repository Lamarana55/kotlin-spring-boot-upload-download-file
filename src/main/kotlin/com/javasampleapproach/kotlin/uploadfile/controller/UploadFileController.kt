package com.javasampleapproach.kotlin.uploadfile.controller

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
 
import com.javasampleapproach.kotlin.uploadfile.filestorage.FileStorage;

@Controller
class UploadFileController {
	
	@Autowired
	lateinit var fileStorage: FileStorage
	
    @GetMapping("/")
    fun index(): String {
        return "multipartfile/uploadform.html"
    }
    
    @PostMapping("/")
    fun uploadMultipartFile(@RequestParam("uploadfile") file: MultipartFile, model: Model): String {
		fileStorage.store(file)
		model.addAttribute("message", "File uploaded successfully! -> filename = " + file.originalFilename)
        return "multipartfile/uploadform.html"
    }	
}