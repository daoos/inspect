package cn.wanglin.inspect;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by wangl on 2018/01/30.
 */
public class InspectService {
    Logger logger = LoggerFactory.getLogger(getClass());

    ResultProcessor resultProcessor;
    ParamsService   paramsService;
    GroovyService   groovyService;
    ContextService  contextService;

    Object inspect(String eventCode, String eventSequenceNo, Map<String, Object> eventObject) throws TimeoutException {
        Object result  = null;
        String eventId = null;
        try {

            logger.info("收到请求{}:{},{},{}", eventId, eventCode, eventSequenceNo, JSON.toJSONString(eventObject));

            EventType type  = paramsService.getType(eventCode);
            Set<Rule> rules = paramsService.getRulesOfType(eventCode);
            assert type != null : "不存在的类型" + eventCode;
            if (rules.size() == 0) {
                result = resultProcessor.process(InnerResult.EMPTY);
                return result;
            }
            InsepectContext ctx = contextService.init(eventId, eventObject, type, rules);
            executeVarHandlers(ctx);

            result = resultProcessor.process(waitForResult(type.getTimeout()));
            return result;
        } finally {
            logger.info("请求{}检测结果:{}", eventId, JSON.toJSONString(result));
        }
    }

    private void executeVarHandlers(InsepectContext ctx) {
        ctx.getNextVars().forEach((var) -> {
            groovyService.getObject(var.getHandler(), VarHandler.class).fetch(var, ctx.getEventId(), ctx.getType(), ctx.getData());
        });
    }

    private InnerResult waitForResult(Integer timeout) throws TimeoutException {
        return null;
    }

    void varNotify(String eventId, String varName, Object varValue) {
        InsepectContext ctx = contextService.get(eventId);
        ctx.var(varName, varValue);
        ctx.getReadyRules().forEach(rule -> {
            try {
                Object ruleResult = EngineFactory.get(rule.getEngine()).execute(rule, ctx.toEngineContext());
                ctx.rule(rule, ruleResult);
                Task ruleTask = ctx.rule(rule);
                if (ruleTask.isFinished() && isReject(rule, ctx.rule(rule))) {
                    signal(eventId);
                }
            } catch (Exception e) {
                ctx.rule(rule, e);
            } finally {

            }
        });
    }


    private boolean isReject(Rule rule, Task task) {
        return RuleResultConverterFactory.get(rule.getResultWrapper()).convert(task.getResult()).isReject();
    }

    private void signal(String eventId) {

    }


}
