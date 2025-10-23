package com.example.hayashi_temma.config;

import com.example.hayashi_temma.filter.AdministratorFilter;
import com.example.hayashi_temma.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//このクラスはFilterが発動するリンクを指定するクラス
//これは「設定クラスだよ」とSpringに教える印

@Configuration
public class FilterConfig {


    //URLの指定があるので@Bean
    @Bean
    //このメソッドでDIされたfilterを引数に渡す
    //もしLoginFilterクラスに＠Componentついてなかったら、beanにセットする前にnewして引数は空。
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();
        //SpringがDIしてくれたフィルタークラスのオブジェクト
        //URL先
        //優先順
        //をそれぞれセットしてSPRINGに渡す;
        bean.addUrlPatterns("/user*", "/comment*","/message*", "/home", "/logout", "/post");
        bean.setFilter(new LoginFilter());
        bean.setOrder(1); // 先に動く（ログインチェック）
        return bean;
    }


    //このメソッドの戻り値（FilterRegistrationBean）をSpringの「管理対象（Bean）」として保存してねって意味
    //@Bean
    //戻り値のやつは「どのフィルターを、どのURLで動かすか」を定義する箱
    //引数はSpringが自動でくれる「AdministratorFilterの本体」。自分でnewしなくてOK。
    //SPRING箱にどのFilterを、どのURLに～という情報をいれる。
    @Bean
    public FilterRegistrationBean<AdministratorFilter> administratorFilter() {
        FilterRegistrationBean<AdministratorFilter> bean = new FilterRegistrationBean<>();
        //どのFilterを登録するかをセット。
        bean.setFilter(new AdministratorFilter());
        // URLを指定
        //「/user/list にアクセスが来たら AdministratorFilter を通してから Controller に渡してね」という指定
        bean.addUrlPatterns("/user/*");
        // 複数指定したい場合は bean.addUrlPatterns("/user/*", "/admin/*"); みたいに書ける

        return bean;
    }
}

//“どのフィルターを使うか” や “どのURLで動かすか”“どのフィルターを使うか” や “どのURLで動かすか”
//リクエスト受けたらまずここが動く
