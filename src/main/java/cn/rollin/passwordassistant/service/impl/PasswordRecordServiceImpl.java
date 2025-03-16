package cn.rollin.passwordassistant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.rollin.passwordassistant.common.exception.BizException;
import cn.rollin.passwordassistant.common.res.Response;
import cn.rollin.passwordassistant.mapper.PasswordRecordMapper;
import cn.rollin.passwordassistant.pojo.dto.PasswordAddReq;
import cn.rollin.passwordassistant.pojo.dto.PasswordUpdateReq;
import cn.rollin.passwordassistant.pojo.entity.PasswordRecord;
import cn.rollin.passwordassistant.pojo.vo.PasswordRecordVO;
import cn.rollin.passwordassistant.service.IPasswordRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.RECORD_NOT_FOUND;

@Service
public class PasswordRecordServiceImpl implements IPasswordRecordService {

    @Autowired
    private PasswordRecordMapper passwordRecordMapper;

    @Override
    public Response<Integer> addPasswordRecord(PasswordAddReq req, Integer userId) {
        PasswordRecord record = PasswordRecord.builder()
                .userId(userId)
                .platformName(req.getPlatformName())
                .platformIcon(req.getPlatformIcon())
                .description(req.getDescription())
                .password(req.getPassword())
                .build();
                
        passwordRecordMapper.insert(record);
        return Response.buildSuccess(record.getId());
    }

    @Override
    public Response<Boolean> updatePasswordRecord(PasswordUpdateReq req, Integer userId) {
        // 检查记录是否存在且属于当前用户
        PasswordRecord record = passwordRecordMapper.selectOne(
            new LambdaQueryWrapper<PasswordRecord>()
                .eq(PasswordRecord::getId, req.getId())
                .eq(PasswordRecord::getUserId, userId)
        );
        
        if (record == null) {
            throw new BizException(RECORD_NOT_FOUND);
        }
        
        // 更新记录
        record.setPlatformName(req.getPlatformName());
        record.setPlatformIcon(req.getPlatformIcon());
        record.setDescription(req.getDescription());
        record.setPassword(req.getPassword());
        
        int updated = passwordRecordMapper.updateById(record);
        return Response.buildSuccess(updated > 0);
    }

    @Override
    public Response<List<PasswordRecordVO>> queryPasswordRecords(Integer id, Integer userId) {
        // 构建查询条件
        LambdaQueryWrapper<PasswordRecord> wrapper = new LambdaQueryWrapper<PasswordRecord>()
                .eq(PasswordRecord::getUserId, userId)
                .eq(id != null, PasswordRecord::getId, id);
        
        // 查询记录
        List<PasswordRecord> records = passwordRecordMapper.selectList(wrapper);
        
        // 转换为VO
        List<PasswordRecordVO> voList = records.stream()
                .map(record -> {
                    PasswordRecordVO vo = new PasswordRecordVO();
                    BeanUtil.copyProperties(record, vo);
                    return vo;
                })
                .collect(Collectors.toList());
                
        return Response.buildSuccess(voList);
    }

    @Override
    public Response<Boolean> deletePasswordRecord(Integer id, Integer userId) {
        // 检查记录是否存在且属于当前用户
        PasswordRecord record = passwordRecordMapper.selectOne(
            new LambdaQueryWrapper<PasswordRecord>()
                .eq(PasswordRecord::getId, id)
                .eq(PasswordRecord::getUserId, userId)
        );
        
        if (record == null) {
            throw new BizException(RECORD_NOT_FOUND);
        }
        
        // 删除记录
        int deleted = passwordRecordMapper.deleteById(id);
        return Response.buildSuccess(deleted > 0);
    }
} 