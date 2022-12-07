package com.smw.contract.ui.contract.detail;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cy.necessaryview.shapeview.RecShapeTextView;
import com.gyf.immersionbar.ImmersionBar;
import com.limpoxe.support.servicemanager.ServiceManager;
import com.smw.base.activity.IDataLoadView;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.bean.BaseBean;
import com.smw.base.utils.ToastUtil;
import com.smw.common.adapter.CommonBindingAdapters;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.services.ILoginService;
import com.smw.common.utils.DensityUtils;
import com.smw.common.utils.ResUtils;
import com.smw.common.views.DrawableTextView;
import com.smw.contract.R;
import com.smw.contract.data.model.ContractDetailRes;
import com.smw.contract.data.model.SignFileBean;
import com.smw.contract.data.model.SignUserBean;
import com.smw.contract.databinding.ContractActivityDetailBinding;

import java.util.ArrayList;

@Route(path = RouterActivityPath.Contract.PAGER_CONTRACT_DETAIL)
public class ContractDetailActivity extends MvvmBaseActivity <ContractActivityDetailBinding, ContractDetailVM>
        implements IDataLoadView {

    @Autowired(name ="CONTRACT_ID")
    String mContractId;

    public static void open(String contractId){
        ARouter.getInstance().build(RouterActivityPath.Contract.PAGER_CONTRACT_DETAIL).withString("CONTRACT_ID",contractId).navigation();
    }

    @Override
    protected ContractDetailVM getViewModel() {
        return ViewModelProviders.of(this).get(ContractDetailVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contract_activity_detail;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void doBusiness() {
        ImmersionBar.with(this)
                .titleBar(viewDataBinding.llTop)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();
        ARouter.getInstance().inject(this);
        initRecycleView();
        setLoadSir(viewDataBinding.rlRoot);
        viewModel.initModel();
        showLoading();
        viewModel.load(mContractId);
    }


    FileLsAdapter mFileAdapter;
    UserLsAdapter mUserAdapter;
    String myUUID;
    private void initRecycleView() {

        Object objc = ServiceManager.getService(ILoginService.LOGIN_SERVICE_NAME);
        if (objc!=null){
            ILoginService loginService = (ILoginService)objc;
            myUUID = loginService.getUUID();
        }

        mFileAdapter = new FileLsAdapter();
        mFileAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        viewDataBinding.rvTemplate.setAdapter(mFileAdapter);
        viewDataBinding.rvTemplate.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.rvTemplate.setNestedScrollingEnabled(false);

        mUserAdapter = new UserLsAdapter();
        mUserAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        viewDataBinding.rvUser.setAdapter(mUserAdapter);
        viewDataBinding.rvUser.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        viewDataBinding.rvUser.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDataListLoadFinish(ArrayList<? extends BaseBean> viewModels, boolean isEmpty) {

    }

    @Override
    public void onDataLoadFinish(BaseBean viewModel) {
        ContractDetailRes detailRes = (ContractDetailRes) viewModel;
        showContent();
        refreshView(detailRes);
    }

    private void refreshView(ContractDetailRes detailRes) {
        CommonBindingAdapters.loadHeadImage(viewDataBinding.ivHead,detailRes.getCreate_user_head());
        viewDataBinding.tvContractName.setText(detailRes.getContract_name());
        viewDataBinding.tvCreator.setText(detailRes.getCreate_user());
        viewDataBinding.tvCreateTime.setText(detailRes.getCreate_time());
        viewDataBinding.tvUpdateTime.setText(detailRes.getUpdate_time());
        mFileAdapter.setNewData(detailRes.getFiles());
        mUserAdapter.setNewData(detailRes.getUsers());
    }

    @Override
    public void showFailure(String message) {
        super.showFailure(message);
        showContent();
        ToastUtil.show(this,message);
    }

    public class FileLsAdapter extends BaseQuickAdapter<SignFileBean, BaseViewHolder> {

        private boolean isChooseType;

        public FileLsAdapter() {
            super(R.layout.contract_layout_item_sign_file);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder baseViewHolder, SignFileBean item) {
            baseViewHolder.setText(R.id.tv_file_name,item.getFile_name());
            baseViewHolder.setText(R.id.tv_update_time,item.getUpdate_time()+"更新");

            TextView tv_sign_status = baseViewHolder.getView(R.id.tv_sign_status);
            if (item.getStatus()==0){
                if (item.getNow_sign_uuid().equals(myUUID)){
                    tv_sign_status.setText("去签署");
                    tv_sign_status.setTextColor(ResUtils.getColor(tv_sign_status,R.color.common_accent_blue));
                    baseViewHolder.setGone(R.id.iv_arrow,false);
                    baseViewHolder.setText(R.id.tv_now_user,"该您签署了");
                    baseViewHolder.setTextColor(R.id.tv_now_user,ResUtils.getColor(tv_sign_status,R.color.common_accent_red));

                }else {
                    tv_sign_status.setText("等待他人签署");
                    tv_sign_status.setTextColor(ResUtils.getColor(tv_sign_status,R.color.common_666));
                    baseViewHolder.setGone(R.id.iv_arrow,true);
                    baseViewHolder.setText(R.id.tv_now_user,"当前签署用户："+item.getNow_sign_name());
                    baseViewHolder.setTextColor(R.id.tv_now_user,ResUtils.getColor(tv_sign_status,R.color.common_accent_blue));
                }
            }else if (item.getStatus()==1){
                if (item.getNow_sign_uuid().equals(myUUID)){
                    tv_sign_status.setText("去确认");
                    tv_sign_status.setTextColor(ResUtils.getColor(tv_sign_status,R.color.common_accent_blue));
                    baseViewHolder.setGone(R.id.iv_arrow,false);
                    baseViewHolder.setText(R.id.tv_now_user,"该您确认了");
                    baseViewHolder.setTextColor(R.id.tv_now_user,ResUtils.getColor(tv_sign_status,R.color.common_accent_red));
                }else {
                    tv_sign_status.setText("等待他人确认");
                    tv_sign_status.setTextColor(ResUtils.getColor(tv_sign_status,R.color.common_666));
                    baseViewHolder.setGone(R.id.iv_arrow,true);
                    baseViewHolder.setText(R.id.tv_now_user,"当前确认用户："+item.getNow_sign_name());
                    baseViewHolder.setTextColor(R.id.tv_now_user,ResUtils.getColor(tv_sign_status,R.color.common_accent_blue));
                }
            }else {
                //签署完
                tv_sign_status.setText("已完成");
                tv_sign_status.setTextColor(ResUtils.getColor(tv_sign_status,R.color.common_666));
                baseViewHolder.setGone(R.id.iv_arrow,true);
                baseViewHolder.setText(R.id.tv_now_user,"");
            }
        }

        public boolean isChooseType() {
            return isChooseType;
        }

        public void setChooseType(boolean chooseType) {
            isChooseType = chooseType;
        }
    }

    public class UserLsAdapter extends BaseQuickAdapter<SignUserBean, BaseViewHolder> {
        

        public UserLsAdapter() {
            super(R.layout.contract_layout_item_sign_user);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder baseViewHolder, SignUserBean item) {
            CommonBindingAdapters.loadHeadImage(baseViewHolder.getView(R.id.head),item.getHead_image());
            baseViewHolder.setText(R.id.tv_name,item.getUser_name());
            RecShapeTextView tv_status = baseViewHolder.getView(R.id.tv_status);
            GradientDrawable gradientDrawable = new GradientDrawable();//创建背景drawable
            gradientDrawable.setCornerRadius(DensityUtils.dip2px(ContractDetailActivity.this,4));//radiusCorner优先级比cornerRadii高
            Drawable[] layers = {gradientDrawable};
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            if (item.getStatus()==0){
                gradientDrawable.setColor(ResUtils.getColor(ContractDetailActivity.this,R.color.common_accent_red));//设置填充色
                tv_status.setText("未完成");
            }else if(item.getStatus()==1){
                tv_status.setText("待确认");
                gradientDrawable.setColor(ResUtils.getColor(ContractDetailActivity.this,R.color.common_accent_yellow));//设置填充色
            }else if (item.getStatus()==2){
                gradientDrawable.setColor(ResUtils.getColor(ContractDetailActivity.this,R.color.common_accent_blue));//设置填充色
                tv_status.setText("已完成");
            }
            tv_status.setBackground(layerDrawable);
        }
        
    }
}