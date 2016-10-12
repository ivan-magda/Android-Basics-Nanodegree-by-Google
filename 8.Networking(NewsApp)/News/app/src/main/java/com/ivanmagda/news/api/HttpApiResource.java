package com.ivanmagda.news.api;

import java.util.HashMap;

public interface HttpApiResource {

    public String url();

    public String httpMethod();

    public HashMap<String, Object> methodParameters();

}
