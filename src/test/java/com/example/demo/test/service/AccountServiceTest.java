package com.example.demo.test.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.example.demo.Application;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AccountServiceTest {
	
	@Autowired private AccountService accountService;
	
	@Test
	public void findByAccountModel() {
		Account account = new Account();
		account.setUsername("admin");
		List<Account> list = this.accountService.findByAccountModel(account);
		assertEquals(1, list.size());
		System.out.println(JSON.toJSONString(list));
	}

}
