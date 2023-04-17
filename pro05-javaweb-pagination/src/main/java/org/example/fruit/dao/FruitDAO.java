package org.example.fruit.dao;

import org.example.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    //获取所有的库存列表信息
    List<Fruit> getFruitList(int pageNo);

    //根据fid获取特定的水果库存信息
    Fruit getFruitById(Integer fid);

    //修改指定的库存记录
    void updateFruit(Fruit fruit);

    //向库存中添加水果
    void addFruit(Fruit fruit);

    //删除库存中的水果
    void delById(int fid);

    //获取库存总记录数
    int getFruitCount();
}
