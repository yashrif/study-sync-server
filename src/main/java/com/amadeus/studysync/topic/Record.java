package com.amadeus.studysync.topic;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Record implements Comparable<Record> {
    @NonNull
    private Status status;

    @NonNull
    private LocalDateTime date;

    @Override
    public int compareTo(Record other) {
        return this.date.compareTo(other.date);
    }
}
