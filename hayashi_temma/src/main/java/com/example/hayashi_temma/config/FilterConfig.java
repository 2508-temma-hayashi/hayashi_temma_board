package com.example.hayashi_temma.config;

import com.example.hayashi_temma.filter.AdministratorFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//このクラスはFilterが発動するリンクを指定するクラス
//これは「設定クラスだよ」とSpringに教える印
@Configuration
public class FilterConfig {

    //このメソッドの戻り値（FilterRegistrationBean）をSpringの「管理対象（Bean）」として保存してねって意味
    @Bean
    //戻り値のやつは「どのフィルターを、どのURLで動かすか」を定義する箱
    //引数はSpringが自動でくれる「AdministratorFilterの本体」。自分でnewしなくてOK。
    //Filter の情報を持つ箱を作成。
    //この箱にどのFilterを、どのURLにという情報をいれる。
    public FilterRegistrationBean<AdministratorFilter> adminFilter(AdministratorFilter filter) {
        FilterRegistrationBean<AdministratorFilter> bean = new FilterRegistrationBean<>();
        //どのFilterを登録するかをセット。
        bean.setFilter(filter);

        // URLを指定
        //「/user/list にアクセスが来たら AdministratorFilter を通してから Controller に渡してね」という指定
        bean.addUrlPatterns("/user/list","/user/register");
        // 複数指定したい場合は bean.addUrlPatterns("/user/*", "/admin/*"); みたいに書ける

        return bean;
    }
}

//“どのフィルターを使うか” や “どのURLで動かすか”“どのフィルターを使うか” や “どのURLで動かすか”
//リクエスト受けたらまずここが動く
