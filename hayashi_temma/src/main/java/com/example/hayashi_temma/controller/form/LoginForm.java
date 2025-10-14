package com.example.hayashi_temma.controller.form;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Getter
@Setter
public class LoginForm {

    @NotBlank(message = "アカウントを入力してください。")
    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "アカウントを入力してください")
    private String account;

    @NotBlank(message = "パスワードを入力してください。")
    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "パスワードを入力してください")
    private String password;
}
