package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.FileInfoDto;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "finproj", name = "files")
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "original_name")
    private String originalFileName;
    @Column(name = "storage_name")
    private String storageFileName;
    private String type;
    private String url;

    public static FileInfo fromFileInfoDto(FileInfoDto fileInfoDto) {
        return FileInfo.builder()
                .id(fileInfoDto.getId())
                .originalFileName(fileInfoDto.getOriginalFileName())
                .storageFileName(fileInfoDto.getStorageFileName())
                .type(fileInfoDto.getType())
                .url(fileInfoDto.getUrl())
                .build();
    }

    public static FileInfoDto toFileDto(FileInfo fileInfo) {
        return FileInfoDto.builder()
                .id(fileInfo.getId())
                .originalFileName(fileInfo.getOriginalFileName())
                .storageFileName(fileInfo.getStorageFileName())
                .type(fileInfo.getType())
                .url(fileInfo.getUrl())
                .build();
    }
}