package com.smw.contract.ui.contract.sign;

import com.smw.base.activity.IDataLoadView;
import com.smw.base.bean.BaseBean;
import com.smw.base.bean.DownloadResult;
import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;
import com.smw.contract.ui.contract.list.ContractLsModel;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.subsciber.IProgressDialog;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author zeit
 * @since 2020-02-20
 */
public class ContractSignVM
    extends MvmBaseViewModel<IContractSignView, ContractSignModel>
    implements IModelListener<BaseBean>
{

    @Override
    protected void initModel() {
        model = new ContractSignModel();
        model.register(this);

    }

    public void load(){
        model.load();
    }

    public void downLoadFile(String fileDir,String fileName,String url){
        model.downloadFile(fileDir,fileName,url);
    }

    public void uploadFile(IProgressDialog dialog, UIProgressResponseCallBack listener, String contactId, String fileId, String filePath){
        model.uploadFile(dialog,listener,contactId,fileId,filePath);
    }



    @Override
    public void onLoadFinish(BaseModel model, BaseBean data) {
        if (getPageView() != null) {
            if (data != null ){
                if (data instanceof DownloadResult){
                    getPageView().onFileDownloadFinish((DownloadResult) data);
                }else {
                    getPageView().onDataLoadFinish(data);
                }
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        if (getPageView() != null) {
            getPageView().showFailure(prompt);
        }
    }

    @Override
    public void detachUi()
    {
        super.detachUi();
        if (model != null)
        {
            model.unRegister(this);
        }
    }


}
