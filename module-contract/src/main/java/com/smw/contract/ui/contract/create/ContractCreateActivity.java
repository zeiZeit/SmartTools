package com.smw.contract.ui.contract.create;

import android.content.Intent;
import android.os.Parcelable;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.utils.ToastUtil;
import com.smw.base.bean.BaseBean;

import com.smw.common.global.GlobalKey;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.utils.StringUtils;
import com.smw.contract.R;
import com.smw.common.contract.SearchUserBean;
import com.smw.contract.data.model.TemplateFile;
import com.smw.contract.databinding.ContractActivityContractCreateBinding;

import org.json.JSONArray;

import java.util.ArrayList;

@Route(path = RouterActivityPath.Contract.PAGER_CONTRACT_CREATE)
public class ContractCreateActivity extends MvvmBaseActivity <ContractActivityContractCreateBinding, ContractCreateVM>
        implements IContractCreateView {

    @Autowired(name="uuid")
    String uuid;
    @Autowired(name="user_name")
    String userName;
    @Autowired(name="head_image")
    String headImage;


    public static void open(){
        ARouter.getInstance().build(RouterActivityPath.Contract.PAGER_CONTRACT_CREATE).navigation();
    }

    @Override
    protected ContractCreateVM getViewModel() {
        return ViewModelProviders.of(this).get(ContractCreateVM.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contract_activity_contract_create;
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
        viewDataBinding.ivBack.setOnClickListener(t->{
            onBackPressed();
        });
        setLoadSir(viewDataBinding.rlRoot);
        viewModel.initModel();
        initUserRecycleView();
        initTemplateRecycleView();
        viewDataBinding.btnChooseTemplate.setOnClickListener(T->{
            //去选择模板文件
            ARouter.getInstance()
                    .build(RouterActivityPath.Contract.PAGER_TEMPLATE_MINE)
                    .withBoolean("IS_CHOOSE",true)
                    .withParcelableArrayList("SELECTED_FILES", (ArrayList<? extends Parcelable>) templateAddAdapter.getData())
                    .navigation(this, 555);
        });
        viewDataBinding.btnCreate.setOnClickListener(t->{
            checkToCreate();
        });
    }

    private void checkToCreate() {
        String fileName = viewDataBinding.etContractName.getText().toString();
        if (StringUtils.isNull(fileName.trim())){
            ToastUtil.show(this,"请输入合同名");
            return;
        }
        if (userAddAdapter.getData()==null||userAddAdapter.getData().size()==2){
            ToastUtil.show(this,"请先添加签署文件的用户");
            return;
        }
        if (templateAddAdapter.getData()==null||templateAddAdapter.getData().size()==0){
            ToastUtil.show(this,"请先添加要签署的文件");
            return;
        }
        JSONArray jaUser = new JSONArray();
        JSONArray jaTemplate = new JSONArray();
        for (SearchUserBean userBean:userAddAdapter.getData()){
            jaUser.put(userBean.getUuid());
        }
        for (TemplateFile templateFile:templateAddAdapter.getData()){
            jaTemplate.put(templateFile.getTemplate_id());
        }
        showLoading();
        viewModel.createContract(fileName,jaUser.toString(),jaTemplate.toString());
    }

    UserAddAdapter userAddAdapter;
    private void initUserRecycleView() {
        userAddAdapter = new UserAddAdapter();
        viewDataBinding.rvUser.setAdapter(userAddAdapter);
        viewDataBinding.rvUser.setLayoutManager(new GridLayoutManager(this,4));
        viewDataBinding.rvUser.setNestedScrollingEnabled(false);
        SearchUserBean userBean = new SearchUserBean(userName,headImage,uuid,false);
        userAddAdapter.addData(userBean);
        SearchUserBean add = new SearchUserBean();
        add.setAdd(true);
        add.setUuid("");
        userAddAdapter.addData(add);
        userAddAdapter.addChildClickViewIds(R.id.remove,R.id.rl_add);
        userAddAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId()==R.id.remove){
                //删除
                userAddAdapter.remove(position);
            }else if (view.getId()==R.id.rl_add){
                //添加 应该带上已添加用户的信息
                toSearchAddUser();
            }
        });
    }

    TemplateAddAdapter templateAddAdapter;
    private void initTemplateRecycleView() {
        templateAddAdapter = new TemplateAddAdapter();
        templateAddAdapter.addChildClickViewIds(R.id.remove);
        viewDataBinding.rvTemplate.setAdapter(templateAddAdapter);
        viewDataBinding.rvTemplate.setLayoutManager(new LinearLayoutManager(this));
        templateAddAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //删除
            templateAddAdapter.remove(position);
        });
    }

    private void toSearchAddUser() {
        ARouter.getInstance()
                .build(RouterActivityPath.User.PAGER_USER_SEARCH)
                .withParcelableArrayList("SELECT_USERS", (ArrayList<? extends Parcelable>) userAddAdapter.getData())
                .navigation(this, 666);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 666:
                if (data==null)
                    return;
                SearchUserBean userBean = data.getParcelableExtra(GlobalKey.Key.USER_SEARCH_RESULT);
                userBean.setCanDelete(true);
                if (!StringUtils.isNull(userBean.getUuid())){
                    userAddAdapter.addData(userAddAdapter.getData().size()-1,userBean);
                }
                break;
            case 555:
                if (data==null)
                    return;
                ArrayList<TemplateFile> files =  data.getParcelableArrayListExtra(GlobalKey.Key.TEMPLATE_SELECTED_RESULT);
                templateAddAdapter.setNewData(files);
                break;
            default:
                break;
        }
    }


    @Override
    public void onCreateContractResult(BaseBean viewModel) {
        showContent();
        finish();
    }

    @Override
    public void onRequestFailed(String message) {
        showContent();
        ToastUtil.show(this,message);
    }
}