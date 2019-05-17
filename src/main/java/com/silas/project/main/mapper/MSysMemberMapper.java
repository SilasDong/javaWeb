package com.silas.project.main.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.silas.project.main.entity.SysMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
  * 系统会员_会员基本信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-03-08
 */
@Mapper
public interface MSysMemberMapper extends BaseMapper<SysMember> {
	//IM 获取会员信息
	@Select("select t.id,ifnull(t.nickname,t.account) as nick,t.avatar,t.gender,t.birthDate from p_sys_member t where t.id = #{id}")
	SysMember getMemberInfoById(@Param("id")String id);
}