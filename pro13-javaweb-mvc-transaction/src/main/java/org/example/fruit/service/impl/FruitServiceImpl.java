package org.example.fruit.service.impl;

import org.example.fruit.dao.FruitDAO;
import org.example.fruit.dao.impl.FruitDAOImpl;
import org.example.fruit.pojo.Fruit;
import org.example.fruit.service.FruitService;

import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final FruitDAO fruitDAO = null;

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        return fruitDAO.getFruitList(keyword, pageNo);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return fruitDAO.getFruitById(fid);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.delById(fid);
    }

    @Override
    public Integer getPageCount(String keyword) {
        return (int) Math.ceil(fruitDAO.getFruitCount(keyword) / 5.0);
    }

    @Override
    public void updateFruit(Fruit fruit) {
//        // 测试事务失败是否会回滚，第一个操作成功，第二个操作失败
//        fruitDAO.updateFruit(fruit);
//        fruitDAO.addFruit(new Fruit(23, "test2", 1,2,"ok"));
        fruitDAO.updateFruit(fruit);
    }
}
