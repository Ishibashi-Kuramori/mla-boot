package jp.ken.mla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.mla.db.MemberDb;
import jp.ken.mla.db.PayDb;
import jp.ken.mla.db.PlanDb;
import jp.ken.mla.model.ListMemberModel;
import jp.ken.mla.model.LoginModel;
import jp.ken.mla.model.ModMemberModel;

@Controller
@RequestMapping("listMember")
@SessionAttributes("loginModel")
public class ListMemberController {

	@Autowired
	private MemberDb memberDb;
	@Autowired
	private PayDb payDb;
	@Autowired
	private PlanDb planDb;

	// 会員一覧画面表示
	@RequestMapping(method=RequestMethod.GET)
	public String toRental(Model model, LoginModel lModel) {
		if(lModel.getMember_id() == 0) { // 未ログイン時はログイン画面にリダイレクト
			return "redirect:/login";
		}
		if(lModel.getAdmin() != 1) { // 管理ユーザ以外からのアクセスはエラー
			return IndexController.dispError(model, "管理者ユーザ以外はアクセス出来ません。");
		}
		model.addAttribute("memberList", memberDb.allList());
		model.addAttribute("listMemberModel", new ListMemberModel());
		IndexController.setActiveTab(model, "listMember");
		return "index";
	}

	// 個人情報管理画面表示
	@RequestMapping(method=RequestMethod.POST)
	public String toAddMember(@ModelAttribute LoginModel lModel, ListMemberModel lmModel, Model model) {
		if(lModel.getMember_id() == 0) { // 未ログイン時はログイン画面にリダイレクト
			return "redirect:/login";
		}
		LoginModel getMember = memberDb.getById(lmModel.getMod_id());
		ModMemberModel newMmModel = new ModMemberModel();
		newMmModel.setMod(getMember);
		model.addAttribute("modMemberModel", newMmModel);
		IndexController.setActiveTab(model, "modMember");
		int[] icons = {1, 2, 3, 4, 5, 6, 7, 8};
		model.addAttribute("icons", icons);
		model.addAttribute("payList", payDb.allList());
		model.addAttribute("planList", planDb.allList());
		return "index";
	}

}
