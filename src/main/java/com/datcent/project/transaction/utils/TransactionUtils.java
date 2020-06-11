package com.datcent.project.transaction.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@Component
public class TransactionUtils {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * begin  事务
     * @return
     */
    public  TransactionStatus begin(){
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transaction;
    }

    /**
     * commit 提交事务
     * @param transaction
     */
    public void commit(TransactionStatus transaction){

        dataSourceTransactionManager.commit(transaction);
    }

    /**
     * rollback  回滚事务
     */
    public void rollback(TransactionStatus transaction){

        dataSourceTransactionManager.rollback(transaction);
    }

}
