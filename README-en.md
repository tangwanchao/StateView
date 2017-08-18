# StateView

[![Build Status](https://travis-ci.org/nukc/StateView.svg?branch=master)](https://travis-ci.org/nukc/StateView)
[![Download](https://api.bintray.com/packages/nukc/maven/StateView/images/download.svg) ](https://bintray.com/nukc/maven/StateView/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-StateView-green.svg?style=true)](https://android-arsenal.com/details/1/4255)

[中文](https://github.com/nukc/StateView/blob/master/README-zh.md)

StateView is an invisible, zero-sized View that can be used to lazily inflate loadingView/emptyView/retryView at runtime.

<img src="https://raw.githubusercontent.com/nukc/stateview/master/art/custom.gif"><img width="200"><img src="https://raw.githubusercontent.com/nukc/stateview/master/art/animations.gif">

## Installation

add the dependency to your build.gradle:

```groovy
   compile 'com.github.nukc.stateview:library:1.3.0'

   // animator providers
      compile 'com.github.nukc.stateview:animations:1.0'
```

##Usage

Can be directly used in java.

```java
    mStateView = StateView.inject(Activity activity);
```

```java
    mStateView = StateView.inject(ViewGroup parent);

    mStateView = StateView.inject(ViewGroup parent, boolean hasActionBar);
```

```java
    // if view is not ViewGroup, StateView will be inject to view.getPatent()
    mStateView = StateView.inject(View view);

    mStateView = StateView.inject(View view, boolean hasActionBar);
```


Or include the StateView widget in your layout.

```xml

    <com.github.nukc.stateview.StateView
        android:id="@+id/stateView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

```

To switch the state view:

- ```mStateView.showEmpty();```
- ```mStateView.showLoading();```
- ```mStateView.showRetry();```
- ``` mStateView.showContent();```

To listen the retry click:

```java
    mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
        @Override
        public void onRetryClick() {
            //do something, no need to call showLoading()
        }
    });
```

To customize view:

- Global settings way: create a new layout (layout's name must be ```base_empty```/```base_retry```/```base_loading```).

- Single page setting: create a new layout whit different name, and set resource in java.

```java
setEmptyResource(@LayoutRes int emptyResource)

setRetryResource(@LayoutRes int retryResource)

setLoadingResource(@LayoutRes int loadingResource)
```


## Custom Attribute

```xml
<resources>
    <declare-styleable name="StateView">
        <attr name="emptyResource" format="reference" />
        <attr name="retryResource" format="reference" />
        <attr name="loadingResource" format="reference" />
    </declare-styleable>
</resources>
```

## Animation

set:

```java
    // provider default is null, no animation
    // if need, set a AnimatorProvider
    setAnimatorProvider(AnimatorProvider provider)

```

animation can custom, can also compile ```animations```

```groovy
compile 'com.github.nukc.stateview:animations:1.0'

```

```animations``` library has:

- ```FadeScaleAnimatorProvider```
- ```FlipAnimatorProvider```
- ```SlideAnimatorProvider```


if want a custom animation，implements ```AnimatorProvider```

```java
public class FadeScaleAnimatorProvider implements AnimatorProvider {

    @Override
    public Animator showAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(null, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(null, "scaleX", 0.1f, 1f),
                ObjectAnimator.ofFloat(null, "scaleY", 0.1f, 1f)
        );
        return set;
    }

    @Override
    public Animator hideAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(null, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(null, "scaleX", 1f, 0.1f),
                ObjectAnimator.ofFloat(null, "scaleY", 1f, 0.1f)
        );
        return set;
    }
}

```


## License

    The MIT License (MIT)

    Copyright (c) 2016, 2017 Nukc

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.