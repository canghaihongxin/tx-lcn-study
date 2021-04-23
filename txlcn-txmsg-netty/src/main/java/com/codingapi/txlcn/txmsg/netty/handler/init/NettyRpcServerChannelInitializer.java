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
package com.codingapi.txlcn.txmsg.netty.handler.init;

import com.codingapi.txlcn.txmsg.dto.ManagerProperties;
import com.codingapi.txlcn.txmsg.netty.handler.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Description:  Channel为Netty服务和服务之间创建连接的通道
 * 在channel通道中可以添加handler处理器，来具体实现业务操作   RpcAnswerHandler SocketManagerInitHandler都得处理器
 * 同时也需要在channel中配置各种其他的处理数据节点，如编码，解码 ObjectSerializerEncoder ObjectSerializerDecoder
 * Company: CodingApi
 * Date: 2018/12/10
 *
 * @author ujued  田培融
 */
@Component
public class NettyRpcServerChannelInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private RpcAnswerHandler rpcAnswerHandler;

    @Autowired
    private SocketManagerInitHandler socketManagerInitHandler;

    @Autowired
    private RpcCmdDecoder rpcCmdDecoder;


    private ManagerProperties managerProperties;

    public void setManagerProperties(ManagerProperties managerProperties) {
        this.managerProperties = managerProperties;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new LengthFieldPrepender(4, false));
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

        // 配置netty的心跳检查
        ch.pipeline().addLast(new IdleStateHandler(managerProperties.getCheckTime(),
                managerProperties.getCheckTime(), managerProperties.getCheckTime(), TimeUnit.MILLISECONDS));


        ch.pipeline().addLast(new ObjectSerializerEncoder());
        ch.pipeline().addLast(new ObjectSerializerDecoder());


        ch.pipeline().addLast(rpcCmdDecoder);
        ch.pipeline().addLast(new RpcCmdEncoder());
        ch.pipeline().addLast(socketManagerInitHandler);
        ch.pipeline().addLast(rpcAnswerHandler);
    }
}
