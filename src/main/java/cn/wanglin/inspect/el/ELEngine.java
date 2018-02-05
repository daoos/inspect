package cn.wanglin.inspect.el;

import cn.wanglin.inspect.Engine;
import cn.wanglin.inspect.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ELEngine implements Engine {
    @Override
    public Object execute(Rule rule, Map<String, Object> stringObjectMap) {
        return null;
    }

    @Override
    public Set<String> analyse(String expression) {
        Set<String> ruleVars = new HashSet<>();
        List<Token> tokens   = new Tokenizer(expression).process();
        tokens.forEach(token -> {
            if (token.isRootIdentifier()) {
                ruleVars.add(token.data);

            }
        });
        return ruleVars;
    }
}
