package jp.ken.mla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.mla.db.RentalDb;
import jp.ken.mla.model.LoginModel;
import jp.ken.mla.model.RentalHopeModel;
import jp.ken.mla.model.RentalModel;

@Controller
@RequestMapping("rental")
@SessionAttributes("loginModel")
public class RentalController {

	@Autowired
	private RentalDb rentalDb;

	private int member_id = 0;

	// レンタル管理画面表示
	@RequestMapping(method=RequestMethod.GET)
	public String toRental(Model model, LoginModel lModel) {
		if(lModel.getMember_id() == 0) { // 未ログイン時はログイン画面にリダイレクト
			return "redirect:/login";
		}
		member_id = lModel.getMember_id();
		model.addAttribute("rentalList", rentalDb.getByMemberId(member_id));
		model.addAttribute("rentalIds", rentalDb.getByRentalItemIds(member_id));
		model.addAttribute("rentalHopeModel", new RentalHopeModel());
		IndexController.setActiveTab(model, "rental");
		return "index";
	}

	// 商品の借りるボタン
	@RequestMapping(method=RequestMethod.POST, params="rentalAdd")
	public String rentalAddBtn(@ModelAttribute RentalModel rModel, Model model) {
		if(!rentalDb.insertRentalData(rModel)) {
			return IndexController.dispError(model, "DB更新に失敗しました。");
		}
		return "redirect:/index";
	}

	// 商品の発送準備中(キャンセル)ボタン
	@RequestMapping(method=RequestMethod.POST, params="rentalDel")
	public String rentalDelBtn(@ModelAttribute RentalModel rModel, Model model) {
		if(!rentalDb.deleteRentalData(rModel)) {
			return IndexController.dispError(model, "DB更新に失敗しました。");
		}
		return "redirect:/rental";
	}

	// 商品レコードドラッグ＆ドロップ時
	@RequestMapping(method=RequestMethod.POST, params="rental_hope")
	public String rentalRecChange(@ModelAttribute RentalHopeModel rhModel, Model model) {
		if(!rentalDb.updateRentalHope(rhModel.getRental_hope())) {
			return IndexController.dispError(model, "DB更新に失敗しました。");
		}
		return "redirect:/rental";
	}
}
