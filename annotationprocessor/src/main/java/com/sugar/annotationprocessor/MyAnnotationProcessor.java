package com.sugar.annotationprocessor;

import com.google.auto.service.AutoService;
import com.google.auto.service.processor.AutoServiceProcessor;
import com.google.common.collect.ImmutableSet;

/**
 * @author huangxin
 * @date 2021/8/27
 */
@AutoService(AutoServiceProcessor.class)
public class MyAnnotationProcessor extends AutoServiceProcessor {
    @Override
    public ImmutableSet<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of();
    }
}
