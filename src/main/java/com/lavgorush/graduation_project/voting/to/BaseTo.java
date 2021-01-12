package com.lavgorush.graduation_project.voting.to;

import com.lavgorush.graduation_project.voting.HasId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class BaseTo implements HasId {
    protected Integer id;
}
