package pl.myapplication.plot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.myapplication.plot.utils.JWTFilter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class PlotApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlotApplication.class, args);}


    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JWTFilter());
        List<String> list = new ArrayList<>();
                list.add("/user/auth/*");
                list.add("/productList/auth/*");
                list.add("/todoList/auth/*");
        filterRegistrationBean.setUrlPatterns(list);
        return filterRegistrationBean;
    }
}
