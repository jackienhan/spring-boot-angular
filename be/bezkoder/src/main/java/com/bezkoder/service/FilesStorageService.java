package com.bezkoder.service;

import com.bezkoder.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();

    public FileInfo store(MultipartFile file) throws IOException;
    public FileInfo getFile(String id);
    public Stream<FileInfo> getAllFiles();
}
