package org.example.free_new_magazine.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.UUID;

@Service
public class StorageService {
    @Value( "${storage.root}")
    private  String ROOT ;

    public String save(MultipartFile file,String folder){

        if(file == null || file.isEmpty()){
            throw new IllegalArgumentException("File is empty");
        }
        try{
            String orginal = file.getOriginalFilename();
            if(orginal == null){
                orginal ="file";
            }
            String normalized = Normalizer.normalize(orginal, Normalizer.Form.NFKC);

            String safeName = normalized
                    .replaceAll("[#?&%]","_")
                    .replaceAll("[\\\\/:*?\"<>|]","_")
                    .replaceAll("\\s+","_");

            String fileName  = UUID.randomUUID() + "_" + safeName;

            Path dir = Paths.get(ROOT,folder);
            Files.createDirectories(dir);

            Path path = dir.resolve(fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return "/files/" + folder + "/" + fileName;

        } catch (Exception e) {
          throw new RuntimeException("File save error: " + e.getMessage() );
        }
    }
}
