# sdk开发者文档(Android)

## SDK简介说明

- 开发环境
	
    本sdk文档是基于Android Studio开发而编写的，因此暂未提供jar包方式依赖。

- Idr类

    调用SDK所有的入口包括但不限于SDK初始化、加载地图、定位、导航，都是通过Idr类调用的。
	
    除了Idr.initSDK(Context)初始化SDK的方法和Idr.getRegionList()获取用户下面的region列表等少数方法是静态方法外，其他的方法都是实例方法。
	
    调用实例方法均需要**获取Idr对象**
	
	```
	Idr idr  = Idr.with(IdrMapView);
	```

## 1、配置和初始化

### sdk植入

- **build.gradle文件配置**
	
```
allprojects {
repositories {
jcenter()
maven { url 'https://dl.bintray.com/yellfun/maven'}
}
}
```
	
- **app/build.gradle文件配置**
	
```
dependencies {
compile 'com.indoorun.mapapi:IndoorunSDK_Core:2.1.0' // SDK核心库
compile 'com.indoorun.mapapi:IndoorunSDK_UI:2.1.0' // UI模块库
}
```
	
### 开发者信息配置

- **AndroidManifest.xml文件配置**
	
```
	
	<!-- 您申请的AppId-->
   <meta-data
       android:name="idrAppId"
       android:value="******" /> 
       
	<!-- 您申请的AppKey-->
   <meta-data
       android:name="idrAppKey"
       android:value="*****************************" />

```
### sdk初始化

- **应用Application的onCreate方法里面调用**
	
```
Idr.initSDK(this)//必须调用此方法来初始化SDK，下面的方法均是可选的。
      .needCrash()// 是否·打印崩溃log到sd卡
      .setBeaconTimeSpan(1000))//设置beacon扫描时间间隔，单位ms
      .setBeaconUUID(“uuid”)//设置beacon扫描过滤的UUID
      .setBeaconTimeLive(4000);//设置蓝牙信号存活时间，单位ms
	
```

## 2、地图模块
### region相关

#### 获取region列表
    
```
/**
* 获取region列表，老方法，2.1.0 版本里面含有此方法，可以调用
*/
ComApi.getInstance().getRegionListOfUser()
              .doOnNext(resRegionListModel -> {
   
              })
              .onErrorResumeNext(Observable.empty())
              .subscribe();
              
/**
* 获取region列表 , 新增加的方法 在 2.1.0 里面没有。后期会加上。此方法为静态方法, 可以直接通过Idr类名调用
*/
Idr.getRegionList(regions -> {
  // regions List<IdrMapRegion> 对象
}, throwable -> {
  // 错误异常 ，可不传
});
```
        
#### 加载region
	
```
/**
* 加载region，根据regionId获取region信息。
* 此方法会返回一个MapLoader对象，此对象是对Map的基础操作和一些事件回调，
* MapLoader对象有包括但不限于loadFLoor()方法。
* 调用这个方法不会从网络获取region信息，只会记录regionId和设置一些回调，
* 只有在调用loadFloor()的时候，才会判断region对象是否为空。
* 如果region对象为空，才会去网络获取region信息。
*/
idr.loadRegion("xxxxx");
```
	
- 获取region信息
	
```
IdrMapRegion region = idr.getMapRegion();
```
  
`idr.getMapRegion()` 方法可以获取到IdrMapRegion对象，里面包含的是整个region的信息。
  
`IdrMapRegion`对象是在调用`loadFloor()`之后才会去网络或者缓存里面异步获取，也就是说，该方法返回的对象有可能是空的。因此，使用此对象之前，要做一下非空判断。		

**IdrMapRegion**对象

region的所有信息，例如楼层信息和相应楼层的unit信息，都在这个对象里面。该对象的结构如下:

