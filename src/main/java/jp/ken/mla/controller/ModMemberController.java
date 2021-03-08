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
import jp.ken.mla.db.PlanDb;
import jp.ken.mla.model.LoginModel;
import jp.ken.mla.model.ModMemberModel;

@Controller
@RequestMapping("modMember")
@SessionAttributes("loginModel")
public class ModMemberController {

	@Autowired
	private MemberDb memberDb;
	@Autowired
	private PayDb payDb;
	@Autowired
	private PlanDb planDb;

	// 個人情報管理画面表示
	@RequestMapping(method=RequestMethod.GET)
	public String toAddMember(Model model, LoginModel lModel) {
		if(lModel.getMember_id() == 0) { // 未ログイン時はログイン画面にリダイレクト
			return "redirect:/login";
		}
		LoginModel newLModel = new LoginModel();
		BeanUtils.copyProperties(lModel, newLModel);
		ModMemberModel mmModel = new ModMemberModel();
		mmModel.setMod(newLModel);
		return setDispModMember(model, mmModel, "");
	}

	// 更新ボタン実行処理
	@RequestMapping(method=RequestMethod.POST, params="update")
	public String updateToTop(@ModelAttribute LoginModel lModel, ModMemberModel mmModel, Model model) {

		// 更新後の情報をLoginModelにセット
		LoginModel after = mmModel.getMod();
		// 一旦更新前の会員情報をDBから取得
		LoginModel befor = memberDb.getById(after.getMember_id());

		// メールアドレス変更時は重複チェック
		if(!befor.getEmail().equals(after.getEmail())) {
			LoginModel chkEmail = memberDb.getByMail(after.getEmail());
			if(chkEmail.getMember_id() > 0) {
				return setDispModMember(model, mmModel, "既に登録済のEmailです。");
			}
		}

		// プラン変更時はプラン情報をモデルにセットする
		if(befor.getPlan_id() != after.getPlan_id()) {
			after.setPlan(planDb.getById(after.getPlan_id()));
		}

		if(memberDb.updateMemberData(after)) {
			// 更新対象者とログインユーザが一致する場合はセッションを更新
			if(lModel.getMember_id() == after.getMember_id()) {
				BeanUtils.copyProperties(after, lModel);
			}
			if(lModel.getAdmin() == 1) {
				return "redirect:/listMember"; // 管理ユーザは会員一覧画面に戻る
			} else {
				return "redirect:/index"; // 会員はTOPページに戻る
			}
		} else {
			return setDispModMember(model, mmModel, "DB更新に失敗しました。");
		}

	}

	// 削除ボタン実行処理
	@RequestMapping(method=RequestMethod.POST, params="delete")
	public String deleteToTop(@ModelAttribute LoginModel lModel, ModMemberModel mmModel, Model model) {
		if(memberDb.deleteMemberData(lModel.getMember_id())) {
			return "redirect:/logout";
		} else {
			return setDispModMember(model, mmModel, "DB更新に失敗しました。");
		}
	}

	// -----------------------------
	// 内部処理メソッド
	// -----------------------------

	// 個人情報管理画面表示データ共通設定
	private String setDispModMember(Model model, ModMemberModel mmModel, String errorMessage) {
		model.addAttribute("modMemberModel", mmModel);
		model.addAttribute("errorMessage", errorMessage);
		IndexController.setActiveTab(model, "modMember");
		int[] icons = {1, 2, 3, 4, 5, 6, 7, 8};
		model.addAttribute("icons", icons);
		model.addAttribute("payList", payDb.allList());
		model.addAttribute("planList", planDb.allList());
		return "index";
	}
}
