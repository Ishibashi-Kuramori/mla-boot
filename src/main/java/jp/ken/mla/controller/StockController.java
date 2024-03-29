package jp.ken.mla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.mla.db.ItemDb;
import jp.ken.mla.db.MediaDb;
import jp.ken.mla.model.ItemModel;
import jp.ken.mla.model.LoginModel;

@Controller
@RequestMapping("stock")
@SessionAttributes("loginModel")
public class StockController {

	@Autowired
	private ItemDb itemDb;
	@Autowired
	private MediaDb mediaDb;

	// 在庫管理画面表示
	@RequestMapping(method=RequestMethod.GET)
	public String toAddMember(Model model, LoginModel lModel) {
		if(lModel.getMember_id() == 0) { // 未ログイン時はログイン画面にリダイレクト
			return "redirect:/login";
		}
		if(lModel.getAdmin() != 1) { // 管理ユーザ以外からのアクセスはエラー
			return IndexController.dispError(model, "管理者ユーザ以外はアクセス出来ません。");
		}
		model.addAttribute("itemList", itemDb.allList());
		IndexController.setActiveTab(model, "stock");
		return "index";
	}

	// 個人情報管理画面表示
	@RequestMapping(method=RequestMethod.POST)
	public String toModItem(@ModelAttribute LoginModel lModel, ItemModel iModel, Model model) {
		if(lModel.getMember_id() == 0) { // 未ログイン時はログイン画面にリダイレクト
			return "redirect:/login";
		}
		iModel = itemDb.getById(iModel.getItem_id());
		model.addAttribute("itemModel", iModel);
		IndexController.setActiveTab(model, "modItem");
		model.addAttribute("mediaList", mediaDb.allList());
		return "index";
	}

}
