package cn.rollin.passwordassistant.service;

import cn.rollin.passwordassistant.common.res.Response;
import cn.rollin.passwordassistant.pojo.dto.PasswordAddReq;
import cn.rollin.passwordassistant.pojo.dto.PasswordUpdateReq;
import cn.rollin.passwordassistant.pojo.vo.PasswordRecordVO;

import java.util.List;

public interface IPasswordRecordService {
    Response<Integer> addPasswordRecord(PasswordAddReq req, Integer userId);
    
    Response<Boolean> updatePasswordRecord(PasswordUpdateReq req, Integer userId);
    
    Response<List<PasswordRecordVO>> queryPasswordRecords(Integer id, Integer userId);

    /**
     * 删除密码记录
     *
     * @param id     记录ID
     * @param userId 用户ID
     * @return 删除结果
     */
    Response<Boolean> deletePasswordRecord(Integer id, Integer userId);
} 