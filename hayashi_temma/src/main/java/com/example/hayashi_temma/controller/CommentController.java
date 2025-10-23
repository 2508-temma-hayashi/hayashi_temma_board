package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.controller.form.CommentForm;
import com.example.hayashi_temma.controller.form.PostForm;
import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.CommentService;
import com.example.hayashi_temma.service.DeleteCommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.example.hayashi_temma.controller.LoginController.getErrorMessages;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    DeleteCommentService deleteCommentService;

    @PostMapping("/comment")
    public ModelAndView saveComent(@ModelAttribute @Validated CommentForm form,
                                   BindingResult result,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        //resultからエラーメッセージを取得してリストの変数に入れる
        List<String> errorMessages = getErrorMessages(result);
        //セッションからログインしてる人のIDを取得する
        User user = (User) session.getAttribute("loginUser");
        int userId = user.getId();

        //アノテーションにエラーが引っかかってたら発動
        if (result.hasErrors()) {
            //Springが内部でセッションを1回だけ使う（自動削除付き）
            //リダイレクト先でもModelに自動で乗る
            //RedirectAttributesはSpringが用意しているリダイレクト専用の「Model」みたいな入れ物
            //FlashMap（フラッシュマップ） という一時ストレージに保存してくれる
            redirectAttributes.addFlashAttribute("errorMessages", getErrorMessages(result));
            redirectAttributes.addFlashAttribute("commentForm", form);
            return new ModelAndView("redirect:/home");
        }

        //サービスのメソッドを利用する
        commentService.saveComment(form, userId);
        //リダイレクトの場合はこう書く。
        //ModelAndView を使わない場合は、シンプルに return "redirect:/home"; でOK
        return new ModelAndView("redirect:/home");
    }
    //@RequestMapping("/comment")がくらすについてれば@PostMapping("/delete/{id}")相対パスの指定でもOK
    @PostMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") int commentId){

        deleteCommentService.deleteComment(commentId);
        //return "redirect:home";って書いてると相対パス扱いになる
        //RequestMappingしてたら相対パスでもいいけど、してないならダメ。
        return "redirect:/home";
    }
}
