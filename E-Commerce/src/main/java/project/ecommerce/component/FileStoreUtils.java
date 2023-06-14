package project.ecommerce.component;


import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Component
public class FileStoreUtils {

    /**
     * This function will store the multipart file in server
     * and then it will return file path
     *
     * @param multipartFile
     * @return
     */
    public String saveMultipartFile(MultipartFile multipartFile) throws IOException, TikaException {


        String dirPath = System.getProperty("user.home") + "/Desktop/ECOMMERCE_FILE_STORE";
        File directoryFile = new File(dirPath);
        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String filePath = dirPath + "/" + originalFileName;
        File myFile = new File(filePath);


        try {
            multipartFile.transferTo(myFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;



    }
    public String extensionvalidation(MultipartFile multipartFile) throws IOException {
        Tika tika = new Tika();
        String type = tika.detect(multipartFile.getOriginalFilename());
        return type;
    }

    //convert image  into base64
    public String getBase64FormFilePath(String filePath) throws IOException {
        File file = new File(filePath);
        if(file.exists()){
            byte[] bytes = Files.readAllBytes(file.toPath());

            String base64Code = Base64.getEncoder().encodeToString(bytes);

            return "data:image/jpeg;base64,"+ base64Code;

        }else {
            return  null;
        }
    }

}