```
/**
  * region对象模型
  */
public class IdrMapRegion {
	private String name;								//region的名字
	private String cityId;								//region所在的城市ID
	private String regionType;
	private float northDeflectionAngle;					//北偏角，地理正北到地图正北的逆时针夹角，0~360
	private String telephone;
	private String address;								//region的位置描述
	private String markerUrl;
	private float longitude;							//region的位置的纬度信息
	private float latitude;								//region的位置的经度信息
	private String status;
	private long recordTime;
	private int collectStatus;							//是否已采集完成
	private int checkStatus;							//是否已测试通过
	private String recordUser;
	public String modifyUser;
	public String defaultFloorId;						//默认楼层,默认加载的楼层ID
	public long modifyTime;
	private List<IdrMapRegionFloor> floorList;			//region的楼层列表
	public String totalPackSize;
	public String packingFee;
	public String workTimes;
	public String packingType;
	public String description;
	public List<IdrMapRegionFloorPasses> floorPassList;	//贯通层数据
	public String beaconUUID;
	private List<IdrMapRegionFloorUnit> outerExitList;
	private boolean cuserStored;
	private boolean enDyGuide;
	private String introduction;
	private String photoUrl;
	private String RegionWholeDataFileName;
	private long RegionWholeDataFileSize;
	private String pinyin;
}

/**
  * region楼层模型
  */
public class IdrMapRegionFloor {
	private String title;
	private float ratio;								//当前楼层的缩放率，这个影响定位层的移动速度 1个像素代表真实的X米
	private String mapUrl;    							//从该地址下载该楼层地图的svg文件
	private String mapFilePath;
	private String dataImagePath;
	private int dataImageSize;
	private long dataImageTime;
	private long modifyTime;
	private List<IdrMapHeat> quyuList;
	public String floorName;
	public long collectModifyTime;
	public long mapModifyTime;
	public long unitModifyTime;
	public String regionId;
	public float width;
	public float height;
	private String modifyUser;
	private String recordUser;
	private long recordTime;
	private ResRouteModel pathStructure;
	private List<IdrMapLine> collectLineList;			//采集线
	List<IdrMapRegionFloorUnit> unitList;				//该楼层下面的unit列表
}

/**
  * region楼层的unit模型
  */
public class IdrMapRegionFloorUnit {
	private String name;
	private int unitTypeId;								//0，自定义类型 ;1，自动扶梯 ;2，电梯;3，卫生间;4，取款机;5，出口;6，点状图标，可自定义名称;7，入口;8，安全出口;9，楼梯;10，洗车,11收银台;
	private String description;
	private float boundLeft;
	private float boundTop;
	private float boundRight;
	private float boundBottom;
	private String status;
	private String creator;
	private long gmtCreate;
	private String modifier;
	private long gmtModified;							//格林威治标准时间(校准)
	private String points;
	private String detailUrl;
	private boolean isShown = true;
	private double lineDistance;
} 
```


### 地图显示
#### 加载地图
	
加载地图需要用`MapLoader`对象。结合上面的加载region的方法，可以通过下面几个方法加载楼层
	
- 加载默认楼层
	
	```
	  idr.loadRegion("xxxxxx")   // 加载指定Region
		 .loadFloor();          // 加载默认楼层
	```	
- 加载指定楼层
	
	```
	  idr.loadRegion("xxxxxx")   // 加载指定Region
		 .loadFloor("xxxxxx")；  // 加载指定楼层
	```
	
- 用外部楼层切换器加载楼层
	
   ```
   idr.loadRegion("xxxxxx")     // 加载指定Region
   .loadFloor(Loader);     // 外部实现Loader接口，来实现楼层切换
   ```
  UI模块的`SpinnerView`已经实现`Loader`接口，可以用来进行楼层切换。
		
- 获取当前显示楼层信息

   可以通过`MapLoader`对象来获取当前楼层信息。

   ```
       MapLoader mapLoader = idr.loadRegion("xxxxxx")
                                .loadFloor();
       IdrMapRegionFloor floor = mapLoader.getShowFloor();
   ```
