package io.shulie.takin.definition.command.zk;

/**
 * Agent 和控制台交互的时候，下发各种命令的时候，这是命令的路径
 *
 * @author shiyajian
 * create: 2020-12-09
 */
public final class takinZkCommandConstants {

    private takinZkCommandConstants() { /* no instance */ }

    public static final String NAME_SPACE = "/takin/command";

    /**
     * dump 内存文件
     */
    public static final String DUMP_MEMORY_FILE_PATH = "/dumpMemory/file/path";

    /**
     * 方法追踪交互zk节点
     */
    public static final String TRACE_MANAGE_DEPLOY_PATH = "/trace/sample";

}
