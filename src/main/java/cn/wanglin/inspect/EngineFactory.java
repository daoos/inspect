package cn.wanglin.inspect;

import cn.wanglin.inspect.el.ELEngine;

/**
 * Created by wangl on 2018/01/30.
 */
public class EngineFactory {
    static Engine el = new ELEngine();
    public static Engine get(EngineType engine) {
        if(engine ==EngineType.EL){
            return el;
        }
        return null;
    }
}
