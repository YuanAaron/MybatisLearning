package cn.coderap.config;

import cn.coderap.utils.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yw
 * 2021/1/11
 */
@Data
@AllArgsConstructor
public class BoundSql {

    //解析过后的sql
    private String sqlText;

    //#{}里面的参数名称
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();
}
