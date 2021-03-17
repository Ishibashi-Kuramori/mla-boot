package jp.ken.mla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.mla.db.ItemDb;
import jp.ken.mla.model.ItemModel;
import jp.ken.mla.model.LoginModel;

@Controller
@RequestMapping("modItem")
@SessionAttributes("loginModel")
public class ModItemController {

	@Autowired
	private ItemDb itemDb;

	// 個人情報管理画面表示
	@RequestMapping(method=RequestMethod.POST)
	public String toModItem(@ModelAttribute LoginModel lModel, ItemModel iModel, Model model) {
		if(lModel.getMember_id() == 0) { // 未ログイン時はログイン画面にリダイレクト
			return "redirect:/login";
		}
		if(itemDb.updateItemData(iModel)) {
			return "redirect:stock";
		} else {
			return IndexController.dispError(model, "DB更新に失敗しました。");
		}
	}
}
