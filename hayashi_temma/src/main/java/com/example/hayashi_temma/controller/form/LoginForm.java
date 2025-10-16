package com.example.hayashi_temma.controller.form;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Getter
@Setter
public class LoginForm {
    //(?![\\s　]*$)で否定の先読みをしてその後に「何かしらの文字」が1文字以上 .+ 続いて、行末 $ で終わる。という正規表現
    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "アカウントを入力してください。")
    private String account;

    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "パスワードを入力してください。")
    private String password;
}
