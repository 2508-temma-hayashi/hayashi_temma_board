package com.example.hayashi_temma.controller.form;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {

    // 件名（30文字以内）
    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "件名を入力してください。")
    @Size(max = 30, message = "件名は30文字以内で入力してください")
    private String title;

    // 本文（1000文字以内）
    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "本文を入力してください。")
    @Size(max = 1000, message = "本文は1000文字以内で入力してください")
    private String text;

    // カテゴリ（10文字以内）
    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "カテゴリを入力してください。")
    @Size(max = 10, message = "カテゴリは10文字以内で入力してください")
    private String category;
}