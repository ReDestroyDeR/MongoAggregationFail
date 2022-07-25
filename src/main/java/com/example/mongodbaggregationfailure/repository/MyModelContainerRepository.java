package com.example.mongodbaggregationfailure.repository;

import com.example.mongodbaggregationfailure.domain.MyModelContainer;
import com.example.mongodbaggregationfailure.enums.Status;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MyModelContainerRepository extends MongoRepository<MyModelContainer, String> {
    @Aggregation({
            "    {" +
                    "        $match: {" +
                    "           \"groupId\": ?0" +
                    "        }" +
                    "    }",
            "    {" +
                    "        $lookup: {" +
                    "            from: \"models\"," +
                    "            let: { contId: \"$_id\" }," +
                    "            pipeline: [" +
                    "                {$match: {" +
                    "                    $expr: {" +
                    "                        $and: [" +
                    "                            {" +
                    "                                $eq: [ \"$containerId\", \"$$contId\" ]" +
                    "                            }," +
                    "                            {" +
                    "                                $eq: [ \"$status\", ?1 ]" +
                    "                            }]}" +
                    "                    }" +
                    "                }," +
                    "                {" +
                    "                    $count: \"status\"" +
                    "                }" +
                    "            ]," +
                    "            as: \"counter\"" +
                    "        }" +
                    "    }",
            "    {" +
                    "        $project: {" +
                    "            \"_id\": 1," +
                    "            \"_class\": 1," +
                    "            \"groupId\": 1," +
                    "            \"counter\": {" +
                    "               $map: {" +
                    "                   input: \"$counter\"," +
                    "                   as: \"counter\"," +
                    "                   in: \"$$counter.status\"" +
                    "               }" +
                    "            }" +
                    "        }" +
                    "    }",
            "    {" +
                    "        $unwind: {" +
                    "            path: \"$counter\"," +
                    "            preserveNullAndEmptyArrays: true" +
                    "        }" +
                    "    }",
            "    {" +
                    "        $project: {" +
                    "            \"_id\": 1," +
                    "            \"_class\": 1," +
                    "            \"groupId\": 1," +
                    "            \"counter\": {" +
                    "               $ifNull: [" +
                    "                   \"$counter\"," +
                    "                   0" +
                    "               ]" +
                    "            }" +
                    "        }" +
                    "    }"
    })
    List<MyModelContainer> findAllByGroupIdCountingStatus(String groupId, String status);

    @Aggregation({
            "    {" +
                    "        $match: {" +
                    "           \"groupId\": ?0" +
                    "        }" +
                    "    }",
            "    {" +
                    "        $lookup: {" +
                    "            from: \"models\"," +
                    "            let: { contId: \"$_id\" }," +
                    "            pipeline: [" +
                    "                {$match: {" +
                    "                    $expr: {" +
                    "                        $and: [" +
                    "                            {" +
                    "                                $eq: [ \"$containerId\", \"$$contId\" ]" +
                    "                            }," +
                    "                            {" +
                    "                                $eq: [ \"$status\", ?1 ]" +
                    "                            }]}" +
                    "                    }" +
                    "                }," +
                    "                {" +
                    "                    $count: \"status\"" +
                    "                }" +
                    "            ]," +
                    "            as: \"counter\"" +
                    "        }" +
                    "    }",
            "    {" +
                    "        $project: {" +
                    "            \"_id\": 1," +
                    "            \"_class\": 1," +
                    "            \"groupId\": 1," +
                    "            \"counter\": {" +
                    "                $cond: {" +
                    "                    if: {$eq: [ \"$counter\", [ ] ]}," + // FIXME: WE FAIL HERE ON 2.3.3.RELEASE
                    "                    then: [0]," +
                    "                    else: {" +
                    "                        $map: {" +
                    "                            input: \"$counter\"," +
                    "                            as: \"counter\"," +
                    "                            in: \"$$counter.status\"" +
                    "                        }" +
                    "                    }" +
                    "                }" +
                    "            }" +
                    "        }" +
                    "    }",
            "    {" +
                    "        $unwind: {" +
                    "            path: \"$counter\"," +
                    "            preserveNullAndEmptyArrays: true" +
                    "        }" +
                    "    }"
    })
    List<MyModelContainer> desiredFindAllByGroupIdCountingStatus(String s, String status);
}
