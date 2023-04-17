package org.example.fruit.dao.impl;

import org.example.fruit.dao.FruitDAO;
import org.example.fruit.pojo.Fruit;
import org.example.myssm.basedao.BaseDAO;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit");
    }

    @Override
    public Fruit getFruitById(Integer fid) {
        return super.load("select * from t_fruit where fid = ?", fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ?, price = ?, fcount = ?, remark = ? where fid = ?";
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
    }

    @Override
    public void addFruit(Fruit fruit) {
        String sql = "insert into t_fruit(fname, price, fcount, remark) values(?, ?, ?, ?)";
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
    }

    @Override
    public void delById(int fid) {
        super.executeUpdate("delete from t_fruit where fid = ?", fid);
    }
}
