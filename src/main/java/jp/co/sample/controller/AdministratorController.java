package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;
/**
 * AdministratoryController.
 * @author ayaka.yamade
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	@RequestMapping("/insert")
	public String save(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form,administrator);
		administratorService.save(administrator);
		return "redirect:/";
	}
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/login")
	public String findByMailAddressAndPassword(LoginForm form, BindingResult result, Model model) {
		Administrator administrator = administratorService.findByMailAddressAndPassword(form.getMailAddress(),form.getPassword());
		if(administrator == null) {
			model.addAttribute("errors","メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}else {
			session.setAttribute("administratorName", administrator.getName());
		}
		return "forward:/employee/showList";
	}

}
