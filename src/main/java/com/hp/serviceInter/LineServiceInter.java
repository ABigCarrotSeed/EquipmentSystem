package com.hp.serviceInter;

import java.util.List;

import com.hp.domain.Users;

public interface LineServiceInter {

	public List<String> getLineNameByUser(Users user);
}
