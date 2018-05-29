package com.mmall.netty;

import com.mmall.netty.protocol.MessageHolder;
import com.mmall.netty.protocol.ProtocolHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolEncoder extends MessageToByteEncoder<MessageHolder> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageHolder msg, ByteBuf out) throws Exception {
        String body = msg.getBody();

        if (body == null) {
            throw new Exception("body == null");
        }

        // 编码
        byte[] bytes = body.getBytes("utf-8");

        out.writeShort(ProtocolHeader.MAGIC)
                .writeByte(msg.getSign())
                .writeByte(msg.getType())
                .writeByte(msg.getStatus())
                .writeInt(bytes.length)
                .writeBytes(bytes);
    }
}
