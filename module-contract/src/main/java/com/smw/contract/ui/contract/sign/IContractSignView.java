package com.smw.contract.ui.contract.sign;

import com.smw.base.activity.IDataLoadView;
import com.smw.base.bean.BaseBean;
import com.smw.base.bean.DownloadResult;

public interface IContractSignView extends IDataLoadView {
    /**
     * 数据加载完成 单个
     * @param viewModel data
     */
    void onFileDownloadFinish(DownloadResult viewModel);
}
