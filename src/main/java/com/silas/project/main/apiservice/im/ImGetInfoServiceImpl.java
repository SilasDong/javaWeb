package com.silas.project.main.apiservice.im;

import com.silas.project.main.entity.SysMember;
import com.silas.project.main.mapper.MSysMemberMapper;
import com.silas.core.entity.CApiBase;
import com.silas.core.exception.ApiProcessException;
import com.silas.core.service.ParamContextHolder;
import com.silas.core.service.impl.XcdApiProcessService;
import com.silas.project.main.constants.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IM获取用户信息
 */
@Service("imGetInfoService")
public class ImGetInfoServiceImpl extends XcdApiProcessService {
	@Autowired
	private MSysMemberMapper memberMapper;

	@Override
	public Object apiProcess(ParamContextHolder paramContextHolder, CApiBase cApiBase) throws ApiProcessException {
		String accountType = paramContextHolder.getString("accountType");
		String id;// = paramContextHolder.getString("id");
		// 会员
		if("1".equals(accountType)){
			id = paramContextHolder.getString("memberId");
			SysMember member = memberMapper.getMemberInfoById(id);
			if(member==null){
			    throw new ApiProcessException(ErrorCode.MEMBER_ACCOUNT_EXIST);
			}
			return resultMap(member);
		}else{
			throw new ApiProcessException(ErrorCode.ACCOUNT_TYPE_ERROR);
		}
	}
}
