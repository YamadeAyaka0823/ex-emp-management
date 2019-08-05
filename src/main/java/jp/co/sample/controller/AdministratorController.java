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
 * 管理者情報を操作するコントローラ.
 * 
 * @author ayaka.yamade
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	/**
	 * 管理者情報を登録する.
	 * @return ログイン画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	/**
	 * 管理者情報を登録する.
	 * @param form リクエストパラメータが入ったフォーム
	 * @return ログイン画面
	 */
	@RequestMapping("/insert")
	public String save(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form,administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}
	
	/**
	 * ログインをする.
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	
	/**
	 * ログインをする.
	 * 
	 * @param form リクエストパラメータが入ったフォーム
	 * @param result　エラーメッセージが格納されるオブジェクト
	 * @param model　リクエストスコープ
	 * @return　従業員一覧画面　（ログイン失敗時はログイン画面に戻る)
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, BindingResult result, Model model) {
		Administrator administrator = administratorService.findByMailAddressAndPassword(form.getMailAddress(),form.getPassword());
		if(administrator == null) {
			model.addAttribute("errors","メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}else {
			session.setAttribute("administratorName", administrator.getName());
		}
		return "forward:/employee/showList";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

}
