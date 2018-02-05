package cn.wanglin.inspect;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangl on 2018/01/30.
 */

public class InsepectContext {
    String              eventId;
    EventType           type;
    Map<String, Object> data;
    Map<Rule, Task>     rules;
    Map<Var, Task>      vars;
    Integer             currentVarPriority;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<Rule, Task> getRules() {
        return rules;
    }

    public void setRules(Map<Rule, Task> rules) {
        this.rules = rules;
    }

    public Map<Var, Task> getVars() {
        return vars;
    }

    public void setVars(Map<Var, Task> vars) {
        this.vars = vars;
    }

    public Iterable<Var> getNextVars() {
        return vars.keySet().stream().filter(var -> null == var.getPriority() ||  var.priority > currentVarPriority).collect(Collectors.toSet());
    }

    public void var(String varName, Object varValue) {

    }

    public Iterable<Rule> getReadyRules() {
        return null;
    }

    public Map<String, Object> toEngineContext() {
        return null;
    }

    public void rule(Rule rule, Object ruleResult) {

    }

    public Object rule() {
        return null;
    }

    public Task rule(Rule rule) {
        return null;
    }
}
