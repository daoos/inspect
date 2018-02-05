package cn.wanglin.inspect;

import java.util.Map;

/**
 * Created by wangl on 2018/01/30.
 */
public interface VarHandler {
    void fetch(Var var, String eventId, EventType type, Map<String, Object> eventObject);
}
