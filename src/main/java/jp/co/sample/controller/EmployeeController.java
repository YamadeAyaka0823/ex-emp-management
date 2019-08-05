package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;
/**
 * 従業員関連機能の処理の制御を行うコントローラ.
 * 
 * @author ayaka.yamade
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	private UpdateEmployeeForm setUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	/**
	 * 従業員情報の一覧を表示する.
	 * @param model リクエストスコープ
	 * @return　従業員情報の一覧
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	/**
	 * 従業員情報.
	 * @param id ID
	 * @param model リクエストスコープ
	 * @return　従業員情報
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		int id2 = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(id2);
		model.addAttribute("employee",employee);
		return "employee/detail";
	}
	
	/**
	 * 従業員詳細を更新する
	 * @param form 従業員IDと扶養人数をコピー
	 * @return 従業員詳細を更新(扶養人数のみ)
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		System.out.println(form);
		Employee employee = new Employee();
//		BeanUtils.copyProperties(form, employee);
		int id = form.getIntegerId();
		int dependentsCount = form.getIntegerDependentsCount();
		employee.setId(id);
		employee.setDependentsCount(dependentsCount);
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

}
