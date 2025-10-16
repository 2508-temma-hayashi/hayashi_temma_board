package com.example.hayashi_temma.controller.form;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    private Integer messageId;

    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "メッセージを入力してください")
    @Size(max = 500, message = "500文字以内で入力してください")
    private String text;

}
