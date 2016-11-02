package com.base.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.daoImpl.BaseInfoDaoImpl;
import com.base.daoImpl.LandApplyDaoImpl;
import com.base.daoImpl.LandInfoDaoImpl;
import com.base.daoImpl.LandLayoutDaoImpl;
import com.base.po.BaseInfo;
import com.base.po.LandApply;
import com.base.po.LandInfo;
import com.base.po.LandLayout;
import com.base.service.LandApplyService;

@Service("landApplyService")
public class LandApplyServiceImpl implements LandApplyService {
	
	@Autowired
	private LandApplyDaoImpl landApplyDaoImpl;
	@Autowired
	private BaseInfoDaoImpl baseInfoDaoImpl;
	@Autowired
	private LandLayoutDaoImpl landLayoutDaoImpl;
	@Autowired
	private LandInfoDaoImpl landInfoDaoImpl;

	//1.代表土地  2.代表校内  3.代表校外    
	@Override
	public List<BaseInfo> getBaseInfos(int baseType) {
		List<BaseInfo> list=baseInfoDaoImpl.getBaseInfos(baseType);
		return list;
	}

	@Override
	public List<LandLayout> getLandLayout(int bid) {
		List<LandLayout> list=landLayoutDaoImpl.getLayout(bid);
		return list;
	}

	@Override
	public LandInfo getLandInfo(int lid) {
		
		return landInfoDaoImpl.getLandInfo(lid);
	}

	@Override
	public void addLandApply(LandApply la) {
		landApplyDaoImpl.doLandApply(la);
         
	}

	//查询用户个人所有的申请记录
	@Override
	public List<LandApply> getUserApplys(String applicantId) {
		List<LandApply> list=landApplyDaoImpl.getUserApplys(applicantId);
		return list;
	}

	@Override
	public void updateUserApply(LandApply la) {
		landApplyDaoImpl.updateLandApply(la);

	}

	@Override
	public int getSpareValue(int lid) {
		// 保留
		return 0;
	}

	//7.失效(用户自行取消)
	@Override
	public void cancelApply(int la_id) {
		LandApply la=landApplyDaoImpl.getapply(la_id);
		la.setStatus(7);
		landApplyDaoImpl.updateLandApply(la);

	}

}
