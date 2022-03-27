package com.smw.base.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.smw.base.R;

/**
 * 应用模块:
 * <p>
 * 类描述: 网络超时
 * <p>
 *
 * @author zeit
 * @since 2020-01-27
 */
public class TimeoutCallback extends Callback
{
    @Override
    protected int onCreateView()
    {
        return R.layout.base_layout_timeout;
    }
    
}
