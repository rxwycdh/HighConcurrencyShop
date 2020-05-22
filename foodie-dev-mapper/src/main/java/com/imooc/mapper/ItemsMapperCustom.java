package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @FileName ItemsMapperCustom
 * @Description
 * @Author jiuhao
 * @Date 2020/5/21 15:25
 * @Modified
 * @Version 1.0
 */
public interface ItemsMapperCustom {

    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);
}
