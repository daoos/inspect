package cn.wanglin.inspect;

import java.util.Map;
import java.util.Set;

/**
 * Created by wangl on 2018/01/30.
 */
public interface Engine {
    Object execute(Rule rule, Map<String, Object> stringObjectMap);

    Set<String> analyse(String expression);
}
