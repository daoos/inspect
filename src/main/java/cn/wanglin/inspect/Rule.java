package cn.wanglin.inspect;

/**
 * Created by wangl on 2018/01/30.
 */
public class Rule {
    EngineType engine;
    String     expression;
    String     resultWrapper;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResultWrapper() {
        return resultWrapper;
    }

    public void setResultWrapper(String resultWrapper) {
        this.resultWrapper = resultWrapper;
    }

    public EngineType getEngine() {
        return engine;
    }

    public void setEngine(EngineType engine) {
        this.engine = engine;
    }
}
