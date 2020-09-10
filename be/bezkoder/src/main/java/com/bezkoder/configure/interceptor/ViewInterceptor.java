package com.bezkoder.configure.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewInterceptor extends HandlerInterceptorAdapter {

  private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptorAdapter.class);

  @Autowired
  private MappingJackson2HttpMessageConverter jsonConverter;


  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                              Object handler, @Nullable Exception ex) throws Exception {
    logger.info("Reset JSONView");
    var viewConfig = this.jsonConverter.getObjectMapper().getSerializationConfig();
    this.jsonConverter.getObjectMapper().setConfig(viewConfig.withView(null));
  }
}
