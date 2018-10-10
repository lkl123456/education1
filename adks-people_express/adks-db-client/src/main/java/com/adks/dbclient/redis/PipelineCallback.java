package com.adks.dbclient.redis;

import redis.clients.jedis.Pipeline;

/**
 * 一次执行一组命令可以提高性能(比如批量插入删除), 注意不能在这个方法里面执行查询操作
 * @author panpanxu
 *
 */
public interface PipelineCallback {
	public void doInPipeline(Pipeline pipeline);
}
