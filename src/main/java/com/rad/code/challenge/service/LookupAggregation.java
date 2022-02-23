package com.rad.code.challenge.service;

import com.rad.code.challenge.entities.country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;
//import sample.data.mongo.models.Course;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
@Service
public class LookupAggregation {

    @Autowired
    MongoTemplate mongoTemplate;

    public void LookupAggregationExample() {

        AggregationOperation unwind = Aggregation.unwind("studentIds");

        String query1 = "{$lookup: {from: 'student', let: { stuId: { $toObjectId: '$studentIds' } },"
                + "pipeline: [{$match: {$expr: { $eq: [ '$_id', '$$stuId' ] },},}, "
                + "{$project: {isSendTemplate: 1,openId: 1,stu_name: '$name',stu_id: '$_id',},},], "
                + "as: 'student',}, }";

        TypedAggregation<country> aggregation = Aggregation.newAggregation(
                country.class,
                unwind,
                new CustomAggregationOperation(query1)
        );

        AggregationResults<country> results =
                mongoTemplate.aggregate(aggregation, country.class);
        System.out.println(results.getMappedResults());
    }


    public class CustomAggregationOperation implements AggregationOperation {

        private String jsonOperation;

        public CustomAggregationOperation(String jsonOperation) {
            this.jsonOperation = jsonOperation;
        }

        @Override
        public org.bson.Document toDocument(AggregationOperationContext aggregationOperationContext) {
            return aggregationOperationContext.getMappedObject(org.bson.Document.parse(jsonOperation));
        }
    }
}