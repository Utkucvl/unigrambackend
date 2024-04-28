package unigram.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unigram.demo.dao.entity.ImageData;
import unigram.demo.repository.ImageRepository;
import unigram.demo.util.ImageUtils;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl {
    @Autowired
    private ImageRepository imageRepository;
    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = imageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = imageRepository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
