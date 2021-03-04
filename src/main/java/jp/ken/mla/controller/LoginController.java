package jp.ken.mla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.mla.db.MemberDb;
import jp.ken.mla.model.LoginModel;

@Controller
@RequestMapping("login")
@SessionAttributes("loginModel")
public class LoginController {

	@Autowired
	private MemberDb memberDb;

	@ModelAttribute("loginModel")
	public LoginModel setUpLoginModel() {
		return new LoginModel();
	}

	// ログイン画面遷移時
	@RequestMapping(method=RequestMethod.GET)
	public String toLogin() {
		return "login";
	}

	// ログインボタン
	@RequestMapping(method=RequestMethod.POST)
	public String toTop(@ModelAttribute LoginModel lModel, Model model) {
		lModel = memberDb.getByLogin(lModel);
		if(lModel.getMember_id() == 0) {
			model.addAttribute("errorMessage", "mailもしくはpasswordが間違っています。");
			return "login";
		} else {
			return "redirect:/index";
		}
	}

}
