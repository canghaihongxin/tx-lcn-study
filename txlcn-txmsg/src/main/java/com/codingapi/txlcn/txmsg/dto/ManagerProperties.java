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
package com.codingapi.txlcn.txmsg.dto;

import lombok.Data;

/**
 * Description:   TM 管理器的配置文件
 * Company: CodingApi
 * Date: 2018/12/21
 *
 * @author codingapi  田培融
 */
@Data
public class ManagerProperties  {

    private String rpcHost;

    /**
     * 端口
     */
    private int rpcPort;

    /**
     * 心态检测时间(ms)  默认5分钟一次
     */
    private long checkTime;


    /**
     * 其他参数
     */
    private transient Object params;

}
