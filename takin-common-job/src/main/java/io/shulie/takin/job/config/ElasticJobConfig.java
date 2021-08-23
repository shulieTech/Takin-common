package io.shulie.takin.job.config;

import javax.sql.DataSource;

import io.shulie.takin.job.config.zk.ZkClientConfig;
import lombok.Data;

/**
 * @author 无涯
 * @Package io.hzq.job.elasticjob.config
 * @date 2021/6/13 10:14 下午
 */
@Data
public class ElasticJobConfig {
    /**
     * zk配置
     */
    private ZkClientConfig zkClientConfig;

    /**
     * 数据库配置
     */
    private DataSource dataSource;


}
