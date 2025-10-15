package com.example.hayashi_temma.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {

    // 件名（30文字以内）
    private String title;

    // 本文（1000文字以内）
    private String text;

    // カテゴリ（10文字以内）
    private String category;
}