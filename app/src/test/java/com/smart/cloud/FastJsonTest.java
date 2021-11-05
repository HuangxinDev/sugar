package com.smart.cloud;

import com.alibaba.fastjson.JSONObject;

import junit.framework.TestCase;

/**
 * fastjson测试
 *
 * @author huangxin
 * @date 2021/8/13
 */
public class FastJsonTest extends TestCase {
    private String json;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        json = "\\{user:11\\}";
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testParseJson() {
        JSONObject object = JSONObject.parseObject(json);
        assertEquals(object.getString("user"), "11");
    }

}
