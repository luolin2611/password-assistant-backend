package cn.rollin.passwordassistant.mapper;

import cn.rollin.passwordassistant.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author rollin
 * @since 2024-12-27 23:53:06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
