package vn.vdbc.core.service

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

import javax.sql.DataSource

@Component
class DatabaseService {
    private BeanFactory beanFactory;


    @Autowired
    public BeanFactoryDynamicAutowireService(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    List<GroovyRowResult> rows(String dsName, String sqlString, @Nullable Object... args){
        DataSource ds = beanFactory.getBean(dsName + "DataSource")
        def sql = new Sql(ds)
        println 'Query ['+sqlString+' - params: '+args+']';
        return sql.rows(sqlString, args)
    }

    List<GroovyRowResult> rows(String dsName, String sqlString, List params){
        DataSource ds = beanFactory.getBean(dsName + "DataSource")
        def sql = new Sql(ds)
        println 'Query ['+sqlString+' - params: '+params+']';
        return sql.rows(sqlString, params)
    }

    int executeUpdate(String dsName, String sqlString, @Nullable Object... args){
        DataSource ds = beanFactory.getBean(dsName + "DataSource")
        def sql = new Sql(ds)
        println 'Query ['+sqlString+' - params: '+args+']';
        return sql.executeUpdate(sqlString, args)
    }

    int executeUpdate(String dsName, String sqlString, List params){
        DataSource ds = beanFactory.getBean(dsName + "DataSource")
        def sql = new Sql(ds)
        println 'Query ['+sqlString+' - params: '+params+']';
        return sql.executeUpdate(sqlString, params)
    }
}
