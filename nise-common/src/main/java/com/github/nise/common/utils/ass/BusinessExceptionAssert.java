package com.github.nise.common.utils.ass;

import com.github.nise.common.exception.BusinessException;

/**
 * @author huzhi
 * @version $ v 0.1 2022/3/2 17:12 huzhi Exp $$
 */
public interface BusinessExceptionAssert extends ICodeEnum, Assert {

    @Override
    default BusinessException businessException() {
        return new BusinessException(this.getCode(),this.getMessage());
    }
}
