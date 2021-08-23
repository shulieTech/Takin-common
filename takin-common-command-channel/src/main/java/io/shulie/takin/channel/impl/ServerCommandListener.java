package io.shulie.takin.channel.impl;

import io.shulie.takin.channel.CommandListener;
import io.shulie.takin.channel.bean.CommandPacket;

/**
 * @author: Hengyu
 * @className: ServerCommandListener
 * @date: 2020/12/30 8:44 上午
 * @description:
 */
public class ServerCommandListener  implements CommandListener {
    @Override
    public void call(CommandPacket packet) {
        System.out.println(packet.toString());
    }
}
