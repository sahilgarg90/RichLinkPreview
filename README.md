## Attention: This is a fork of the project(https://github.com/ponnamkarthik/RichLinkPreview)
**I have made some changes to add a new Link preview UI like shown in Facebook**

I did not write this library, all credit goes to [ponnamkarthik](https://github.com/ponnamkarthik).


# RichLink-Preview
A Rich Link Preview Library for Android


> Sample Image
<img src="https://github.com/sahilgarg90/RichLinkPreview/raw/master/screenshots/sample.png" width="300" alt="ScreenShot">


## Setup

The simplest way to use SweetAlertDialog is to add the library as aar dependency to your build.

**Maven**

    <dependency>
	    <groupId>com.github.sahilgarg90</groupId>
	    <artifactId>RichLinkPreview</artifactId>
	    <version>1.3</version>
	</dependency>
    
**Gradle**

Add it in your root build.gradle at the end of repositories

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Add the dependency in your project level build.gradle

    dependencies {
        implementation 'com.github.sahilgarg90:RichLinkPreview:1.3'
        implementation 'org.jsoup:jsoup:1.12.1'
    }

Initialize the SDK in Application class's onCreate() method

    RickLinkPreviewSdk.init(getApplicationContext());


#### To implement existing layout using XML

Add below code in activity_main.xml

~~~xml
<!--Facebook -->
<io.github.ponnamkarthik.richlinkpreview.RichLinkViewFacebook
    android:id="@+id/richLinkViewFacebook"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
</io.github.ponnamkarthik.richlinkpreview.RichLinkViewFacebook>
<!--default view or whatsapp -->
<io.github.ponnamkarthik.richlinkpreview.RichLinkView
    android:id="@+id/richLinkView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
</io.github.ponnamkarthik.richlinkpreview.RichLinkView>
<!-- Telegram -->
<io.github.ponnamkarthik.richlinkpreview.RichLinkViewTelegram
    android:id="@+id/richLinkView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
</io.github.ponnamkarthik.richlinkpreview.RichLinkViewTelegram>
<!-- Skype -->
<io.github.ponnamkarthik.richlinkpreview.RichLinkViewSkype
    android:id="@+id/richLinkView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
</io.github.ponnamkarthik.richlinkpreview.RichLinkViewSkype>
<!-- Twitter -->
<io.github.ponnamkarthik.richlinkpreview.RichLinkViewTwitter
    android:id="@+id/richLinkView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
</io.github.ponnamkarthik.richlinkpreview.RichLinkViewTwitter>
~~~

In your MainActivity.java add below code

~~~java
public class MainActivity extends AppCompatActivity {
    
    RichLinkView richLinkView; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ...
        // 
        richLinkView = (RichLinkView) findViewById(R.id.richLinkView);
        
        
        
        richLinkView.setLink("https://stackoverflow.com", new ViewListener() {
            
            @Override
            public void onSuccess(boolean status) {
                
            }
            
            @Override
            public void onError(Exception e) {
                
            }
        });
        
    }
}
~~~


~~~java
RichLinkViewFacebook richLinkViewFacebook;
RichLinkView richLinkView;
RichLinkViewTelegram richLinkViewTelegram;
RichLinkViewSkype richLinkViewSkype;
RichLinkViewTwitter richLinkViewTwitter;

//Set Link is same as default
~~~

> **OR**

#### If you want to implement your own layout.

~~~java
private MetaData data;

RichPreview richPreview = new RichPreview(new ResponseListener() {
    @Override
    public void onData(MetaData metaData) {
        data = metaData;
       
        //Implement your Layout
    }
    
    @Override
    public void onError(Exception e) {
        //handle error
    }
});
~~~

> if you want to set obtained meta data to view

~~~java

richLinkView.setLinkFromMeta(metaData)

or

MetaData metaData = new MetaData();
metaData.setTitle("Title");
metaData.setDescription("Custom Meta Data");
metaData.setFavicon("http://favicon url");
metaData.setImageurl("http://image url");
metaData.setSitename("Custom Meta data site");

richLinkView.setLinkFromMeta(metaData);


~~~


> Set your own CickListener

~~~java

//at first disable default click
richLinkView.setDefaultClickListener(false);

//set your own click listener
richLinkView.setClickListener(new RichLinkListener() {
    @Override
    public void onClicked(View view, MetaData meta) {
        //do stuff
        Toast.makeText(getApplicationContext(), meta.getTitle(), Toast.LENGTH_SHORT).show();
    }
});

~~~

> MetaData

```java
metaData.getTitle();

metaData.getImageurl();

metaData.getDescription();

metaData.getSitename();

metaData.getUrl();

metaData.getMediatype();
```
