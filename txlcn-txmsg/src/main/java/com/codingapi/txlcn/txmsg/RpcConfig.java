/*
 * Copyright 2017-2019 CodingApi .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codingapi.txlcn.txmsg;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: RPC 服务的相关配置
 * Date: 19-1-9 下午6:04
 *
 * @author ujued  田培融
 */
@NoArgsConstructor
@Data
public class RpcConfig {
    /**
     * 最大等待时间 (ms)  如果小于5 默认设置为1000ms
     */
    private long waitTime = -1;

    /**
     * 最大缓存锁的数量
     */
    private int cacheSize = 1024;

    /**
     * appName 参数延迟删除时间(ms)  默认和分布式事务超时时间(ms)一样 TxManagerConfig.dtxTime
     */
    private long attrDelayTime = -1;

    /**
     * 断线重连次数
     */
    private int reconnectCount = 8;

    /**
     * 重连延迟时间(ms)
     */
    private long reconnectDelay = 6000;

}
