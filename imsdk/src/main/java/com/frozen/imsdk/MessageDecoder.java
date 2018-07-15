package com.frozen.imsdk;

import com.frozen.daily.imsdk.R;

import java.net.PasswordAuthentication;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageDecoder extends ChannelInboundHandlerAdapter {
    private static int PackSize = 4;
    private static int HeaderSize = 2;
    private static int VerSize = 2;
    private static int OperationSize = 4;
    private static int SeqIdSize = 4;
    private static int RawHeaderSize = PackSize + HeaderSize + VerSize + OperationSize + SeqIdSize;

//    private int packLen;
//    private int rawHeader;
//    private int version;
//    private int operation;
//    private int seqId;


    public static byte[] decode(ByteBuf buf) {
        ByteBuf rawHead = buf.readBytes(RawHeaderSize);
//        byte[] bytes = new byte[rawHead.readableBytes()];
//        rawHead.readBytes(bytes);

        byte[] packLen = new byte[PackSize];
        byte[] rawHeader = new byte[HeaderSize];
        byte[] version = new byte[VerSize];
        byte[] operation = new byte[OperationSize];
        byte[] seqId = new byte[SeqIdSize];

        rawHead.readBytes(packLen);
        rawHead.readBytes(rawHeader);
        rawHead.readBytes(version);
        rawHead.readBytes(operation);
        rawHead.readBytes(seqId);



        System.out.println(byteArrayToInt(packLen));
        System.out.println(byteArrayToInt(rawHeader));
        System.out.println(byteArrayToInt(version));
        System.out.println(byteArrayToInt(operation));
        System.out.println(byteArrayToInt(seqId));

//        ByteBuf bodyBuf = buf.readBytes();
        System.out.println(byteArrayToInt(packLen) - byteArrayToInt(rawHeader));
        byte[] bodyBytes = new byte[byteArrayToInt(packLen) - byteArrayToInt(rawHeader)];
        buf.readBytes(bodyBytes);

        System.out.println(new String(bodyBytes));


////        System.arraycopy(bytes, 0, packLen, 0, PackSize);
//        index += PackSize;
//        System.out.println(new String(bytes, index, HeaderSize));
//
////        System.arraycopy(bytes, PackSize, rawHeader, index, index + HeaderSize);
//        index += HeaderSize;
//        System.out.println(new String(bytes, index, VerSize));
////        System.arraycopy(bytes, 0, version, index, index + VerSize);
//        index += VerSize;
//        System.out.println(new String(bytes, index, OperationSize));
////        System.arraycopy(bytes, 0, operation, index, index + OperationSize);
//        index += OperationSize;
//        System.out.println(new String(bytes, index, SeqIdSize));
////        System.arraycopy(bytes, 0, seqId, index, index + SeqIdSize);
//        index += OperationSize;
//


        System.out.println(new String(packLen) + "\n"
                + new String(rawHeader) + "\n"
                + new String(version) + "\n"
                + new String(operation) + "\n"
                + new String(seqId));
//        byte[] detail = new byte[new string-rawHeader];
//        System.arraycopy(bytes,0,);


//        System.out.println(new String(bytes));
        return bodyBytes;
    }

    public static ByteBuf encode(String msg) {

        byte[] msgBytes = msg.getBytes();
//        (msgBytes.length+16);


        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(IntToByte4Array((msgBytes.length + 16)));

        buffer.writeBytes(IntToByte2Array(RawHeaderSize));

        buffer.writeBytes(IntToByte2Array(2));

        buffer.writeBytes(IntToByte4Array(4));

        buffer.writeBytes(IntToByte4Array(12));

        buffer.writeBytes(msgBytes);


        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    public static byte[] IntToByte4Array(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    public static byte[] IntToByte2Array(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);

        byte[] result = new byte[2];
        result[0] = src[2];
        result[1] = src[3];

        return result;
    }

    public static int byteArrayToInt(byte[] b) {
        int value= 0;
        for(int i=0; i<b.length; i++)
            value = (value << 8) | b[i];
        return value;

    }

}
