package org.example.postservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public File(String url, String fileName, String contentType, Long size, LocalDateTime uploadedAt, Post post) {
        this.url = url;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.uploadedAt = uploadedAt;
        this.post = post;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", post=" + post.getId() +
                '}';
    }
}
