# Toast


> Step 1. Add the JitPack repository to your build file

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

> Step 2. Add the dependency

```gradle
dependencies {
	        implementation 'com.github.vatsalleshwala2:Toast:1.0.0'
	}
```

> Step 3. How to use

```

public class MainActivity extends AppCompatActivity {
    AppConstants appConstants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appConstants = AppConstants.getInstance(this);



    }
}
```
