package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author ayaka.yamade
 *
 */
@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	/**
	 * 管理登録者情報を登録する.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		administratorRepository.save(administrator);
	}
	
	/**
	 * メールアドレスとパスワードから管理者情報を取得する.
	 * 
	 * @param mailAddress　メールアドレス
	 * @param password　パスワード
	 * @return　管理者情報　(存在しない場合はnullを返す)
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		return administratorRepository.findByMailAddressAndPassword(mailAddress,password);
	}

}
