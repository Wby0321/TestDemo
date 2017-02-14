# Loading-lib
a simple Loading View for android

## Classes
* ThreeDotLoading

### ThreeDotLoading

#### attrs.xml
| ID  |Attribute|Describe|
| --- |:-------:|-------:|
|  1  | type    | jump - 跳动动画  zoom - 缩放动画|
|  2  | dotCount| 点个数 |
|  3  | dotColor| 点颜色 |
|  4  | duration|动画持续时间|
|  5  | minValue| 最小值 |
|  6  | maxValue| 最大值 |


#### Methods
* setType(String type)
 * 设置动画类型
* setDotColor(int color)
 * 设置点颜色
* setDotCount(int count)
 * 设置点个数
* setDuration(long duration)
 * 设置动画持续时间
* setMaxValue(int dotMaxValue)
 * 设置缩放最大值或跳跃高度
* setMinValue(int dotMinValue)
 * 设置缩放最小值或跳动最底部
* startAnim()
 * 开始动画
* startAnim(float minValue, float maxValue, long duration)
 * 开始动画
* stopAnim()
 * 停止动画



