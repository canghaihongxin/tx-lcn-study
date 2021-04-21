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
package com.codingapi.txlcn.common.runner;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: TxLcn Runner
 * Company: CodingApi
 * Date: 2019/1/16
 *
 * @author codingapi
 */
public class TxLcnApplicationRunner implements ApplicationRunner, DisposableBean {
    
    private final ApplicationContext applicationContext;
    
    private List<TxLcnInitializer> initializers;
    
    @Autowired
    public TxLcnApplicationRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    // 在spring 容器启动的时候需要执行的一些方法
    // 这里有点类似于观察者模式
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, TxLcnInitializer> runnerMap = applicationContext.getBeansOfType(TxLcnInitializer.class);
        initializers = runnerMap.values().stream().sorted(Comparator.comparing(TxLcnInitializer::order))
                .collect(Collectors.toList());

        System.out.println("TM 在启动时: "+initializers);

        // TM 启动时初始化的类  根据以下顺序查看源码
        // 1.com.codingapi.txlcn.tm.txmsg.TMRpcServer@57eed461,
        // 2.com.codingapi.txlcn.tm.txmsg.EnsureIdGenEngine@3b8d2abe,
        // 3.com.codingapi.txlcn.txmsg.netty.RpcNettyInitializer@7aa63f50,
        // 4. com.codingapi.txlcn.logger.helper.MysqlLoggerHelper@2dc73024,
        // 5.com.codingapi.txlcn.tm.cluster.TMAutoCluster@6d9428f3

        for (TxLcnInitializer txLcnInitializer : initializers) {
            txLcnInitializer.init();
        }
    }
    
    @Override
    public void destroy() throws Exception {
        for (TxLcnInitializer txLcnInitializer : initializers) {
            txLcnInitializer.destroy();
        }
    }
    
}
