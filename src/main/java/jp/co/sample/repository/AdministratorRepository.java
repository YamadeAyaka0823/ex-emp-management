package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリ.
 * 
 * @author rksuser
 *
 */

@Repository
public class AdministratorRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	/**
	 * Administratorオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs,i)->{
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};
	
	/**
	 * 管理登録者情報を登録する.
	 * 
	 * @param administrator 管理者情報
	 */
	public void save(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
	    String sql = "INSERT INTO administrator(name,maill_address,password) VALUES(:name, :mailAddress, :password)";
	    template.update(sql, param);
	    		
	}
	/**
	 * メールアドレスとパスワードから管理者情報を取得する.
	 * 
	 * @param mailAddress　メールアドレス
	 * @param password　パスワード
	 * @return　管理者情報　(存在しない場合はnullを返す)
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
	    	 String sql = "SELECT id,name,mail_address,password FROM administrators WHERE mail_address = :mailAddress, password = :password";
	    	 SqlParameterSource param = new MapSqlParameterSource().addValue("mail_address",mailAddress).addValue("password", password);
	    	 List<Administrator> administratorList = template.query(sql,param,ADMINISTRATOR_ROW_MAPPER);
	    	 if(administratorList.size() == 0) {
	    		 return null;
	    	 }else {
	    		 
	    		return administratorList.get(0);
	    		 
	    	 }
	      
	}

}
