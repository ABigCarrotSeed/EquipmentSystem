package com.hp.serviceInter;

import java.util.List;

import com.hp.domain.MaintainItems;

public interface MaintainItemsServiceInter {

	public List<MaintainItems> getMaintainItemsByEid(String eId,String dateType);
}
