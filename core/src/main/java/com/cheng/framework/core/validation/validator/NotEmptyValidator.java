package com.cheng.framework.core.validation.validator;

import com.cheng.framework.core.validation.NotEmpty;
import com.cheng.framework.core.util.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 非空注解验证器
 *
 * @author huangyong
 * @since 1.0.0
 */
public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {

    @Override
    public void initialize(NotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtil.isNotEmpty(value);
    }
}
