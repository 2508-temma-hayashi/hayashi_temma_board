package com.example.hayashi_temma.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 件名（タイトル）
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    // 本文
    @Column(name = "text", length = 1000, nullable = false)
    private String text;

    // カテゴリ
    @Column(name = "category", length = 10, nullable = false)
    private String category;

    // 投稿者（外部キー）
    //@ManyToOne	「多対一（Many-to-One）」の関係を定義
    //@JoinColumn(name = "user_id")	結合に使う外部キー列（FK）を指定
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 投稿日時
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // 投稿日時
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
