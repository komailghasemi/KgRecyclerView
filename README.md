# KgRecyclerView

screen shots

<div align="center">
    <img src="https://raw.githubusercontent.com/komailghasemi/KgRecyclerView/master/shots/Screenshot_20220410-154531_TestRv.jpg" width="200px" height="400px"></img> 
        <img src="https://github.com/komailghasemi/KgRecyclerView/blob/master/shots/Screenshot_20220410-154549_TestRv.jpg" width="200px" height="400px"></img> 
            <img src="https://github.com/komailghasemi/KgRecyclerView/blob/master/shots/Screenshot_20220410-154607_TestRv.jpg" width="200px" height="400px"></img> 
</div>
How To Use

in Xml 
```
    <com.app.libarary.KgRecyclerView
        android:id="@+id/recycle"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:noDataIconId="@mipmap/ic_launcher"
        app:noDataText="NO DATA"
        app:noDataTextColor="#f00f00"/>
```

in Kotlin 

```
        val rv = findViewById<KgRecyclerView>(R.id.recycle)

        rv.setHasFixedSize(true)
        rv.setLayoutManager()
        rv.setNestedScrolling(true)
        val adapter = CustomAdapter(list)
        rv.setAdapter(adapter)
```
before you set list to adapter you will see progressbar after that
if list of adapter is empty then you will see 'No data Icon and Text'

also you can enable paging 
```
rv.enableEndlessRecyclerView {
            for (i in 50..1000000){
                list.add("item $i")
            }
            adapter.addItems(list)
        }
```

at end of list you can load more data 

---------------------------------------------------------
setup :

Step 1. Add the JitPack repository to your build file


Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.komailghasemi:KgRecyclerView:1.0.0'
	}



