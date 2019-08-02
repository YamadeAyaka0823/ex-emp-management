package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author rksuser
 *
 */
@Repository
public class EmployeeRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER =(rs,i)->{
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependent_count"));
		return employee;
	};
	/**
	 * 従業員一覧情報を入社日順で取得する.
	 * @return
	 */
	public List<Employee> findAll(){
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_ccount FROM employees ORDER BY hire_date";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
//		if(employeeList.size()==0) {
//			return null;
//		}else {
			return employeeList;
//		}
	}
	/**
	 * 主キーから従業員情報を取得する
	 * @param id
	 * @return
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}
	/**
	 * 従業員情報を変更する
	 * @param employee
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET dependents_count = :dependentsCount WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("dependentsCount", employee.getDependentsCount()).addValue("id",employee.getId());
		template.update(sql, param);
	}
	

}
