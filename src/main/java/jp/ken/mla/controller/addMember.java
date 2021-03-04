package jp.ken.mla.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.mla.db.MemberDb;
import jp.ken.mla.db.PayDb;
import jp.ken.mla.model.LoginModel;

@Controller
@RequestMapping("addMember")
@SessionAttributes("loginModel")
public class addMember {

	@Autowired
	private MemberDb memberDb;

	@Autowired
	private PayDb payDb;

	@ModelAttribute("loginModel")
	public LoginModel setUpLoginModel() {
		return new LoginModel();
	}

	// 新規会員登録画面遷移時
	@RequestMapping(method=RequestMethod.GET)
	public String toAddMember(Model model) {
		return setDispAddMember(model, "");
	}

	// 登録ボタン
	@RequestMapping(method=RequestMethod.POST)
	public String toTop(@ModelAttribute LoginModel lModel, Model model) {
		LoginModel chkEmail = memberDb.getByMail(lModel.getEmail());
		if(chkEmail.getMember_id() != 0) {
			return setDispAddMember(model, "既に登録済のEmailです。");
		}
		if(memberDb.insertMemberData(lModel)) {
			// IDやプラン取得する為、insertしたレコードを再取得する
			LoginModel reGetRec = memberDb.getByMail(lModel.getEmail());
			if(reGetRec.getMember_id() > 0 ) {
				BeanUtils.copyProperties(reGetRec, lModel);
				return "redirect:/index";
			} else {
				return setDispAddMember(model, "レコード再取得に失敗しました。");
			}
		} else {
			return setDispAddMember(model, "DB登録に失敗しました。");
		}
	}

	// -----------------------------
	// 内部処理メソッド
	// -----------------------------

	// 新規登録画面表示データ共通設定
	private String setDispAddMember(Model model, String errorMessage) {
		model.addAttribute("errorMessage", errorMessage);
		int[] icons = {1, 2, 3, 4, 5, 6, 7, 8};
		model.addAttribute("icons", icons);
		model.addAttribute("payList", payDb.allList());
		return "addMember";
	}
}