- 切换楼层
   - 通过代码，主动切换楼层
       
       ```
           MapLoader mapLoader = idr.loadRegion("xxxx");
                                    .loadFloor();
           mapLoader.switchFloor(SwitchModel.fromFloorId("xxxxx"));
       ```
   - 自定义楼层切换器
   
       继承自接口`Loader`，来实现楼层的UI响应切换楼层。
       
       ```
           SpinnerView spinnerView = (SpinnerView)findViewById(R.id.spinerView);
           MapLoader mapLoader = idr.loadRegion("xxxxxx")
                                     .loadFloor(spinnerView);
       ```
       `SpinnerView`是UI库里面的一个视图组件，已实现了`Loader`接口，可以用来在视图里面让用户手动来切换楼层。
       
   - 两种加载楼层的区别
   
       `MapLoader#loadFloor()`方法和`MapLoader#switchFloor()`方法均可以加载楼层，他们有很明显的区别。
       
       `loadFloor`是`MapLoader`还未加载`Region`的时候，调用以此来加载`Region`信息的。此方法有主要有2类方法，一类是加载指定楼层（指定FloorId，指定IdrMapRegionFloor对象，或者默认楼层），一类是加载一个实现`Loader`接口的对象，用来外部切换楼层加载。
       
#### marker操作
- 添加marker
    添加marker同样需要MapLoader来添加
    
   ```
     /**
     * 添加marker
     * @params String 添加marker所在的楼层
     * @params int 添加marker的class （可以通过class移除一类marker）
     * @params Bitmap 添加marker的Bitmap
     * @params float 添加marker中心点的x坐标
     * @params float 添加market中心点的y坐标
     * @params float marker的x偏移，实际位置是中心点减去偏移
     * @params float marker的y偏移，实际位置是中心点减去偏移
     * @return Marker 返回添加的Marker对象
     */
     mapLoader.addMarker(String,int,Bitmap,float,float,float,float);
  ```
  
- 移除marker

   移除Marker可以用MapLoader来操作，也可以用Marker来操作。
   添加Marker的方法，将返回一个Marker对象。用此对象来更新icon。
   
  ```
     /**
     * 移除所有marker
     */
     mapLoader.removeAllMarker();
     
     /**
     * 移除指定的marker
     */
     mapLoader.removeMarker(Marker)
     
     /**
     * 根据指定的class移除marker
     */
     mapLoader.removeMarkerByClass(int)
     
     /**
     * 用Marker对象来移除单个Marker
     */
     marker.removeMe();
     
     /**
     * 用Marker对象来移除一类Marker
     */
     marker.removeOurCls();
  ```
  
- 更新icon
   添加Marker的方法，将返回一个Marker对象。用此对象来更新icon
   
  ```
    /**
    * 更新Marker的图标
    */        
    marker.updateMarkerBitMap(Bitmap);
  ```
  
- 更新Marker坐标
	
  ```
    /**
    * 更新Marker的Position
    * @params String marker的楼层ID
    * @params PointF marker的坐标
    */        
    marker.updateMarkerPosition(String,PointF);
  ```
  
- 单击、长按marker
   
  ```
    mapLoader.onMarkerClick(new Func1<Marker, Boolean>() {//marker单击事件
             @Override
             public Boolean call(Marker marker) {     
                 return true;// 是否拦截此次事件传递
                 }
         })
         .onMapLongClick(new Func2<MapLoader, PointF, Boolean>() {//marker长按事件
             @Override
             public Boolean call(MapLoader mapLoader, PointF pointF) {
                 return true;// 是否拦截此次事件传递
             }
         });
  ```

#### unit响应

- 单击、长按unit

  ```
   mapLoader.onMapUnitClick(new Func2<MapLoader, IdrMapRegionFloorUnit, Boolean>() {// unit单击事件
             @Override
             public Boolean call(MapLoader mapLoader, IdrMapRegionFloorUnit idrMapRegionFloorUnit) {
                 return true;// 是否拦截此次事件传递
             }
         })
         .onMapUnitLongClick(new Func2<MapLoader, IdrMapRegionFloorUnit, Boolean>() {// unit长按事件
             @Override
             public Boolean call(MapLoader mapLoader, IdrMapRegionFloorUnit idrMapRegionFloorUnit) {
                 return true;// 是否拦截此次事件传递
             }
         });
  ```
       
#### 地图响应

- 单击、长按地图

  ```
    mapLoader.onMapClick(new Func2<MapLoader, PointF, Boolean>() {// 地图单击事件
             @Override
             public Boolean call(MapLoader mapLoader, PointF pointF) {
                 return true;// 是否拦截此次事件传递
             }
         })
         .onMapLongClick(new Func2<MapLoader, PointF, Boolean>() {// 地图长按
             @Override
             public Boolean call(MapLoader mapLoader, PointF pointF) {
                 return true;// 是否拦截此次事件传递
             }
         })
   ```
        
