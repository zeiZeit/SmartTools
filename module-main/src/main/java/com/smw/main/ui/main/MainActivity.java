package com.smw.main.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.smw.base.activity.MvvmBaseActivity;
import com.smw.base.storage.MmkvHelper;
import com.smw.base.utils.SerializeUtil;
import com.smw.common.adapter.ScreenAutoAdapter;
import com.smw.common.contract.AppConfig;
import com.smw.common.contract.BaseCustomViewModel;
import com.smw.common.contract.StocksInfo;
import com.smw.common.router.RouterActivityPath;
import com.smw.common.router.RouterFragmentPath;
import com.smw.main.R;
import com.smw.main.adapter.MainPageAdapter;
import com.smw.main.databinding.MainActivityMainBinding;
import com.smw.main.utils.ColorUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.NavigationController;

/**
 * 
 * app 主页面
 * 
 * @author zeit
 */

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity
    extends MvvmBaseActivity<MainActivityMainBinding, MainActivityViewModel>
    implements IMainView
{
    
    private List<Fragment> fragments;
    
    private MainPageAdapter adapter;
    
    private NavigationController mNavigationController;

    @Override
    protected MainActivityViewModel getViewModel()
    {
        return ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }


    public static void start(Context context){
        MmkvHelper.getInstance().getMmkv().encode("first",false);
        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        ScreenAutoAdapter.match(this, 375.0f);
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this)
//            .statusBarColor(R.color.main_color_bar)
//            .navigationBarColor(R.color.main_color_bar)
//            .fitsSystemWindows(true)
//            .autoDarkModeEnable(true)
//            .init();
        initView();
        initFragment();
        loadConfig();
    }

    private void loadConfig() {
        viewModel.initModel();
        viewModel.load();
    }

    private void initView()
    {
        mNavigationController = viewDataBinding.bottomView.material()
            .addItem(R.drawable.main_select,
                "双录",
                ColorUtils.getColor(this, R.color.main_bottom_check_color))
//            .addItem(R.drawable.main_news,
//                "BB",
//                ColorUtils.getColor(this, R.color.main_bottom_check_color))
//            .addItem(R.drawable.main_practice,
//                "cc",
//                ColorUtils.getColor(this, R.color.main_bottom_check_color))
//            .addItem(R.drawable.main_mine,
//                "dd",
//                ColorUtils.getColor(this, R.color.main_bottom_check_color))
            .setDefaultColor(
                ColorUtils.getColor(this, R.color.main_bottom_default_color))
            .enableAnimateLayoutChanges()
            .build();
//        mNavigationController.setHasMessage(2, true);
//        mNavigationController.setMessageNumber(3, 6);
        adapter = new MainPageAdapter(getSupportFragmentManager(),
            FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        viewDataBinding.cvContentView.setOffscreenPageLimit(1);
        viewDataBinding.cvContentView.setAdapter(adapter);
        mNavigationController.setupWithViewPager(viewDataBinding.cvContentView);
    }
    
    private void initFragment()
    {
        fragments = new ArrayList<>();
        //通过ARouter 获取其他组件提供的fragment
        Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Select.PAGER_SELECT).navigation();
//        Fragment userFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.User.PAGER_USER).navigation();
        fragments.add(homeFragment);
//        fragments.add(userFragment);
        adapter.setData(fragments);
    }
    
    @Override
    protected int getBindingVariable()
    {
        return 0;
    }
    
    @Override
    protected int getLayoutId()
    {
        return R.layout.main_activity_main;
    }
    
    @Override
    protected void onRetryBtnClick()
    {
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAppConfigFinish(BaseCustomViewModel viewModel) {
        AppConfigResult appConfigResult = (AppConfigResult) viewModel;
        if (appConfigResult.appConfig!=null){
            if (appConfigResult.appConfig.getV() > AppConfig.getInstance().getV()){
                try {
                    AppConfig.init(appConfigResult.appConfig);
                    String infoStr = SerializeUtil.serialize(appConfigResult.appConfig);
                    MmkvHelper.getInstance().getMmkv().encode("appConfig",infoStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (appConfigResult.stocksInfo!=null){
            try {
                StocksInfo.init(appConfigResult.stocksInfo);
                String infoStr = SerializeUtil.serialize(appConfigResult.stocksInfo);
                MmkvHelper.getInstance().getMmkv().encode("stockInfo",infoStr);
                MmkvHelper.getInstance().getMmkv().encode("STOCK_INFO_UPDATE_TIME",System.currentTimeMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
