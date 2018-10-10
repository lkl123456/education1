package com.adks.dbclient.mysql;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:adksMultitenantMysql.xml"})
public class MultitenantMysqlClientTest {
	@Autowired
	private MultitenantMysqlClient multitenantMysqlClient;
	
	@Before
	public void before() {
		multitenantMysqlClient.setServiceDb("org01");
	}
	
	@Test
	public void testGen() {
		String sql = "select * from user limit 1";
		String targetSql = String.format(MultitenantMysqlClient.MLTITENANT_SQL_COMMENT, "org01") + sql;
		System.out.println(targetSql);
		System.out.println(multitenantMysqlClient.genMultitenantSql(sql));
		Assert.assertTrue(targetSql.equals(multitenantMysqlClient.genMultitenantSql(sql)));
	}
	@Test
	public void testQueryForInt(){
		String sql = "select count(*) from dept";
		Integer res = multitenantMysqlClient.queryforInt(sql,new Object[0]);
		System.out.println(res);
	}
}