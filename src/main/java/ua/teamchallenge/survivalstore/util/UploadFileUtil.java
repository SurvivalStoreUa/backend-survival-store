package ua.teamchallenge.survivalstore.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class UploadFileUtil {
    @Value("${upload.path}")
    private String uploadPath;
    private final Logger logger = LogManager.getLogger(UploadFileUtil.class);

    public String saveFile(MultipartFile multipartFile){
        logger.info("saveFile() - Saving file {}", multipartFile.getOriginalFilename());
        createDirectoryIfNotExist();;
        String uniqueName = null;
        try {
            uniqueName = saveFileToDirectory(multipartFile);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("saveFile() - File has been saved");
        return uniqueName;
    }
    private void createDirectoryIfNotExist(){
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }
    }
    private String saveFileToDirectory(MultipartFile multipartFile) throws IOException {
        String uuidFile = UUID.randomUUID().toString();
        String uniqueName = uuidFile+"_"+multipartFile.getOriginalFilename();
        Path path = Paths.get(uploadPath+"/"+uniqueName);
        multipartFile.transferTo(new File(path.toUri()));
        return uniqueName;
    }

}
