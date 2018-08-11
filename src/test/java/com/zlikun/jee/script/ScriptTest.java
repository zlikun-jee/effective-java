package com.zlikun.jee.script;

import org.junit.Test;

import javax.script.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.junit.Assert.assertEquals;

/**
 * Java中执行脚本，这里以javascript为例
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/11 9:49
 */
public class ScriptTest {

    @Test
    public void test() throws ScriptException, IOException, NoSuchMethodException {

        // 脚本引擎管理器
        ScriptEngineManager manager = new ScriptEngineManager();
        // 根据扩展名获取脚本引擎，Java默认对javascript提供支持
        ScriptEngine engine = manager.getEngineByExtension("js");
        // 创建一个绑定对象，用于向js脚本注入变量
        Bindings bindings = engine.createBindings();
        bindings.put("factor", 1);
        // 脚本引擎绑定Bindings对象，并设定作用范围为引擎范围
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        Object value = null;
        try (Reader reader = new FileReader("src/test/java/com/zlikun/jee/script/sample.js")) {
            // 执行javascript代码
            engine.eval(reader, bindings);
            // 判断是否可调用方法
            if (engine instanceof Invocable) {
                // 调用函数，传入两个参数
                value = ((Invocable) engine).invokeFunction("formula", 4, 3);
            }
        }

        // 验证执行结果
        assertEquals(7.0, value);
    }

}
