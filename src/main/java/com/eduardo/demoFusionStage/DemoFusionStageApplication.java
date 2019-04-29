package com.eduardo.demoFusionStage;

import redis.clients.jedis.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoFusionStageApplication {

    Logger logger = LoggerFactory.getLogger(DemoFusionStageApplication.class);

    String prefix = System.getenv().getOrDefault("REDIS_URL", "bor.re");

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World " + prefix;
    }

    @RequestMapping("/redis")
    public String redisUrl() {
        if (!prefix.equals("bor.re")) {
            Long visits = null;
            try {
                Jedis jedis = new Jedis(prefix);
                visits = jedis.incr("java_counter");
                jedis.close();
            } catch (Exception e) {
                logger.error(e.toString());
            }

            String hostname = "";
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                logger.error(e.toString());
            }
            return "Hello from <br> host: <b>" + hostname + "</b> <br> number of visits: <b>" + visits.toString()
                    + "</b>";
        } else {
            return "No  parameter provided to connection to Redis DB.";
        }

    }

    @RequestMapping("/host")
    public String restTest() {
        String hostname = "no hostname";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.error(e.toString());
        }
        return hostname;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoFusionStageApplication.class, args);
    }

}
