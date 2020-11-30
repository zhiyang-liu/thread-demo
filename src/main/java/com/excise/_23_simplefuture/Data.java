package com.excise._23_simplefuture;

/**
 * Data接口有两个重要实现类，一个是RealData也就是真实数据，最终获得的数据
 * 另一个是FutureData，它是用来提取RealData的
 */
public interface Data {

    public String getResult();

}
