package com.example.iotrfidbe.service.backupdata;

import com.example.iotrfidbe.dto.backup.UsersBackupDTO;
import com.example.iotrfidbe.entity.backupdata.UsersBackup;
import com.example.iotrfidbe.repository.backupdata.UsersBackupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;
import java.nio.file.Files;
import java.util.ArrayList;

@Service
public class UsersBackupService {

    @Autowired
    private UsersBackupRepository usersBackupRepository;
    // Thư mục lưu ảnh
    private static final String IMAGE_DIR = "storage/image/backup/";

    public UsersBackup saveUser(Long id, String name, String base64) {
        try {
            File directory = new File(IMAGE_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // chuyển base64 thành ảnh
            if (base64.contains(",")) {
                base64 = base64.split(",")[1];
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64);
            String fileName = id + ".jpg";
            String filePath = IMAGE_DIR + fileName;
            // lưu ảnh
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(imageBytes);
            fos.close();
            System.out.println("Đã lưu ảnh: " + filePath);

            UsersBackup user = new UsersBackup();
            user.setId(id);
            user.setName(name);
            user.setImage_path(filePath);
            return usersBackupRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi lưu ảnh");
        }
    }

    public List<UsersBackupDTO> getAllUsersWithBase64() {
        List<UsersBackup> users = usersBackupRepository.findAll();
        List<UsersBackupDTO> result = new ArrayList<>();
        for (UsersBackup user : users) {
            try {
                File file = new File(user.getImage_path());
                byte[] imageBytes = Files.readAllBytes(file.toPath());
                String base64 = Base64.getEncoder().encodeToString(imageBytes);
                UsersBackupDTO dto = new UsersBackupDTO();
                dto.setId(user.getId());
                dto.setName(user.getName());
                dto.setBase64(base64);
                result.add(dto);
            } catch (Exception e) {
                System.out.println("Lỗi đọc ảnh: " + user.getImage_path());
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<UsersBackup> getAllUsers() {
        return usersBackupRepository.findAll();
    }

}