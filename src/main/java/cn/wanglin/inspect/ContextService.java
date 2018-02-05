package cn.wanglin.inspect;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangl on 2018/01/30.
 */
public class ContextService {
    ParamsService paramsService;
    public InsepectContext init(String eventId, Map<String, Object> eventObject, EventType type, Set<Rule> rules) {
        InsepectContext ctx = new  InsepectContext();
        ctx.setEventId(eventId);
        ctx.setData(eventObject);
        ctx.setType(type);

        ctx.setRules(new HashMap<>());
        ctx.setVars(new HashMap<>());

        rules.forEach(rule->{
            ctx.getRules().put(rule,Task.initTask());
            Set<String> vars = EngineFactory.get(rule.getEngine()).analyse(rule.getExpression());
            if(null != vars ){
                vars.forEach(var->{
                    ctx.getVars().put(paramsService.getVar(var),Task.initTask());
                });
            }
        });

        return ctx;
    }

    public InsepectContext get(String eventId) {
        return null;
    }
}
