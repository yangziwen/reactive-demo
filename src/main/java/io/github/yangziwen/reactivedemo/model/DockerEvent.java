package io.github.yangziwen.reactivedemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.api.model.EventType;
import com.github.dockerjava.api.model.Node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "docker-event")
public class DockerEvent {

    @Id
    private String id;

    @Field
    private String from;

    @Field
    private Node node;

    @Field
    private EventType type;

    @Field
    @Indexed
    private String status;

    @Field
    private String action;

    @Field
    private String actorId;

    @Field
    private Long time;

    @Field
    private Long timeNano;

    public static DockerEvent from(Event event) {
        return builder()
                .from(event.getFrom())
                .node(event.getNode())
                .type(event.getType())
                .status(event.getStatus())
                .action(event.getAction())
                .actorId(event.getActor().getId())
                .time(event.getTime())
                .timeNano(event.getTimeNano())
                .build();
    }

}
