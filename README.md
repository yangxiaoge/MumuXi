# 妹子 & 知乎专栏

我在[个人博客](http://yangjianan.top/2015/05/23/MumuXi%E5%AE%89%E5%8D%93%E7%89%88%E5%BC%80%E5%8F%91/)对APP做了总结

> 参考: [wuchangfeng](https://github.com/wuchangfeng/ZhuanLan) https://github.com/wuchangfeng/ZhuanLan

项目详细 注解 以及功能在提交message和代码注解中


## 下载
<a href="http://fir.im/sq2t" target="_blank" alt="Fir"><img src="http://ww4.sinaimg.cn/mw1024/c05ae6b6gw1f802wvh1s2j203301cq2q.jpg"/></a>

<a href="http://android.myapp.com/myapp/detail.htm?apkName=com.yang.bruce.mumuxi" target="_blank" alt="应用宝"><img src="http://ww4.sinaimg.cn/mw1024/c05ae6b6gw1f5pv5t3kwwj203w01jglf.jpg"/></a>

<a href="http://www.wandoujia.com/apps/com.yang.bruce.mumuxi" target="_blank" alt="豌豆荚"><img src="http://ww1.sinaimg.cn/mw690/c05ae6b6gw1f5iyz0qbdgj204k01mglg.jpg"/></a>

主要实现功能:
- 知乎专栏-作者文章-文章detail
- 妹子福利
- about_me-换头像
- about_app-app开发目的,以及使用的框架
- for_what-是目标功能(有的还没实现)
- 所有的[loading动画](https://github.com/zzz40500/android-shapeLoadingView)用的是开源框架- 布局放在了EasyRecyclerView的 [layout_empty](https://github.com/yangxiaoge/MumuXi/blob/master/app/src/main/res/layout/fragment_zhuanlan_layout.xml) ,layout_progress,error中,具体根据实际来放
- 上拉,下拉刷新用的开源框架[EasyRecyclerView](https://github.com/Jude95/EasyRecyclerView)
-侧滑Drawer功能还没加,后面考虑加些其他功能~~~

**add google analytics谷歌分析** ( 2016-9-5 14:09:40 )

1. `google-services.json`[文件下载](https://developers.google.com/mobile/add?platform=android&cntapi=analytics&cnturl=https:%2F%2Fdevelopers.google.com%2Fanalytics%2Fdevguides%2Fcollection%2Fandroid%2Fv4%2Fapp%3Fconfigured%3Dtrue&cntlbl=Continue%20Adding%20Analytics)
2. 官方集成文档: https://developers.google.com/analytics/devguides/collection/android/v4/app?configured=true
3. 分析用户使用情况的平台: https://analytics.google.com/analytics/web/#realtime/rt-app-overview/a83624565w124744507p129062318/
4. ![google 分析](http://ww2.sinaimg.cn/mw1024/c05ae6b6gw1f7iknnth8nj217y0lx43d.jpg)
