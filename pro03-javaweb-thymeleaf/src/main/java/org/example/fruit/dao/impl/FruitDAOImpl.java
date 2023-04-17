package org.example.fruit.dao.impl;

import org.example.fruit.dao.FruitDAO;
import org.example.myssm.basedao.BaseDAO;
import org.example.fruit.pojo.Fruit;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit");
    }
}
