package org.example.fruit.controller;

import org.example.fruit.pojo.Fruit;
import org.example.fruit.service.FruitService;
import org.example.fruit.service.impl.FruitServiceImpl;
import org.example.myssm.myspringmvc.ViewBaseServlet;
import org.example.myssm.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FruitController extends ViewBaseServlet {
    private final FruitService fruitService = null;

    private String del(Integer fid) throws IOException {
        if (fid != null) {
            fruitService.delFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String edit(Integer fid, HttpServletRequest req) throws IOException {
        if (fid != null) {
            Fruit fruit = fruitService.getFruitByFid(fid);
            req.setAttribute("fruit", fruit);
            return "edit";
        }
        return "error";
    }

    private String update(Integer fid, String fname, Integer price, Integer fcount, String remark) throws IOException {
        //3.执行更新
        fruitService.updateFruit(new Fruit(fid, fname, price, fcount, remark));
        //4.资源跳转
        return "redirect:fruit.do";
    }

    private String index(String oper, String keyword, Integer pageNo, HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession();

        if (pageNo == null) {
            pageNo = 1;
        }

        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        // 设置页总数
        session.setAttribute("pageCount", fruitService.getPageCount(keyword));

        // 更新页数，默认为第一页
        session.setAttribute("pageNo", pageNo);

        List<Fruit> fruitList = fruitService.getFruitList(keyword, pageNo);
        // 保存到 session 作用域
        session.setAttribute("fruitList", fruitList);

        return "index";
    }

    private String add(String fname, Integer price, Integer fcount, String remark) throws IOException {
        fruitService.addFruit(new Fruit(0, fname, price, fcount, remark));

        return "redirect:fruit.do";
    }
}
