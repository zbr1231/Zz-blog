package com.sangeng.filter;

import com.sangeng.domain.UserTest;
import com.sangeng.mapper.TestMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class MyTest {
    @Autowired
    TestMapper mapper;
    @Test
    public void test(){


//        List<Article> list = new ArrayList<Article>();
//        list.add(new Article(1L,1));
//        list.add(new Article(8L,1));
//        articleMapper.updateViewCountChange(list);
//        List<UserTest> users = mapper.find();
//        System.out.println(users);
//        mapper.save(91111111,"zz","北京","12345678911");
//        mapper.update(1);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,1,1,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1),new ThreadPoolExecutor.CallerRunsPolicy()
        );

    }
}
