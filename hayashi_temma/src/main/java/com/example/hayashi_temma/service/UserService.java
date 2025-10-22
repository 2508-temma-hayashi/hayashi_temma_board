package com.example.hayashi_temma.service;

import com.example.hayashi_temma.controller.form.UserForm;
import com.example.hayashi_temma.repository.UserRepository;
import com.example.hayashi_temma.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAllUsersWithBranchAndDepartment();
    }

    //ユーザーの稼働ステータス更新メソッド
    public void updatedStatus(int id) {

        User user = userRepository.findById(id).orElse(null);
        int status = user.getIsStopped();
        if (status == 0) {
            user.setIsStopped(1);
        }else if (status == 1) {
            user.setIsStopped(0);
        }

        userRepository.save(user);
    }

    //バリデーションメソッド（登録用）
    public List<String> validate(UserForm form){
        List<String> errorMessages = new ArrayList<>();
        String account = form.getAccount();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        Integer branchId = form.getBranchId();
        Integer departmentId = form.getDepartmentId();

        // 必須チェック
        if (account == null || account.trim().isEmpty()) {
            errorMessages.add("アカウントを入力してください");
        } else if (!account.matches("^[a-zA-Z0-9]{6,20}$")) {
            errorMessages.add("アカウントは半角英数字かつ6文字以上20文字以下で入力してください");
        }

        if (password == null || password.trim().isEmpty()) {
            errorMessages.add("パスワードを入力してください");
        } else if (!password.matches("^[a-zA-Z0-9]{6,20}$")) {
            errorMessages.add("パスワードは半角英数字かつ6文字以上20文字以下で入力してください");
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            errorMessages.add("確認用パスワードを入力してください");
        } else if (!confirmPassword.matches("^[a-zA-Z0-9]{6,20}$")) {
            errorMessages.add("確認用パスワードは半角英数字かつ6文字以上20文字以下で入力してください");
        }


        // アカウント重複チェック
        if (userRepository.findByAccount(account) != null) {
            errorMessages.add("アカウントが既に使用されています");
        }
        //一致してるか確認
        if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
            errorMessages.add("パスワードと確認用パスワードが一致しません");
        }

        //支社と部署の組み合わせが正しいか確認
        if (branchId != null && departmentId != null) {
            boolean invalid = false;

            // 本社(1): 営業部(1)、技術部(2)、総務人事部(3)
            // 大阪支社(2): 営業部(1)、技術部(2)
            // 福岡支社(3): 営業部(1)、技術部(2)
            switch (branchId) {
                case 1 -> invalid = (departmentId == 1 || departmentId == 2 || departmentId == 3);
                case 2, 3 -> invalid = (departmentId == 1 || departmentId == 2);
                default -> invalid = true;
            }

            if (!invalid) {
                errorMessages.add("支社と部署の組み合わせが不正です");
            }
        }
        return errorMessages;
    }


    //ユーザーの新規登録メソッド（formをentityに変換してそれを使って追加。）
    public void registerUser(UserForm form){
        User user = new User();
        user.setAccount(form.getAccount());
        user.setPassword(form.getPassword());
        user.setName(form.getName());
        user.setIsStopped(0);
        user.setBranchId(form.getBranchId());
        user.setDepartmentId(form.getDepartmentId());
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
    }

    //1件ユーザーのIDを使ってユーザーの情報を取得するメソッド
    public UserForm pickUp(int id){
        User user = new User();
        UserForm form = new UserForm();
        //←findById() は「そのIDのデータが存在するかもしれない／しないかもしれない」前提なので、戻り値が Optional<User> になっている。
        user = userRepository.findById(id).orElse(null);

        //formに取得したentityを詰める（コントローラーの処理を軽くするために）
        form.setId(user.getId());
        form.setAccount(user.getAccount());
        form.setName(user.getName());
        form.setBranchId(user.getBranchId());
        form.setDepartmentId(user.getDepartmentId());

        return form;
    }

    //バリデーションメソッド（更新用）
    public List<String> editValidate(UserForm form){
        List<String> errorMessages = new ArrayList<>();
        Integer id = form.getId();
        String account = form.getAccount();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        Integer branchId = form.getBranchId();
        Integer departmentId = form.getDepartmentId();

        // 必須チェック
        if (account == null || account.trim().isEmpty()) {
            errorMessages.add("アカウントを入力してください");
        } else if (!account.matches("^[a-zA-Z0-9]{6,20}$")) {
            errorMessages.add("アカウントは半角英数字かつ6文字以上20文字以下で入力してください");
        }

        if ((password != null && !password.trim().isEmpty())
                &&(!password.matches("^[a-zA-Z0-9]{6,20}$"))){
            errorMessages.add("パスワードは半角英数字かつ6文字以上20文字以下で入力してください");
        }

        if ((confirmPassword != null && !confirmPassword.trim().isEmpty())
                &&(!confirmPassword.matches("^[a-zA-Z0-9]{6,20}$"))){
            errorMessages.add("確認用パスワードは半角英数字かつ6文字以上20文字以下で入力してください");
        }


        // アカウント重複チェック
        User duplicate = userRepository.findByAccount(form.getAccount());
        if (duplicate != null && !duplicate.getId().equals(form.getId())) {
            errorMessages.add("アカウントが既に使用されています");
        }
        //一致してるか確認
        if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
            errorMessages.add("パスワードと確認用パスワードが一致しません");
        }

        //支社と部署の組み合わせが正しいか確認
        if (branchId != null && departmentId != null) {
            boolean invalid = false;

            // 本社(1): 営業部(1)、技術部(2)、総務人事部(3)
            // 大阪支社(2): 営業部(1)、技術部(2)
            // 福岡支社(3): 営業部(1)、技術部(2)
            switch (branchId) {
                case 1 -> invalid = (departmentId == 1 || departmentId == 2 || departmentId == 3);
                case 2, 3 -> invalid = (departmentId == 1 || departmentId == 2);
                default -> invalid = true;
            }

            if (!invalid) {
                errorMessages.add("支社と部署の組み合わせが不正です");
            }
        }
        return errorMessages;
    }

    // 更新登録メソッド
    public void updateUser(UserForm form) {
        // DBから既存ユーザーを取得
        User user = userRepository.findById(form.getId()).orElse(null);

        // 共通項目を上書き
        user.setAccount(form.getAccount());
        user.setName(form.getName());
        user.setBranchId(form.getBranchId());
        user.setDepartmentId(form.getDepartmentId());

        // パスワード欄に入力がある場合のみ更新
        if (form.getPassword() != null && !form.getPassword().trim().isEmpty()) {
            user.setPassword(form.getPassword());
        }

        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
    }



}



