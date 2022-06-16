package com.gilang.network.netty.ws;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.SocketAppLayerInvokerAdapter;
import com.gilang.network.netty.context.NettySessionContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gylang
 * data 2022/6/16
 */
@ChannelHandler.Sharable
public class WebSocketDispatcherInboundHandler extends SimpleChannelInboundHandler<SocketDataPackage<?>> implements AfterNetWorkContextInitialized {

    private SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    public WebSocketDispatcherInboundHandler(SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter) {
        this.socketAppLayerInvokerAdapter = socketAppLayerInvokerAdapter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SocketDataPackage<?> dataPackage) throws Exception {

        socketAppLayerInvokerAdapter.dispatch(dataPackage, new NettySessionContext(channelHandlerContext));
    }

    @Override
    public void post(ServerContext serverContext) {
        this.socketAppLayerInvokerAdapter = serverContext.getBeanFactoryContext().getPrimaryBean(SocketAppLayerInvokerAdapter.class);
    }
}
