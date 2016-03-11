package com.cheng.framework.core.json;

import com.cheng.framework.core.util.StringUtil;
import com.fasterxml.jackson.core.JsonEncoding;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Desc:支持 JSONP
 * Author: Cheng
 * Date: 2016/3/10 0010
 */

/**
  $.ajax({
     type: <your type>,
     url: <your url>,
     dataType: 'jsonp',
     jsonpCallback: 'JsonpCallback', //这个值要与第一步的ConfigContainer.JSONP_CALLBACK同名
     contentType: 'application/jsonp;charset=UTF-8',
     }).done(function (result) {
     //TODO
     }).fail(function (result, textStatus, info) {
     //TODO
     });
  }
 */
public class JsonpHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private String callbackName;

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
//        JsonGenerator jsonGenerator = this.getObjectMapper().getFactory().createJsonGenerator(outputMessage.getBody(), encoding);
//        try {
//            //ConfigContainer.JSONP_CALLBACK 为回调名称，如"callback"
////            jsonGenerator.writeRaw(ConfigContainer.JSONP_CALLBACK);
//            jsonGenerator.writeRaw("callback");
//            jsonGenerator.writeRaw('(');
//            this.getObjectMapper().writeValue(jsonGenerator, object);
//            jsonGenerator.writeRaw(");");
//            jsonGenerator.flush();
//        } catch (JsonProcessingException ex) {
//            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
//        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String callbackParam = request.getParameter(callbackName);
        if(StringUtil.isEmpty(callbackParam)) {
            super.writeInternal(object, outputMessage);
        }else {
            JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
            try {
                String result = callbackParam + "(" + super.getObjectMapper().writeValueAsString(object) +")";
                IOUtils.write(result, outputMessage.getBody(), encoding.getJavaName());
            } catch (IOException e) {
                e.printStackTrace();
                throw new HttpMessageNotWritableException("Could not write JSON:" + e.getMessage(), e);
            }
        }
    }

    public void setCallbackName(String callbackName) {
        this.callbackName = callbackName;
    }
}
