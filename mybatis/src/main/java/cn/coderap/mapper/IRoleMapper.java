package cn.coderap.mapper;

import cn.coderap.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IRoleMapper {

    @Select("select *,role_name roleName from role r inner join person_role pr on r.id = pr.role_id where pr.person_id=#{id}")
    public List<Role> findRoleByPersonId(Integer id);
}
