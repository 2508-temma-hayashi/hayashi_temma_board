package com.example.hayashi_temma.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

    private String account;
    private String password;
    private String confirmPassword;

    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "氏名を入力してください。")
    @Size(max = 10, message = "氏名は10文字以内で入力してください")
    private String name;

    @NotNull(message = "支社を選択してください")
    private Integer branchId;

    @NotNull(message = "部署を選択してください")
    private Integer departmentId;


}