package cn.wanglin.inspect;

import groovy.lang.GroovyClassLoader;

/**
 * Created by wangl on 2018/01/30.
 */
public class GroovyService {
    static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    public <T> T getObject(String scriptName, Class<T> clz) {
        return null;
    }

    public static <T> T getObject(String scriptName, String content, Class<T> clz) throws Exception {
        Class  scriptClass = getGroovyClassLoader().parseClass(content, scriptName);
        Object obj         = scriptClass.newInstance();
        return (T) obj;
    }

    private synchronized static GroovyClassLoader getGroovyClassLoader() {
        if (groovyClassLoader == null)
            GroovyService.groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());

        return GroovyService.groovyClassLoader;
    }
}
