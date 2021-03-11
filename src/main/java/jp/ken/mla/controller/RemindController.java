package jp.ken.mla.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.mla.model.LoginModel;

@Controller
@RequestMapping("remind")
@SessionAttributes("loginModel")
public class RemindController {

	// 督促管理画面表示
	@RequestMapping(method=RequestMethod.GET)
	public String toAddMember(Model model, LoginModel lModel) {
		if(lModel.getMember_id() == 0) { // 未ログイン時はログイン画面にリダイレクト
			return "redirect:/login";
		}
		if(lModel.getAdmin() != 1) { // 管理ユーザ以外からのアクセスはエラー
			return IndexController.dispError(model, "管理者ユーザ以外はアクセス出来ません。");
		}
		IndexController.setActiveTab(model, "remind");
		return "index";
	}
}
