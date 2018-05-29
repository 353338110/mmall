package com.mmall.netty;

import com.mmall.netty.protocol.MessageHolder;
import com.mmall.netty.protocol.ProtocolHeader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 解码Handler.
 *
 *                                       Jelly Protocol
 *  __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __
 * |           |           |           |           |              |                          |
 *       2           1           1           1            4               Uncertainty
 * |__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __ __|__ __ __ __ __ __ __ __ __|
 * |           |           |           |           |              |                          |
 *     Magic        Sign        Type       Status     Body Length         Body Content
 * |__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __ __|__ __ __ __ __ __ __ __ __|
 *
 * 协议头9个字节定长
 *     Magic      // 数据包的验证位，short类型
 *     Sign       // 消息标志，请求／响应／通知，byte类型
 *     Type       // 消息类型，登录／发送消息等，byte类型
 *     Status     // 响应状态，成功／失败，byte类型
 *     BodyLength // 协议体长度，int类型
 *
 *
 */
public class ProtocolDecoder extends ByteToMessageDecoder {
    private static final Logger logger = Logger.getLogger(ProtocolDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < ProtocolHeader.HEADER_LENGTH) {
            // 数据包长度小于协议头长度
            logger.info("数据包长度小于协议头长度");
            return;
        }
        in.markReaderIndex();

        if (in.readShort() != ProtocolHeader.MAGIC) {
            // Magic不一致，表明不是自己的数据
            logger.info("Magic不一致");
            in.resetReaderIndex();
            return;
        }

        // 开始解码
        byte sign = in.readByte();
        byte type = in.readByte();
        byte status = in.readByte();

        // 确认消息体长度
        int bodyLength = in.readInt();
        if (in.readableBytes() != bodyLength) {
            // 消息体长度不一致
            logger.info("消息体长度不一致");
            in.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[bodyLength];
        in.readBytes(bytes);

        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setSign(sign);
        messageHolder.setType(type);
        messageHolder.setStatus(status);
        messageHolder.setBody(new String(bytes, "utf-8"));

        out.add(messageHolder);
    }

   /* private void writeByte(){
        MessageHolder msg = new MessageHolder();
        msg.setSign(ProtocolHeader.RESPONSE);
        msg.setStatus(ProtocolHeader.SUCCESS);
        msg.setType(ProtocolHeader.LOGIN);
        msg.setBody("好啊");
        String body = msg.getBody();
        // 编码
        byte[] bytes = new byte[0];
        try {
            bytes = body.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ByteBuf out = Unpooled.buffer(64);
        out.writeShort(ProtocolHeader.MAGIC)
                .writeByte(msg.getSign())
                .writeByte(msg.getType())
                .writeByte(msg.getStatus())
                .writeInt(bytes.length)
                .writeBytes(bytes);
        logger.info(out);

    }*/
}
