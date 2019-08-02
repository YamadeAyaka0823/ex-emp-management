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

@Repository
public class AdministratorRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs,i)->{
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};
	
	public void save(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
	    String sql = "INSERT INTO administrator(name,maill_address,password) VALUES(:name, :mailAddress, :password)";
	    template.update(sql, param);
	    		
	}
	
	public List<Administrator> findByMailAddressAndPassword(String mailAddress, String password) {
	    	 String sql = "SELECT id,name,mail_address,password FROM administrators WHERE mail_address = :mailAddress, password = :password";
	    	 SqlParameterSource param = new MapSqlParameterSource().addValue("mail_address",mailAddress).addValue("password", password);
	    	 List<Administrator> administrator = template.query(sql,param,ADMINISTRATOR_ROW_MAPPER);
	    	 if(administrator == null) {
	    		 return null;
	    	 }else {
	    		 return administrator;
	    	 }
	      
	}

}