### 地图基本操作

- 概述 

   地图的基本操作均由一个接口完成        

  ```
      /**
      * @params float 缩放比例
      * @params float 旋转角度
      * @params float 地图x坐标
      * @params float 地图y坐标
      * @params float 屏幕x坐标
      * @params float 屏幕y坐标
      */
      mapLoader.animRotateZoomMergeP2P(float, float, float, float, float, float)
  ```
   
- 平移（平移任何一点到地图中央）
       
  ```
     /** 
     * 平移也就是操作地图，不需要缩放和旋转，那么缩放就填写1，旋转参数填写0。
     * 后面2个参数对应的就是地图坐标和屏幕坐标的x和y参数值，调用此方法之后，这2个坐标将会重合
     */
      mapLoader.animRotateZoomMergeP2P(1,0, float, float, float, float)
     
     /**
     *  移动地图点到屏幕中心（不缩放）
     *
     * @param PointF 要移到屏幕中心的地图上面的点
     */
     mapLoader.moveMapPointToScreenCenter(PointF);
  ```
- 旋转

  ```
      /**
      * 根据地图坐标旋转地图
      * 
      * @params boolean 是否有动画
      * @params float   旋转角度
      * @params PointF  地图坐标
      */
      mapLoader.rotateByMapPos(boolean,float,PointF);
  ```
- 缩放
    
  ```
      /**
       * 根据屏幕坐标缩放地图（含有动画）
       *
       * @param float 缩放比例
       * @param float 屏幕坐标x
       * @param float 屏幕坐标y
       */
       mapLoader.animZoomByScreenPos(float,float,float);
  ```
   
- 屏幕地图坐标转换

 ```
     /**
     * 将屏幕坐标转换为地图坐标
     * 
     * @params PointF 屏幕上面的点
     * @return PointF 返回地图上面的点
     */
     mapLoader.screenToMap(PointF);
     
     /**
     * 将地图坐标转换为屏幕坐标
     * 
     * @params PointF 地图上面的点
     * @return PointF 返回屏幕上面的点
     */
     mapLoader.mapToScreen(PointF);
 ```
- 地图归位

  ```
      /**
      * 重置地图，按照等宽或者等高显示
      */
      mapLoader.resetMap();
  ```
        
### 定位相关接口
- 设置定位点图标
    
 ```
     /**
     * 设置定位点图标
     * 
     * @params int|Bitmap 定位点的资源文件或者Bitmap资源
     */
     mapLoader.setLocator(int|Bitmap);
 ```
- 是否显示罗盘

  ```
      /**
      *　设置是否显示罗盘
      * 
      * @params boolean 是否显示罗盘
      */
      mapLoader.setDirectionIndicatorVisibility(boolean)
  ```
       
- 是否显示红色直线
    
  ```
      /**
      * 设置是否显示红色导航方向线
      * 
      * @params boolean 是否显示红色导航线
      */
      mapLoader.setRouteDirectLineVisible(boolean)
  ```
        
### 导航相关接口
- 鸟瞰地图
   
   鸟瞰地图模式，是在导航中，预览到航线，此模式下，会将所有的点全部都显示在屏幕里面。

   可以设置鸟瞰地图的边距，让靠近屏幕的点和屏幕边缘有一定的距离。

  ```
     /**
     * 鸟瞰地图
     */
     mapLoader.routerBirdEye();
     
     /**
     * 设置鸟瞰地图的边距
     * 
     * @params float 左边距
     * @params float 上边距
     * @params float 右边距
     * @params float 下边距
     */
     mapLoader.setRouterBirdEyeBoundaries(float,float,float,float);
  ```
   
   
- 地图状态改变回调

   地图状态有 `Normal`（普通模式）,`Normal`(追随模式),`Normal`（跟随模式）,`Navigate`（导航模式）
   
   `Trace`：<small>追随模式，定位中，当定位点超出屏幕外面的时候，自动移动地图，让定位点显示在屏幕中央。</small>
   
   `Follow`：<small>跟随模式，定位中，当用户处于移动中，旋转地图，让地图前方显示为移动方向。</small>
   
   `Navigate`：<small>导航模式，定位导航中，旋转地图，使导航线永远在用户正前方。</small>
   
   ```
     /**
      * 监听地图状态改变
      */
      mapLoader.onMapStateChange(new Action2<MapLoader, IdrnGLView.MapState>() {
          @Override
          public void call(MapLoader mapLoader, IdrnGLView.MapState mapState) {
 
           }
       });
    ```
   
