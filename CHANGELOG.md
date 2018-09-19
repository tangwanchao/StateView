## ChangeLog

#### Version
Solve the ClassCastException: constraint-layout version 1.1.3
When viewParent is ConstraintLayout
Change other view's Constraint to root, if the origin Constraint is parent

#### Version 1.5.3
fix #15 when parent.getParent() is ConstraintLayout

#### Version 1.5.0
Supported wrap view

#### Version 1.3.4
fix: no show when quick switch shows Retry, Loading. #11

#### Version 1.3.3
Support inject RecyclerView

#### Version 1.3.2
增加 ```OnInflateListener```，在 view 成功添加到 parent 的时候回调（每个 viewType 只回调一次），
可在回调中对 View 进行操作设置，比如设置文本图像等。

#### Version 1.3.1
更改 ```AnimatorProvider``` 接口，让动画效果的自由度更高

#### Version 1.3.0
增加支持视图动画切换效果，另增加一个动画效果提供库 ```animations```

#### Version 1.2.1
修改 inject 方法，如果使用 ```StateView.inject(View view)``` 传入的 view 不是 ViewGroup，则会尝试注入到 view 的父容器中，
另外增加判断 ViewGroup 是 SwipeRefreshLayout/NestedScrollView 的情况。

#### Version 1.1.0
fix [issues #6](https://github.com/nukc/StateView/issues/6)

#### Version 1.0.0
删除上版本 Deprecated 的方法；
修改 inject(ViewGroup parent) 方法

#### Version 0.3.5
更改inject(activity)方法, 不直接添加到DecorView中, 而加到Content中.
Deprecated几个方法.

#### Version 0.3.3
增加自定义视图的Sample;
修改library的默认layout名字

#### Version 0.3.2
进一步测试, 增加Sample;
删除没必要的方法, 考虑到注入不应该遮挡工具栏, 为此增加inject方法

#### Version 0.3.1
增加静态方法:
inject(View view),该参数view必须是viewGroup,可用于在Fragment中

#### Version 0.3.0
增加静态方法:
inject(Activity activity),用于把StateView添加到DecorView中;

inject(ViewGroup parent),用于添加到ViewGroup中

#### Version: 0.2.4
修复显示LoadingView后还能触摸下层的View

#### Version: 0.2.3
修复 [issues #2](https://github.com/nukc/StateView/issues/2)

#### Version: 0.2.1
更新gradle和library版本, 增加一个私有方法showView。

#### Version: 0.2.0
修复v0.1.0版本中当使用layout_below的时候addView可能无法正常显示的问题。

#### Version: 0.1.0
测试Sample得知：
```xml
        <com.github.nukc.stateview.StateView
             android:id="@+id/stateView"
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             android:layout_marginTop="100dp"
             tools:visibility="gone" />
```
    使用 android:layout_below="@+id/ll" 的话 , addView有时会无法正常显示，有时却正常。在寻找问题。。

    3个按钮错乱多按几次，有几率会出现不显示的情况。
