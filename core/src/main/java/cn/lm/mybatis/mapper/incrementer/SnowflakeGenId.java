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
package cn.lm.mybatis.mapper.incrementer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cn.lm.mybatis.mapper.genid.GenId;


/**
 * 默认生成器, 如果用无参构造器，则默认使用单例
 *
 * @author  Dawn Souther
 * @since 2024-12-16
 */
public class SnowflakeGenId implements GenId<Long> {

    static {
        InetAddress inetAddress;
        try {
            // 尝试取默认网卡，如果拿不到则会拿127.0.0.1
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            inetAddress = InetAddress.getLoopbackAddress();
        }
        INSTANCE = new SnowflakeGenId(inetAddress);
    }

    public static final SnowflakeGenId INSTANCE;

    private final Sequence sequence;

    private SnowflakeGenId(InetAddress inetAddress){
        sequence = new Sequence(inetAddress);
    }

    public Long nextId() {
        return sequence.nextId();
    }

    @Override
    public Long genId(String table, String column) {
        return INSTANCE.nextId();
    }
}
