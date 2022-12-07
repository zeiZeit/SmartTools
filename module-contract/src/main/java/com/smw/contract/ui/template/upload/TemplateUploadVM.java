package com.smw.contract.ui.template.upload;

import android.net.Uri;

import com.smw.base.model.BaseModel;
import com.smw.base.model.IModelListener;
import com.smw.base.viewmodel.MvmBaseViewModel;
import com.smw.base.bean.BaseBean;
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
public class TemplateUploadVM
    extends MvmBaseViewModel<ITemplateUploadView, TemplateUploadModel>
    implements IModelListener<BaseBean>

{

    @Override
    protected void initModel() {
        model = new TemplateUploadModel();
        model.register(this);

    }

    public void load(){
        model.load();
    }


    public void loadTemplate(IProgressDialog dialog, UIProgressResponseCallBack listener, String fileName, String readTime, String filePath, Uri uri){
        model.loadTemplate(dialog,listener,fileName,readTime,filePath,uri);
    }

    @Override
    public void onLoadFinish(BaseModel model, BaseBean data) {
        if (getPageView() != null) {
            if (data != null ){
                getPageView().onLoadTemplateResult(data);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        if (getPageView() != null) {
            getPageView().onUploadFileFailed(prompt);
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
