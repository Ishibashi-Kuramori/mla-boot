package jp.ken.mla.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.ken.mla.model.LoginModel;

@Controller
@SessionAttributes("loginModel")
public class IndexController {

	private int member_id = 0;

	@ModelAttribute("loginModel")
	public LoginModel setUpLoginModel() {
		return new LoginModel();
	}

	// TOPページ
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String toTop(Model model, @ModelAttribute LoginModel lModel) {
		member_id = lModel.getMember_id();
		setActiveTab(model, "top");
//		model.addAttribute("rentalIds", rentalDAO.getByRentalItemIds(member_id));
		return "index";
	}

	// ログアウトボタン
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String runLogout(SessionStatus status) {
		status.setComplete();
		setUpLoginModel();
		return "redirect:/index";
	}

	public static void setActiveTab(Model model, String pName) {
/*
		SearchModel sModel = new SearchModel();
		model.addAttribute("searchModel", sModel);
		// Topページの商品情報各種をセット
		if(pName.equals("top")) {
			model.addAttribute("newList", itemDAO.top5List(0));
			model.addAttribute("oldList", itemDAO.top5List(1));
			model.addAttribute("allList", itemDAO.allList());
		}

		// 借りる／発送準備中ボタン用Modelをセット
		if(pName.equals("top") || pName.equals("rental")) {
			RentalModel rModel = new RentalModel();
			model.addAttribute("rentalModel", rModel);
		}
*/
		model.addAttribute("activeTop",       (pName.equals("top"))       ? "active" : "");
		model.addAttribute("activeModMember", (pName.equals("modMember")) ? "active" : "");
		model.addAttribute("activeRental",    (pName.equals("rental"))    ? "active" : "");
		model.addAttribute("activeHistory",   (pName.equals("history"))   ? "active" : "");
		model.addAttribute("activeStock",     (pName.equals("stock"))     ? "active" : "");
		model.addAttribute("activeRemind",    (pName.equals("remind"))    ? "active" : "");
		model.addAttribute("activeInfo",      (pName.equals("info"))      ? "active" : "");
		model.addAttribute("pName", pName);
	}

}