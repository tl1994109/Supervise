package com.datcent.common.exception.user;

/**
 * 用户不存在异常类
 * 
 * @author datcent
 */
public class UserNotExistsException extends UserException
{

    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super("user.not.exists", null);
    }
}
