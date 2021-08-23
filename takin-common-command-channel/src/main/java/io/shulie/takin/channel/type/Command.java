package io.shulie.takin.channel.type;

/**
 * @author: Hengyu
 * @className: Command
 * @date: 2020/12/29 10:20 下午
 * @description: 命令对象
 */
public class Command {

    private String id;
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}