- 设置地图状态

    ```
     /**
     * 设置地图状态
     * 
     * @params IdrnGLView.MapState  地图状态
     */
     mapLoader.setMapState(IdrnGLView.MapState);
    ```

## 3、定位模块

### 定位
- 开启定位、结束定位

   定位功能需要部署beacon设备，因此，该功能需要在有beacon的环境中才能体现出来。
   
   定位有两种开启和结束方式
   
   - 绑定定位开启和结束到Idr的周期

       ```
           /**
           * 绑定定位周期到Idr的周期里面
           */
           idr.locateWithSwitcher()
               .bindStartAndStopLocateToMapHelper();
               
          /**
          * 如果绑定定位周期到Idr，那么这个方法将会开启定位
          */
          idr.start();
          
           /**
          * 如果绑定定位周期到Idr，那么这个方法将会结束定位
          */
          idr.end()
       ```
   - 单独开启、关闭定位

       ```
           // 获取到定位器
           LocatorViewHelper locator = idr.locateWithSwitcher();
           
           // 开启定位
           locator.readyToLocate().startLocate()；
           
           // 结束定位
           locator.readyToLocate().stopLocate()
       ```
       
- 定位成功、失败回调
    
```
// 拿到LocatorViewHelper之后可以对定位结果进行监听
idr.locateWithSwitcher()
    .onLocateSuccess(new Action1<IdrMapLocationData>() {
        @Override
        public void call(IdrMapLocationData idrMapLocationData) {
            // 定位成功，未处理定位数据，
        }
    })
    .onLocateFloorConfirm(new Action2<String, PointF>() {
        @Override
        public void call(String s, PointF pointF) {
            //  定位成功，当前定位楼层和地图显示楼层为同一楼层
            //  s为定位结果楼层Id，pointF为定位结果点
        }
    })
    .onFloorDiff(new Action2<String, PointF>() {
        @Override
        public void call(String s, PointF pointF) {
            // 定位成功，当前定位楼层和地图显示楼层，不是同一个楼层
            // s为定位结果楼层Id，pointF为定位结果点
        }
    })
    .onLocateFailed(new Action1<String>() {
        @Override
        public void call(String s) {
            // 定位失败，
            // s为定位结果失败信息描述
        }
    });
```

## 4、导航模块

### 导航介绍

**导航类型**
    
*静态导航*
    
<small>静态导航指的是固定了起始点和终点，进行线路规划，并且在地图上面显示导航线</small>
   
*动态导航*
   
<small>动态导航指的是，起始点由定位结果决定，终点由用户设定，在定位的过程中，导航线不断根据起点位置实时规划</small>

**导航功能**
    
- 开启导航
- 导航成功、失败回调
- 导航段结果
- 终止导航
- 导航结束回调

### 导航配置
#### 1. 基本导航方式

   ```
      // 动态导航
     naviResult = idr.naviFrom()
             .to(endUnit, R.drawable.car_position)// 第二个参数是添加的marker资源文件，可不传
             .onNaviInfoList(new Action2<List<LineResult>, NavigateStatus>() {
                 @Override
                 public void call(List<LineResult> lineResults, NavigateStatus navigateStatus) {
                     // 动态导航，导航结果回调。
                     // lineResults 线路规划提示，navigateStatus导航提示
                 }
             })
             .onNaviStatus(des -> {// lambda 表达式 java8.0 新特性
                 switch (des) {
                     case NAVI_SUCCESS:// 导航开启成功
                         break;
                     case NAVI_TOO_NEAR:// 导航开启失败，距离太近
                         break;
                     case NAVI_ARRIVE:// 动态导航，达到目的地，导航结束。
                         break;
                     case NAVI_NO_REGION:// 导航失败，没有region数据
                         break;
                 }
             })
             .startNavi()// 开启导航
             
     naviResult.stopNavi();// 结束导航，在任意需要结束导航的地方结束。
     
     // 静态导航
      naviResult = idr.naviFrom(String, PointF, int);// 参数分别是FloorID，起点Point，起始点marker资源文件（可不传）
              .to(String, PointF, int)// 参数同起点
              .onNaviStatus(new Action1<NaviResultStatus>() {
                  @Override
                  public void call(NaviResultStatus naviResultStatus) {
                      // navigateStatus导航回调，除了没有NAVI_ARRIVE，其他的同动态导航，
                  }
              })
              .startNavi();// 开启静态导航
              
     naviResult.stopNavi();// 结束导航，在任意需要结束导航的地方结束。
   ```
