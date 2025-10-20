package com.example.hayashi_temma.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "branches")
public class Branch {
    //主キー
    @Id
    //「この主キーはデータベース側で自動採番される（AUTO_INCREMENT）」という意味
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 支社名（カラム名は name ）
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "created_date", nullable = false, insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", nullable = false, insertable = false, updatable = false)
    private Timestamp updatedDate;
}
