package cn.wanglin.inspect;

/**
 * Created by wangl on 2018/01/30.
 */
public class Var {
    String handler;
    String name;
    Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