#### 2. 通过idr.naviOptions()，配置导航参数方式

   ```
      idr.naviOptions() 
         .setType(NaviOptions.Type.STATIC_NAVI)// 设置导航类型，静态或者动态
         .setFromPoint(startUnit.getPointF())// 设置起点坐标
         .setFromFloorId(startUnit.getFloorId())// 设置起点楼层ID
         .setSwitcher(mapLoader)// 设置mapLoader，用于添加marker
         .setFromMarker(R.drawable.start_position);// 设置起点marker资源文件
         .setToUnit(endUnit)// 设置终点坐标
         .setToMarker(R.drawable.car_position);// 设置终点的marker
        
     idr.startNavi();// 在任意地方开启导航
     idr.stopNavi();// 在任意地方结束导航
  ```
    
## UI模块

简介：

UI模块在`UI`库里面，提供一些为SDK默认行为的UI界面。开发者可以自定义这些视图。

### 指南针
    
`NorthView` 指南针视图，用于显示地图当前朝向
    
- **XML代码**
    
```
   <com.indoorun.mapapi.view.NorthView
       android:id="@+id/map_north_c"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_margin="5dp" />
```
    
- **java 代码**
    
```
   IdrMapView mMapVIew = (MapView) findViewById(R.id.mapView);
   NorthView northView = (NorthView) findViewById(R.id.map_north_c);
   northView.setMapView(mMapView, 0);//第一个参数为地图视图，第二个参数为北偏角
```
    
### 切换楼层

`SpinnerView` 为楼层切换视图，可以加载一个拥有多层楼层的Region的地图。
    
- **XML代码**
    
```
   <com.indoorun.mapapi.view.SpinnerView
       android:id="@+id/spinner"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_alignParentRight="true"
       android:layout_alignParentTop="true"
       android:layout_margin="20dp" />

```
    
- **java代码**
    
```
   IdrMapView mMapVIew = (MapView) findViewById(R.id.mapView);
   SpinnerView spinnerView = (SpinnerView) findViewById(R.id.spinner);
   
   Idr idr = Idr.with(mMapView);
   idr.loadRegion("xxxxxx")     // 加载指定Region
      .loadFloor(spinnerView); // 用楼层切换器来加载地图
        
   
   LocatorViewHelper locator = idr.locateWithSwitcher()// 开启定位功能，并且自动切换楼层
                                  .bindStartAndStopLocateToMapHelper();// 绑定到idr
   spinnerView.setLocator(locator);// 用于楼层定位的时候，自动更新spinner的定位标记
```
    
### 地图状态按钮

`PositionView` 定位按钮，主要用来改变地图的状态，（normal,trace,follow,navigate），不同状态，地图表现的方式不一样。
    
- **XML代码**
    
```
     <com.indoorun.mapapi.view.PositionView
       android:id="@+id/btn_locate_c"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_alignParentBottom="true"
       android:layout_alignParentLeft="true"
       android:layout_marginBottom="45dp"
       android:layout_marginLeft="25dp" />    
```
    
- **java代码**
    
```
   MapLoader mapLoader = idr.loadRegion("xxx)// 加载Region
                            .loadFloor();// 加载Floor
                            
   LocatorViewHelper locator = idr.locateWithSwitcher()// 开启定位功能，并且自动切换楼层
                                 .bindStartAndStopLocateToMapHelper();// 绑定到idr
   ((PositionView) findViewById(R.id.btn_locate_c)).setMapLoader(mapLoader)// 设置地图加载器，用来当定位成功，不在当前楼层的时候自动切换楼层。
                                                   .setLocator(locator);// 监听定位结果
```
    

