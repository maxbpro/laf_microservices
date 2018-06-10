package com.maxb.like.repository.impl;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.maxb.like.domain.Task;
import com.maxb.like.repository.TaskRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class TaskRepositoryImpl implements TaskRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public List<Task> findAllRunningTasksExceptUser(String userId, String bdateString,
                                                    int sex, int vkStatus, int countryId, int cityId) {

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("actionUserIds").not().in(userId));
        query1.addCriteria(Criteria.where("userId").ne(userId));
        query1.addCriteria(Criteria.where("status").is(Task.TaskStatus.RUNNING));
        query1.addCriteria(Criteria.where("priority").is(0));
        query1.with(new Sort(Sort.Direction.ASC, "currentCount"));
        query1.limit(10);


        Query query2 = new Query();
        query2.addCriteria(Criteria.where("actionUserIds").not().in(userId));
        query2.addCriteria(Criteria.where("userId").ne(userId));
        query2.addCriteria(Criteria.where("status").is(Task.TaskStatus.RUNNING));
        query2.addCriteria(Criteria.where("priority").is(1));
        query2.with(new Sort(Sort.Direction.ASC, "currentCount"));
        query2.limit(7);

        Query query3 = new Query();
        query3.addCriteria(Criteria.where("actionUserIds").not().in(userId));
        query3.addCriteria(Criteria.where("userId").ne(userId));
        query3.addCriteria(Criteria.where("status").is(Task.TaskStatus.RUNNING));
        query3.addCriteria(Criteria.where("priority").is(2));
        query3.with(new Sort(Sort.Direction.ASC, "currentCount"));
        query3.limit(4);

        int age_client = 0;

        if(bdateString != null){
            try{

                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                Date bdate = formatter.parse(bdateString);

                long timeBetween = new Date().getTime() - bdate.getTime();
                double yearsBetween = timeBetween / 3.156e+10;
                age_client = (int) Math.floor(yearsBetween);

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        Criteria ageCriteria = null;

        if(age_client != 0){

            Criteria a = new Criteria().andOperator(
                    Criteria.where("ageFrom").lte(age_client),
                    Criteria.where("ageTo").gte(age_client));

            Criteria b = new Criteria().andOperator(
                    Criteria.where("ageFrom").is(0),
                    Criteria.where("ageTo").is(0));

            ageCriteria  = new Criteria().orOperator(a, b);

            //query1.addCriteria(new Criteria().orOperator(a, b));
            //query2.addCriteria(new Criteria().orOperator(a, b));
            //query3.addCriteria(new Criteria().orOperator(a, b));

        }else{

            query1.addCriteria(Criteria.where("ageFrom").is(0));
            query1.addCriteria(Criteria.where("ageTo").is(0));

            query2.addCriteria(Criteria.where("ageFrom").is(0));
            query2.addCriteria(Criteria.where("ageTo").is(0));

            query3.addCriteria(Criteria.where("ageFrom").is(0));
            query3.addCriteria(Criteria.where("ageTo").is(0));
        }

        //0000
        Criteria rest1 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(vkStatus),
                Criteria.where("sex").is(sex),
                Criteria.where("countryId").is(countryId),
                Criteria.where("cityId").is(cityId));

        //0001
        Criteria rest2 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(vkStatus),
                Criteria.where("sex").is(sex),
                Criteria.where("countryId").is(countryId),
                Criteria.where("cityId").is(0));

        //0010
        Criteria rest3 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(vkStatus),
                Criteria.where("sex").is(sex),
                Criteria.where("countryId").is(0),
                Criteria.where("cityId").is(cityId));

        //0011
        Criteria rest4 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(vkStatus),
                Criteria.where("sex").is(sex),
                Criteria.where("countryId").is(0),
                Criteria.where("cityId").is(0));

        //0100
        Criteria rest5 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(vkStatus),
                Criteria.where("sex").is(0),
                Criteria.where("countryId").is(countryId),
                Criteria.where("cityId").is(cityId));

        //0101
        Criteria rest6 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(vkStatus),
                Criteria.where("sex").is(0),
                Criteria.where("countryId").is(countryId),
                Criteria.where("cityId").is(0));

        //0110
        Criteria rest7 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(vkStatus),
                Criteria.where("sex").is(0),
                Criteria.where("countryId").is(0),
                Criteria.where("cityId").is(cityId));

        //0111
        Criteria rest8 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(vkStatus),
                Criteria.where("sex").is(0),
                Criteria.where("countryId").is(0),
                Criteria.where("cityId").is(0));

        //1000
        Criteria rest9 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(0),
                Criteria.where("sex").is(sex),
                Criteria.where("countryId").is(countryId),
                Criteria.where("cityId").is(cityId));

        //1001
        Criteria rest10 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(0),
                Criteria.where("sex").is(sex),
                Criteria.where("countryId").is(countryId),
                Criteria.where("cityId").is(0));


        //1010
        Criteria rest11 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(0),
                Criteria.where("sex").is(sex),
                Criteria.where("countryId").is(0),
                Criteria.where("cityId").is(cityId));


        //1011
        Criteria rest12 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(0),
                Criteria.where("sex").is(sex),
                Criteria.where("countryId").is(0),
                Criteria.where("cityId").is(0));



        //1100
        Criteria rest13 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(0),
                Criteria.where("sex").is(0),
                Criteria.where("countryId").is(countryId),
                Criteria.where("cityId").is(cityId));


        //1101
        Criteria rest14 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(0),
                Criteria.where("sex").is(0),
                Criteria.where("countryId").is(countryId),
                Criteria.where("cityId").is(0));


        //1110
        Criteria rest15 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(0),
                Criteria.where("sex").is(0),
                Criteria.where("countryId").is(0),
                Criteria.where("cityId").is(cityId));


        //1111
        Criteria rest16 = new Criteria().andOperator(
                Criteria.where("vkStatus").is(0),
                Criteria.where("sex").is(0),
                Criteria.where("countryId").is(0),
                Criteria.where("cityId").is(0));

        Criteria bigCriteria = null;

        if(ageCriteria !=null){
            bigCriteria = new Criteria().orOperator(rest1, rest2, rest3, rest4, rest5, rest6, rest7, rest8,
                    rest9, rest10, rest11, rest12, rest13, rest14, rest15, rest1, ageCriteria);
        }else{
            bigCriteria = new Criteria().orOperator(rest1, rest2, rest3, rest4, rest5, rest6, rest7, rest8,
                    rest9, rest10, rest11, rest12, rest13, rest14, rest15, rest16);
        }



        query1.addCriteria(bigCriteria);
        query2.addCriteria(bigCriteria);
        query3.addCriteria(bigCriteria);

//        Query query1 = new Query(
//                Criteria.where("owner.$id").is(userId).and("idea.$id").ne(null)
//        );

//        Criteria criteria = new Criteria().andOperator(
//                Criteria.where("key1").is(value1),
//                Criteria.where("key2").is(value2));


        List<Task> tasks1 = mongoTemplate.find(query1, Task.class);
        List<Task> tasks2 = mongoTemplate.find(query2, Task.class);
        List<Task> tasks3 = mongoTemplate.find(query3, Task.class);

        List<Task> tasks = new ArrayList<>();
        tasks.addAll(tasks1);
        tasks.addAll(tasks2);
        tasks.addAll(tasks3);

        log.debug("RESULT: " + tasks.toString());

        return tasks;
    }
}
