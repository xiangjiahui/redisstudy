package com.xjh.redisstudy.http;

import com.xjh.redisstudy.utils.BuyShoppingTool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * @author xjh
 * @date 2022/2/2 14:01
 */

public class SecKillServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public SecKillServlet(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String userId = new Random().nextInt(50000) + "";
        String prodId = request.getParameter("prodid");

        BuyShoppingTool buyShoppingTool = new BuyShoppingTool();
        boolean secKill = buyShoppingTool.doSecKill(userId, prodId);
        try {
            response.getWriter().print(secKill);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
