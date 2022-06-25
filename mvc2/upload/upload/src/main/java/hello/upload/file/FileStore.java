package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName){
        return fileDir+fileName;
    }
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult=new ArrayList<>();
        for(MultipartFile multipartFile: multipartFiles){
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originFilename = multipartFile.getOriginalFilename();
        //서버에 저장하는 파일명
        String storeFileName = createStoreFileName(originFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originFilename,storeFileName);

    }
    private String createStoreFileName(String originFilename){
        String ext = extracted(originFilename);
        String uuid= UUID.randomUUID().toString();
        return uuid+"."+ext;
    }
    private  String extracted(String originFilename){
        int pos=originFilename.lastIndexOf(".");
        return originFilename.substring(pos+1);
    }
}
