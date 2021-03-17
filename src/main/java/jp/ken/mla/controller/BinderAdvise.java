package jp.ken.mla.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
@Component
class BinderAdvise {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 日付文字列をDateにバインドする設定
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "can_rental_date", new CustomDateEditor(dateFormat, true));
    }
}
