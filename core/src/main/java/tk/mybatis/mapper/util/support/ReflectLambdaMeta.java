/*
 * Copyright (c) 2011-2024, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tk.mybatis.mapper.util.support;

import java.lang.invoke.SerializedLambda;

import org.slf4j.Logger;
import tk.mybatis.mapper.util.StringUtil;
import tk.mybatis.mapper.util.ClassUtils;

/**
 * Created by hcl at 2021/5/14
 */
public class ReflectLambdaMeta implements LambdaMeta {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReflectLambdaMeta.class);
    private final SerializedLambda lambda;

    private final ClassLoader classLoader;

    public ReflectLambdaMeta(SerializedLambda lambda, ClassLoader classLoader) {
        this.lambda = lambda;
        this.classLoader = classLoader;
    }

    @Override
    public String getImplMethodName() {
        return lambda.getImplMethodName();
    }

    @Override
    public Class<?> getInstantiatedClass() {
        String instantiatedMethodType = lambda.getInstantiatedMethodType();
        String instantiatedType = instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(StringUtil.SEMICOLON)).replace(StringUtil.SLASH, StringUtil.DOT);
        return ClassUtils.toClassConfident(instantiatedType, this.classLoader);
    }

}
